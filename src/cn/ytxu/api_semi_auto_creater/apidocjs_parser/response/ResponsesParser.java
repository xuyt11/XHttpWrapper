package cn.ytxu.api_semi_auto_creater.apidocjs_parser.response;

import cn.ytxu.util.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/15.
 */
public class ResponsesParser {
    private static final String CSS_QUERY_RESPONSE_DESC = "ul.nav.nav-tabs.nav-tabs-examples > li";
    private static final String CSS_QUERY_RESPONSE = "div.tab-content > div.tab-pane";

    private RequestModel requestModel;
    private Element articleEle;

    public ResponsesParser(RequestModel requestModel, Element articleEle) {
        this.requestModel = requestModel;
        this.articleEle = articleEle;
    }

    public void start() {
        Elements responseDescEls = getResponseDescEles(articleEle);// 该请求响应的描述
        Elements responseMessageEls = getResponseMessageEles(articleEle);// 请求响应报文的数据:响应头,响应体

        setResponses(responseDescEls, responseMessageEls);
//        List<ResponseEntity> responses = new ResponsesParser().getResponses(responseDescEls, responseEls);
//        responses = new OutputParamsParser().parseResponseContent(responses, descParams);
    }

    private Elements getResponseDescEles(Element articleEle) {
        return JsoupParserUtil.getEles(articleEle, CSS_QUERY_RESPONSE_DESC);
    }

    private Elements getResponseMessageEles(Element articleEle) {
        return JsoupParserUtil.getEles(articleEle, CSS_QUERY_RESPONSE);
    }

    private void setResponses(Elements responseDescEls, Elements responseMessageEls) {
        List<ResponseModel> responses = new ArrayList<>(responseDescEls.size());
        for (int i = 0, count = responseDescEls.size(); i < count; i++) {
            ResponseModel response = getResponse(responseDescEls.get(i), responseMessageEls.get(i));
            responses.add(response);
        }
        requestModel.setResponses(responses);
    }

    private ResponseModel getResponse(Element responseDescEle, Element responseMessageEle) {
        ResponseModel response = new ResponseModel(requestModel, responseDescEle, responseMessageEle);

        String desc = getResponseDesc(responseDescEle);// 响应的描述
        String message = getResponseMessage(responseMessageEle);// 响应报文

        // 默认两个(responseDesc, responseText)都有数据
        int separatorIndex = getSeparatorIndex(message);
        if (separatorIndex <= 0) {
            response.setNotHaveJsonTypeResponseBody(true);
        } else {
            String header = getResponseHeader(message, separatorIndex);
            String body = getResponseBody(message, separatorIndex);
            response.setData(desc, header, body);
        }
        return response;
    }

    private String getResponseDesc(Element responseDescEle) {
        return JsoupParserUtil.getText(responseDescEle);
    }

    private String getResponseMessage(Element responseEle) {
        return JsoupParserUtil.getText(responseEle);
    }

    private int getSeparatorIndex(String responseMessage) {
        return responseMessage.indexOf("{");
    }

    private String getResponseHeader(String responseMessage, int separatorIndex) {
        return responseMessage.substring(0, separatorIndex).trim();
    }

    private String getResponseBody(String responseMessage, int separatorIndex) {
        return responseMessage.substring(separatorIndex).trim();
    }

}
