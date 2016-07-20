package cn.ytxu.api_semi_auto_creater.parser;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.entity.*;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.ListUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类的解析类
 * Created by ytxu on 2016/6/26.
 */
public class RequestParser {
    private static final String CSS_QUERY_ARTICLE = "article";
    private static final String ATTR_DATA_NAME = "data-name";
    private static final String ATTR_DATA_VERSION = "data-version";
    private static final String CSS_QUERY_GET_METHOD_DESC = "div.pull-left > h1";
    private static final String CSS_QUERY_GET_TYPE_AND_URL_FOR_METHOD = "pre.prettyprint.language-html";
    private static final String ATTR_DATA_TYPE = "data-type";
    private static final String CSS_QUERY_GET_METHOD_URL = "code";
    private static final String CSS_QUERY_FIELDSET = "form.form-horizontal > fieldset";
    private static final String CSS_QUERY_RESPONSE_DESC = "ul.nav.nav-tabs.nav-tabs-examples > li";
    private static final String CSS_QUERY_RESPONSE = "div.tab-content > div.tab-pane";

    /**
     * header字段：class以header-fields结束
     */
    private static final String CSS_QUERY_GET_HEADER = "div[class$=header-fields] > div.control-group";
    /**
     * 输入参数字段：class以param-fields结束
     */
    private static final String CSS_QUERY_GET_INPUT_PARAM = "div[class$=param-fields] > div.control-group";


    private RequestModel baseEntity;
    private Element baseEle;

    private Element articleEle;

    public RequestParser(RequestModel baseEntity) {
        super();
        this.baseEntity = baseEntity;
        this.baseEle = baseEntity.getElement();
    }

    public void get() {
//		String methodId = baseEle.attr("id");
        getArticleElement();
//		String methodCategory = articleEle.attr("data-group");
        getMethodName();
        getMethodVersion();
        getMethodDescription();

        getMethodTypeAndUrl();

        new DefinedParamPreParser().get();
        getInputs();
        getResponses();
    }

    private void getArticleElement() {
        articleEle = JsoupParserUtil.getFirstEle(baseEle, CSS_QUERY_ARTICLE);
    }

