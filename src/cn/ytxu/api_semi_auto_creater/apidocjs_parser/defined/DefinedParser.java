package cn.ytxu.api_semi_auto_creater.apidocjs_parser.defined;

import cn.ytxu.api_semi_auto_creater.apidocjs_parser.util.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 解析类
 * 获取到该方法中所有的字段,包括:header, input params, output params
 * Created by ytxu on 2016/6/26.
 */
public class DefinedParser {
    private static final int DEFINED_PARAM_ATTR_NUMBER = 3;// 参数的属性个数
    private static final int DEFINED_PARAM_ATTR_FIELD = 0;// 参数的字段,在tr标签中的index
    private static final int DEFINED_PARAM_ATTR_TYPE = 1;// 参数的类型,在tr标签中的index
    private static final int DEFINED_PARAM_ATTR_DESC = 2;// 参数的描述,在tr标签中的index

    private static final String CSS_QUERY_GET_FIELD_OPTIONAL = "span.label.label-optional";

    private DefinedParamModel definedModel;
    private Element baseEle;
    private Elements descParamAttrEles;

    public DefinedParser(DefinedParamModel baseEntity) {
        super();
        this.definedModel = baseEntity;
        this.baseEle = baseEntity.getElement();
    }

    /**
     * 下面是一个字段的描述信息例子：<br>
     * Field                Type        Description<br>
     * company optional     Integer     公司id
     */
    public void start() {
        findChildren();

        if (isNotDescParamElement()) {// 本来是要进行throw的,但是出现了StatusCode,没有type的问题.所以直接不处理吧
            LogUtil.i(DefinedParser.class, "table > tbody > tr > td, and the size of td`s children is not 3");
            return;
        }

        getIsOptionalField();
        getName();
        getType();
        getDesc();
    }

    private void findChildren() {
        descParamAttrEles = baseEle.children();
    }

    private boolean isNotDescParamElement() {
        return null == descParamAttrEles || descParamAttrEles.size() != DEFINED_PARAM_ATTR_NUMBER;
    }

    private void getIsOptionalField() {
        Elements fieldOptionalEles = getFieldOptionalEles();
        boolean isOptional = null != fieldOptionalEles && fieldOptionalEles.size() == 1;
        definedModel.setOptional(isOptional);
    }

    private Elements getFieldOptionalEles() {
        Element fieldEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_FIELD);
        return JsoupParserUtil.getEles(fieldEle, CSS_QUERY_GET_FIELD_OPTIONAL);// span elements
    }

    private void getName() {
        Element fieldEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_FIELD);
        String paramName;
        if (definedModel.isOptional()) {
            paramName = JsoupParserUtil.getText(fieldEle.textNodes().get(0));
        } else {
            paramName = JsoupParserUtil.getText(fieldEle);
        }
        definedModel.setName(paramName);
    }

    private void getType() {
        Element typeEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_TYPE);
        String paramType = JsoupParserUtil.getText(typeEle);
        definedModel.setType(paramType);
    }

    private void getDesc() {
        Element descEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_DESC);
        String paramDesc = JsoupParserUtil.getText(descEle);
        definedModel.setDescription(paramDesc);

        getDataType(paramDesc);
    }

    private void getDataType(String paramDesc) {
        Matcher m = DATA_TYPE_PATTERN.matcher(paramDesc);
        if (!m.find()) {
            return;
        }

        String group = m.group();
        int methodNameStart = PATTERN_FRONT.length();
        int methodNameEnd = group.length() - PATTERN_END.length();
        String dataType = group.substring(methodNameStart, methodNameEnd);
        definedModel.setDataType(dataType);
    }

    private static final String PATTERN_FRONT = "{DataType:";
    private static final String PATTERN_END = "}";
    private static final Pattern DATA_TYPE_PATTERN = Pattern.compile("(\\{DataType:)\\w+(\\})");

    public static void main(String... args) {
        String desc = "desc text {DataType:Area}";

        Matcher m = DATA_TYPE_PATTERN.matcher(desc);
        if (m.find()) {
            String group = m.group();
            int methodNameStart = PATTERN_FRONT.length();
            int methodNameEnd = group.length() - PATTERN_END.length();
            String dataType = group.substring(methodNameStart, methodNameEnd);
            System.out.println("data type :" + dataType);
        }
    }

}