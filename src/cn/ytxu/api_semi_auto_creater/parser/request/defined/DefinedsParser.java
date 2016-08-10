package cn.ytxu.api_semi_auto_creater.parser.request.defined;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.util.ListUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
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
            return Collections.EMPTY_LIST;
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
        new DefinedParser(definedParam).start();
        return definedParam;
    }


}