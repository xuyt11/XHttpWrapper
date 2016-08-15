package cn.ytxu.api_semi_auto_creater.parser.request;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.request.restful_url.RESTfulUrlModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.parser.request.defined.DefinedsParser;
import cn.ytxu.api_semi_auto_creater.parser.request.input.InputsParser;
import cn.ytxu.api_semi_auto_creater.parser.request.restful_url.RESTfulUrlParser;
import cn.ytxu.api_semi_auto_creater.parser.response.ResponseParser;
import org.jsoup.nodes.Element;

/**
 * 分类的解析类
 * Created by ytxu on 2016/6/26.
 */
public class RequestParser {
    private static final String CSS_QUERY_ARTICLE = "article";
    private static final String CSS_QUERY_GET_METHOD_DESC = "div.pull-left > h1";
    private static final String CSS_QUERY_GET_TYPE_AND_URL_FOR_METHOD = "pre.prettyprint.language-html";
    private static final String ATTR_DATA_TYPE = "data-type";
    private static final String CSS_QUERY_GET_METHOD_URL = "code";


    private RequestModel request;
    private Element baseEle;

    private Element articleEle;

    public RequestParser(RequestModel baseEntity) {
        super();
        this.request = baseEntity;
        this.baseEle = baseEntity.getElement();
    }

    public void get() {
        getArticleElement();
        getMethodDescription();
        getMethodTypeAndUrl();

        new DefinedsParser(request, articleEle).start();
        new InputsParser(request, articleEle).start();
        new ResponseParser(request, articleEle).start();
    }

    private void getArticleElement() {
        articleEle = JsoupParserUtil.getFirstEle(baseEle, CSS_QUERY_ARTICLE);
    }

    private void getMethodDescription() {
        Element methodDescEle = JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_GET_METHOD_DESC);
        String methodDescription = JsoupParserUtil.getText(methodDescEle);
        request.setDescrption(methodDescription);
    }

    private void getMethodTypeAndUrl() {
        Element preEle = JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_GET_TYPE_AND_URL_FOR_METHOD);
        getMethodType(preEle);
        getMethodUrl(preEle);
    }

    private void getMethodType(Element preEle) {
        String methodType = JsoupParserUtil.getAttr(preEle, ATTR_DATA_TYPE);
        request.setMethodType(methodType);
    }

    private void getMethodUrl(Element preEle) {
        Element methodUrlEle = JsoupParserUtil.getFirstEle(preEle, CSS_QUERY_GET_METHOD_URL);
        String methodUrl = JsoupParserUtil.getText(methodUrlEle);
        RESTfulUrlModel restfulUrlModel = new RESTfulUrlModel(request, methodUrl);
        new RESTfulUrlParser(restfulUrlModel).start();
        request.setRestfulUrl(restfulUrlModel);
    }


}