    private void getMethodName() {
        String dataName = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_NAME);
        String methodName = CamelCaseUtils.toCamelCase(dataName);
        baseEntity.setMethodName(methodName);
    }

    private void getMethodVersion() {
        String methodVersion = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_VERSION);
        baseEntity.setVersionCode(methodVersion);
    }

    private void getMethodDescription() {
        Element methodDescEle = JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_GET_METHOD_DESC);
        String methodDescription = JsoupParserUtil.getText(methodDescEle);
        baseEntity.setDescrption(methodDescription);
    }


    private void getMethodTypeAndUrl() {
        Element preEle = JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_GET_TYPE_AND_URL_FOR_METHOD);
        getMethodType(preEle);
        getMethodUrl(preEle);
    }

    private void getMethodType(Element preEle) {
        String methodType = JsoupParserUtil.getAttr(preEle, ATTR_DATA_TYPE);
        baseEntity.setMethodType(methodType);
    }

    private void getMethodUrl(Element preEle) {
        Element methodUrlEle = JsoupParserUtil.getFirstEle(preEle, CSS_QUERY_GET_METHOD_URL);
        String methodUrl = JsoupParserUtil.getText(methodUrlEle);
        baseEntity.setRestfulUrl(new RESTfulUrlModel(baseEntity, methodUrl));
//        return RESTfulAPIParser.parse(methodUrl);
    }

    private class DefinedParamPreParser {
        private static final String CSS_QUERY_TABLE = "table";
        private static final String CSS_QUERY_GET_DESC_PARAM = "tbody > tr";
        private static final String PARAM_CATEGORY_NAME_TAG_NAME = "h2";

        public void get() {
            Elements descParamCategoryEles = JsoupParserUtil.getEles(articleEle, CSS_QUERY_TABLE);// table elements
            if (ListUtil.isEmpty(descParamCategoryEles)) {
                return;
            }

            setDefinedParams(descParamCategoryEles);
        }

        private void setDefinedParams(Elements descParamCategoryEles) {
            List<DefinedParameterModel> definedParams = new ArrayList<>();

            for (Element descParamCategoryEle : descParamCategoryEles) {
                List<DefinedParameterModel> defineds = getDefinedParams(descParamCategoryEle);
                if (JsoupParserUtil.isNullOrEmpty(defineds)) {
                    continue;
                }
                definedParams.addAll(defineds);
            }

            baseEntity.setDefinedParams(definedParams);
        }

        private List<DefinedParameterModel> getDefinedParams(Element descParamCategoryEle) {
            Elements descParamEles = JsoupParserUtil.getEles(descParamCategoryEle, CSS_QUERY_GET_DESC_PARAM);
            if (JsoupParserUtil.isNullOrEmpty(descParamEles)) {
                return null;
            }

            List<DefinedParameterModel> definedParams = new ArrayList<>(descParamEles.size());
            String paramCategoryName = getParamCategoryName(descParamCategoryEle);
            for (Element descParamEle : descParamEles) {
                DefinedParameterModel definedParam = new DefinedParameterModel(baseEntity, descParamEle, paramCategoryName);
                definedParams.add(definedParam);
            }

            return definedParams;
        }

        /**
         * 参数的类别名称：<br>
         * 有可能可以成为实体类的名称,也有可能是results、params等无实际意义的参数名称
         */
        private String getParamCategoryName(Element descParamCategoryEle) {
            Element descParamCategoryNameEle = descParamCategoryEle.previousElementSibling();
            String paramCategoryName = null;
            if (null != descParamCategoryNameEle && PARAM_CATEGORY_NAME_TAG_NAME.equalsIgnoreCase(descParamCategoryNameEle.tagName())) {
                paramCategoryName = JsoupParserUtil.getText(descParamCategoryNameEle);
            }
            return paramCategoryName;
        }

    }

    private void getInputs() {
        Element fieldsetEle = getFieldsetEle();
        getHeaderFields(fieldsetEle);
        getInputFields(fieldsetEle);
    }

    private Element getFieldsetEle() {
        return JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_FIELDSET);
    }

    private void getHeaderFields(Element fieldsetEle) {
        Elements fieldEls = JsoupParserUtil.getEles(fieldsetEle, CSS_QUERY_GET_HEADER);
        if (ListUtil.isEmpty(fieldEls)) {
            return;
        }

        setHeaders(fieldEls);
//        List<FieldEntity> headerFields = new FieldParser().getHeaderFields(fieldsetEle, descParams);
//        return headerFields;
    }

    private void setHeaders(Elements fieldEls) {
        List<InputParamModel> headers = new ArrayList<>(fieldEls.size());
        for (Element fieldEle : fieldEls) {
            InputParamModel header = new InputParamModel(baseEntity, fieldEle);
            headers.add(header);
        }
        baseEntity.setHeaders(headers);
    }

    private void getInputFields(Element fieldsetEle) {
        Elements fieldEls = JsoupParserUtil.getEles(fieldsetEle, CSS_QUERY_GET_INPUT_PARAM);
        if (ListUtil.isEmpty(fieldEls)) {
            return;
        }

        setInputParams(fieldEls);
//        List<FieldEntity> inputFields = new FieldParser().getInputParamFields(fieldsetEle, descParams);
//        return inputFields;
    }

    private void setInputParams(Elements fieldEls) {
        List<InputParamModel> inputs = new ArrayList<>(fieldEls.size());
        for (Element fieldEle : fieldEls) {
            InputParamModel input = new InputParamModel(baseEntity, fieldEle);
            inputs.add(input);
        }
        baseEntity.setInputParams(inputs);
    }


    private void getResponses() {
        Elements responseDescEls = getResponseDescEles(articleEle);// 该请求响应的描述
        Elements responseEls = getResponseEles(articleEle);// 请求响应报文的数据:响应头,响应体

        if (ListUtil.isEmpty(responseDescEls) || ListUtil.isEmpty(responseEls)) {
            return;
        }

        if (responseDescEls.size() != responseEls.size()) {
            throw new RuntimeException("the response of this request is error status");
        }

        setResponses(responseDescEls, responseEls);
//        List<ResponseModel> responses = new ResponseParser().getResponses(responseDescEls, responseEls);
//        responses = new OutputParamsParser().parseResponseContent(responses, descParams);
    }

    private Elements getResponseDescEles(Element articleEle) {
        return JsoupParserUtil.getEles(articleEle, CSS_QUERY_RESPONSE_DESC);
    }

    private Elements getResponseEles(Element articleEle) {
        return JsoupParserUtil.getEles(articleEle, CSS_QUERY_RESPONSE);
    }

    private void setResponses(Elements responseDescEls, Elements responseEls) {
        List<ResponseModel> responses = new ArrayList<>(responseDescEls.size());
        for (int i = 0, count = responseDescEls.size(); i < count; i++) {
            ResponseModel response = new ResponseModel(baseEntity, responseDescEls.get(i), responseEls.get(i));
            responses.add(response);
        }
        baseEntity.setResponses(responses);
    }

}
