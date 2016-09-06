package cn.ytxu.api_semi_auto_creater.parser.request.input;

import cn.ytxu.util.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.util.ListUtil;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * 字段的解析类
 *
 * @author ytxu 2016-3-21
 */
public class InputFieldParser {
    private static final String CSS_QUERY_GET_FIELD_NAME = "label.control-label";
    private static final String CSS_QUERY_GET_FIELD_TYPE = "div.controls > div.input-append > span.add-on";


    private InputParamModel input;
    private Element fieldEle;

    public InputFieldParser(InputParamModel input) {
        this.input = input;
        this.fieldEle = input.getElement();
    }

    public InputParamModel start() {
        getFieldName();
        getFieldType();
        setDefind();
        return input;
    }

    private void getFieldName() {
        Element fieldNameEle = JsoupParserUtil.getFirstEle(fieldEle, CSS_QUERY_GET_FIELD_NAME);
        String fieldName = JsoupParserUtil.getText(fieldNameEle);
        input.setName(fieldName);
    }

    private void getFieldType() {
        Element fieldTypeEle = JsoupParserUtil.getFirstEle(fieldEle, CSS_QUERY_GET_FIELD_TYPE);
        String fieldTypeText = JsoupParserUtil.getText(fieldTypeEle);
        String fieldType = getFieldType(fieldTypeText);
        input.setType(fieldType);
    }

    private String getFieldType(String fieldTypeText) {
        String fieldType;

        if (isNeedRemoveFrontAndRearHtmlTag(fieldTypeText)) {
            fieldType = removeFrontAndRearHtmlTag(fieldTypeText);
        } else {
            fieldType = fieldTypeText;
        }
        return fieldType;
    }

    /**
     * 是否需要去除前后的<p></p>
     */
    private boolean isNeedRemoveFrontAndRearHtmlTag(String fieldTypeText) {
        return fieldTypeText.startsWith("<p>");
    }

    /**
     * 去除前后的<p></p>
     */
    private String removeFrontAndRearHtmlTag(String fieldTypeText) {
        return fieldTypeText.replace("<p>", "").replace("</p>", "").trim();
    }


    /**
     * 替换为更详细的FieldEntity
     */
    private void setDefind() {
        List<DefinedParamModel> defineds = input.getHigherLevel().getDefinedParams();
        if (ListUtil.isEmpty(defineds)) {
            return;
        }
        setDefind(defineds);
    }

    private void setDefind(List<DefinedParamModel> defineds) {
        for (DefinedParamModel defind : defineds) {
            if (equals(defind)) {
                input.setDefind(defind);
                break;
            }
        }
    }

    private boolean equals(DefinedParamModel defind) {
        return defind.getName().equals(input.getName()) && defind.getType().equals(input.getType());
    }

}