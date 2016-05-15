package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 字段的解析类
 * @author ytxu 2016-3-21
 *
 */
public class FieldParser {
	private static final String PARAM_CATEGORY_NAME_TAG_NAME = "h2";
    private static final String CSS_QUERY_GET_DESC_PARAM = "tbody > tr";
    private static final int DESC_PARAM_ATTR_NUMBER = 3;// 参数的属性个数
    private static final String CSS_QUERY_GET_FIELD_OPTIONAL = "span.label.label-optional";

    /** header字段：class以header-fields结束 */
    private static final String CSS_QUERY_GET_HEADER = "div[class$=header-fields] > div.control-group";
    /** 输入参数字段：class以param-fields结束 */
    private static final String CSS_QUERY_GET_INPUT_PARAM = "div[class$=param-fields] > div.control-group";

    private static final String CSS_QUERY_GET_FIELD_NAME = "label.control-label";
    private static final String CSS_QUERY_GET_FIELD_TYPE = "div.controls > div.input-append > span.add-on";


	/**
	 * 获取到该方法中所有的字段,包括:header, input params, output params
     */
	public List<FieldEntity> getDescParams(Elements descParamCategoryEles) {
		if (JsoupParserUtil.isNullOrEmpty(descParamCategoryEles)) {
			return null;
		}

		List<FieldEntity> descParams = new ArrayList<>();
		for (Element descParamCategoryEle : descParamCategoryEles) {
			Elements descParamEles = JsoupParserUtil.getEles(descParamCategoryEle, CSS_QUERY_GET_DESC_PARAM);
			if (JsoupParserUtil.isNullOrEmpty(descParamEles)) {
				continue;
			}

			String paramCategoryName = getParamCategoryName(descParamCategoryEle);
			for (Element descParamEle : descParamEles) {
                FieldEntity descParam = getDescParam(paramCategoryName, descParamEle);
                descParams.add(descParam);
			}
		}
		return descParams;
	}

    /** 参数的类别名称：<br>
     * 有可能可以成为实体类的名称,也有可能是results、params等无实际意义的参数名称 */
    private String getParamCategoryName(Element descParamCategoryEle) {
        Element descParamCategoryNameEle = descParamCategoryEle.previousElementSibling();
        String paramCategoryName = null;
        if (null != descParamCategoryNameEle && PARAM_CATEGORY_NAME_TAG_NAME.equalsIgnoreCase(descParamCategoryNameEle.tagName())) {
            paramCategoryName = JsoupParserUtil.getText(descParamCategoryNameEle);
        }
        return paramCategoryName;
    }

    /**
     * 下面是一个字段的描述信息例子：<br>
     * Field                Type        Description<br>
     * company optional     Integer     公司id
     */
    private FieldEntity getDescParam(String paramCategoryName, Element descParamEle) {
        Elements descParamAttrEles = descParamEle.children();
        if (isNotDescParamElement(descParamAttrEles)) {// 本来是要进行throw的,但是出现了StatusCode,没有type的问题.所以直接不处理吧
            LogUtil.i(FieldParser.class, "table > tbody > tr > td, and the size of td`s children is not 3");
            return null;
        }

        Element fieldEle = descParamAttrEles.get(0);
        Element typeEle = descParamAttrEles.get(1);
        Element descEle = descParamAttrEles.get(2);

        boolean isOptional = isOptionalField(fieldEle);
        String paramName = getParamName(fieldEle, isOptional);

        String paramType = JsoupParserUtil.getText(typeEle);
        String paramDesc = JsoupParserUtil.getText(descEle);
//        TODO 未来要对paramDesc进行参数在服务器端类型的翻译，并添加到FieldEntity中
//        results	Array 地区信息结果{DataType:Area}
//        children	Array 地区信息子结果{DataType:Area}

        FieldEntity descParam = new FieldEntity(paramName, paramType, paramDesc, isOptional, paramCategoryName);
        return descParam;
    }

    private boolean isNotDescParamElement(Elements descParamAttrEles) {
        return null == descParamAttrEles || descParamAttrEles.size() != DESC_PARAM_ATTR_NUMBER;
    }

    private boolean isOptionalField(Element fieldEle) {
        Elements fieldOptionalEles = getFieldOptionalEles(fieldEle);
        return null != fieldOptionalEles && fieldOptionalEles.size() == 1;
    }

    private Elements getFieldOptionalEles(Element fieldEle) {
        return JsoupParserUtil.getEles(fieldEle, CSS_QUERY_GET_FIELD_OPTIONAL);// span elements
    }

    private String getParamName(Element fieldEle, boolean isOptional) {
        String paramName;
        if (isOptional) {
            paramName = JsoupParserUtil.getText(fieldEle.textNodes().get(0));
        } else {
            paramName = JsoupParserUtil.getText(fieldEle);
        }
        return paramName;
    }



