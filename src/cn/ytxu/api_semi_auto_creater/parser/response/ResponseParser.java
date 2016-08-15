package cn.ytxu.api_semi_auto_creater.parser.response;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.entity.ResponseEntity;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.util.ListUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/15.
 */
public class ResponseParser {
    private static final String CSS_QUERY_RESPONSE_DESC = "ul.nav.nav-tabs.nav-tabs-examples > li";
    private static final String CSS_QUERY_RESPONSE = "div.tab-content > div.tab-pane";

    private RequestModel requestModel;
    private Element articleEle;

    public ResponseParser(RequestModel requestModel, Element articleEle) {
        this.requestModel = requestModel;
        this.articleEle = articleEle;
    }

    public void start() {
        Elements responseDescEls = getResponseDescEles(articleEle);// 该请求响应的描述
        Elements responseEls = getResponseEles(articleEle);// 请求响应报文的数据:响应头,响应体

        setResponses(responseDescEls, responseEls);
//        List<ResponseEntity> responses = new ResponseParser().getResponses(responseDescEls, responseEls);
//        responses = new OutputParamsParser().parseResponseContent(responses, descParams);
    }

    private Elements getResponseDescEles(Element articleEle) {
        return JsoupParserUtil.getEles(articleEle, CSS_QUERY_RESPONSE_DESC);
    }

    private Elements getResponseEles(Element articleEle) {
        return JsoupParserUtil.getEles(articleEle, CSS_QUERY_RESPONSE);
    }

    private void setResponses(Elements responseDescEls, Elements responseEls) {
        List<ResponseEntity> responses = new ArrayList<>(responseDescEls.size());
        // TODO 需要解析response
//        for (int i = 0, count = responseDescEls.size(); i < count; i++) {
//            ResponseEntity response = new ResponseEntity(request, responseDescEls.get(i), responseEls.get(i));
//            responses.add(response);
//        }
//        request.setResponses(responses);
    }


}
