package cn.ytxu.api_semi_auto_creater.parser.request;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.util.ListUtil;
import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DefinedsParser {
    private static final String CSS_QUERY_TABLE = "table";
    private static final String CSS_QUERY_GET_DESC_PARAM = "tbody > tr";
    private static final String PARAM_CATEGORY_NAME_TAG_NAME = "h2";

    private RequestModel request;
    private Element articleEle;

    public DefinedsParser(RequestModel request, Element articleEle) {
        this.request = request;
        this.articleEle = articleEle;
    }

    public void start() {
        Elements descParamCategoryEles = JsoupParserUtil.getEles(articleEle, CSS_QUERY_TABLE);// table elements
        if (ListUtil.isEmpty(descParamCategoryEles)) {
            return;
        }

        setDefinedParams(descParamCategoryEles);
    }

    private void setDefinedParams(Elements descParamCategoryEles) {
        List<DefinedParamModel> definedParams = new ArrayList<>();
        for (Element descParamCategoryEle : descParamCategoryEles) {
            List<DefinedParamModel> defineds = getDefinedParams(descParamCategoryEle);
            if (JsoupParserUtil.isNullOrEmpty(defineds)) {
                continue;
            }
            definedParams.addAll(defineds);
        }
        request.setDefinedParams(definedParams);
    }

    private List<DefinedParamModel> getDefinedParams(Element descParamCategoryEle) {
        Elements descParamEles = JsoupParserUtil.getEles(descParamCategoryEle, CSS_QUERY_GET_DESC_PARAM);
        if (JsoupParserUtil.isNullOrEmpty(descParamEles)) {
            return null;
        }

        List<DefinedParamModel> definedParams = new ArrayList<>(descParamEles.size());
        String categoryName = getCategoryName(descParamCategoryEle);
        for (Element descParamEle : descParamEles) {
            DefinedParamModel definedParam = getDefinedParamModel(categoryName, descParamEle);
            definedParams.add(definedParam);
        }

        return definedParams;
    }

    /**
     * 参数的类别名称：<br>
     * 有可能可以成为实体类的名称,也有可能是results、params等无实际意义的参数名称
     */
    private String getCategoryName(Element descParamCategoryEle) {
        Element descParamCategoryNameEle = descParamCategoryEle.previousElementSibling();
        String categoryName = null;
        if (null != descParamCategoryNameEle && PARAM_CATEGORY_NAME_TAG_NAME.equalsIgnoreCase(descParamCategoryNameEle.tagName())) {
            categoryName = JsoupParserUtil.getText(descParamCategoryNameEle);
        }
        return categoryName;
    }

    private DefinedParamModel getDefinedParamModel(String categoryName, Element descParamEle) {
        DefinedParamModel definedParam = new DefinedParamModel(request, descParamEle, categoryName);
        new DefinedParamDescParser(definedParam).get();
        return definedParam;
    }

    /**
     * 解析类
     * 获取到该方法中所有的字段,包括:header, input params, output params
     * Created by ytxu on 2016/6/26.
     */
    private static class DefinedParamDescParser {
        private static final int DEFINED_PARAM_ATTR_NUMBER = 3;// 参数的属性个数
        private static final int DEFINED_PARAM_ATTR_FIELD = 0;// 参数的字段,在tr标签中的index
        private static final int DEFINED_PARAM_ATTR_TYPE = 1;// 参数的类型,在tr标签中的index
        private static final int DEFINED_PARAM_ATTR_DESC = 2;// 参数的描述,在tr标签中的index

        private static final String CSS_QUERY_GET_FIELD_OPTIONAL = "span.label.label-optional";

        private DefinedParamModel baseEntity;
        private Element baseEle;
        private Elements descParamAttrEles;
        private Element fieldEle, typeEle, descEle;// sub ele

        public DefinedParamDescParser(DefinedParamModel baseEntity) {
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
                LogUtil.i(DefinedParamDescParser.class, "table > tbody > tr > td, and the size of td`s children is not 3");
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


}