    public List<FieldEntity> getHeaderFields(Element fieldsetEle, List<FieldEntity> descParams) {
        List<FieldEntity> headerFields = getFields(fieldsetEle, CSS_QUERY_GET_HEADER);
        headerFields = replaceDescParam(headerFields, descParams);
        return headerFields;
    }

    public List<FieldEntity> getInputParamFields(Element fieldsetEle, List<FieldEntity> descParams) {
        List<FieldEntity> inputParamFields = getFields(fieldsetEle, CSS_QUERY_GET_INPUT_PARAM);
        inputParamFields = replaceDescParam(inputParamFields, descParams);
        return inputParamFields;
    }

    private List<FieldEntity> getFields(Element fieldsetEle, String cssQuery) {
        Elements fieldEls = JsoupParserUtil.getEles(fieldsetEle, cssQuery);
        if (JsoupParserUtil.isNullOrEmpty(fieldEls)) {
            return null;
        }

        List<FieldEntity> fields = new ArrayList<>(fieldEls.size());
        for (Element fieldEle : fieldEls) {
            FieldEntity field = getField(fieldEle);
            fields.add(field);
        }
        return fields;
    }

    private FieldEntity getField(Element fieldEle) {
        String fieldName = getFieldName(fieldEle);
        String fieldType = getFieldType(fieldEle);
        FieldEntity field = new FieldEntity(fieldName, fieldType, null);
        return field;
    }

    private String getFieldName(Element fieldEle) {
        Element fieldNameEle = JsoupParserUtil.getFirstEle(fieldEle, CSS_QUERY_GET_FIELD_NAME);
        String fieldName = JsoupParserUtil.getText(fieldNameEle);
        return fieldName;
    }

    private String getFieldType(Element fieldEle) {
        Element fieldTypeEle = JsoupParserUtil.getFirstEle(fieldEle, CSS_QUERY_GET_FIELD_TYPE);
        String fieldTypeText = JsoupParserUtil.getText(fieldTypeEle);
        String fieldType = getFieldType(fieldTypeText);
        return fieldType;
    }

    private String getFieldType(String fieldTypeText) {
        String fieldType;

        if (isNeedRemoveFrontAndRearHtmlTag(fieldTypeText)){
            fieldType = removeFrontAndRearHtmlTag(fieldTypeText);
        } else {
            fieldType = fieldTypeText;
        }
        return fieldType;
    }

    /** 是否需要去除前后的<p></p> */
    private boolean isNeedRemoveFrontAndRearHtmlTag(String fieldTypeText) {
        return fieldTypeText.startsWith("<p>");
    }

    /** 去除前后的<p></p> */
    private String removeFrontAndRearHtmlTag(String fieldTypeText) {
        return fieldTypeText.replace("<p>", "").replace("</p>", "").trim();
    }


    /** 替换为更详细的FieldEntity */
    private List<FieldEntity> replaceDescParam(List<FieldEntity> fields, List<FieldEntity> descParams) {
        if (isNotNeedReplace(fields, descParams)) {
            return fields;
        }

        return getReplacedDescParams(fields, descParams);
    }

    private List<FieldEntity> getReplacedDescParams(List<FieldEntity> fields, List<FieldEntity> descParams) {
        List<FieldEntity> replaceFields = new ArrayList<>(fields.size());
        for (FieldEntity field : fields) {
            FieldEntity target = getDetailedFieldOtherwiseReturnOriginalField(descParams, field);
            replaceFields.add(target);
        }
        return replaceFields;
    }

    private FieldEntity getDetailedFieldOtherwiseReturnOriginalField(List<FieldEntity> descParams, FieldEntity field) {
        int detailedFieldIndexInDescParams = findDetailedFieldIndexInDescParams(descParams, field);
        if (hasFoundDetailedFieldInDescParams(detailedFieldIndexInDescParams)) {// 找到了详细的FieldEntity对象
            return getDetailedField(descParams, detailedFieldIndexInDescParams);
        }
        return field;
    }

    private boolean isNotNeedReplace(List<FieldEntity> fields, List<FieldEntity> descParams) {
        return JsoupParserUtil.isNullOrEmpty(fields) || JsoupParserUtil.isNullOrEmpty(descParams);
    }

    private int findDetailedFieldIndexInDescParams(List<FieldEntity> descParams, FieldEntity field) {
        return descParams.indexOf(field);
    }

    private boolean hasFoundDetailedFieldInDescParams(int indexOfDescParams) {
        return indexOfDescParams >= 0;
    }

    private FieldEntity getDetailedField(List<FieldEntity> descParams, int detailedFieldIndexInDescParams) {
        return descParams.get(detailedFieldIndexInDescParams);
    }


}