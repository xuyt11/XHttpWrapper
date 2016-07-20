package cn.ytxu.api_semi_auto_creater.parser;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.entity.DefinedParameterModel;
import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 解析类
 * 获取到该方法中所有的字段,包括:header, input params, output params
 * Created by ytxu on 2016/6/26.
 */
public class DefinedParamParser {
    private static final int DEFINED_PARAM_ATTR_NUMBER = 3;// 参数的属性个数
    private static final int DEFINED_PARAM_ATTR_FIELD = 0;// 参数的字段,在tr标签中的index
    private static final int DEFINED_PARAM_ATTR_TYPE = 1;// 参数的类型,在tr标签中的index
    private static final int DEFINED_PARAM_ATTR_DESC = 2;// 参数的描述,在tr标签中的index

    private static final String CSS_QUERY_GET_FIELD_OPTIONAL = "span.label.label-optional";

    private DefinedParameterModel baseEntity;
    private Element baseEle;
    private Elements descParamAttrEles;
    private Element fieldEle, typeEle, descEle;// sub ele

    public DefinedParamParser(DefinedParameterModel baseEntity) {
        super();
        this.baseEntity = baseEntity;
        this.baseEle = baseEntity.getElement();
    }

    /**
     * 下面是一个字段的描述信息例子：<br>
     * Field                Type        Description<br>
     * company optional     Integer     公司id
     */
    public void get() {
        findChildren();

        if (isNotDescParamElement()) {// 本来是要进行throw的,但是出现了StatusCode,没有type的问题.所以直接不处理吧
            LogUtil.i(DefinedParamParser.class, "table > tbody > tr > td, and the size of td`s children is not 3");
            return;
        }

        getSubEles();

        getIsOptionalField();

        getName();
        getType();
        getDesc();

//        TODO 未来要对paramDesc进行参数在服务器端类型的翻译，并添加到FieldEntity中
//        results	Array 地区信息结果{DataType:Area}
//        children	Array 地区信息子结果{DataType:Area}
    }

    private void findChildren() {
        descParamAttrEles = baseEle.children();
    }

    private boolean isNotDescParamElement() {
        return null == descParamAttrEles || descParamAttrEles.size() != DEFINED_PARAM_ATTR_NUMBER;
    }

    private void getSubEles() {
        fieldEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_FIELD);
        typeEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_TYPE);
        descEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_DESC);
    }

    private void getIsOptionalField() {
        Elements fieldOptionalEles = getFieldOptionalEles();
        boolean isOptional = null != fieldOptionalEles && fieldOptionalEles.size() == 1;
        baseEntity.setOptional(isOptional);
    }

    private Elements getFieldOptionalEles() {
        return JsoupParserUtil.getEles(fieldEle, CSS_QUERY_GET_FIELD_OPTIONAL);// span elements
    }

    private void getName() {
        String paramName;
        if (baseEntity.isOptional()) {
            paramName = JsoupParserUtil.getText(fieldEle.textNodes().get(0));
        } else {
            paramName = JsoupParserUtil.getText(fieldEle);
        }
        baseEntity.setName(paramName);
    }

    private String getType() {
        String paramType = JsoupParserUtil.getText(typeEle);
        baseEntity.setType(paramType);
        return paramType;
    }

    private String getDesc() {
        String paramDesc = JsoupParserUtil.getText(descEle);
        baseEntity.setDescription(paramDesc);
        return paramDesc;
    }

}
