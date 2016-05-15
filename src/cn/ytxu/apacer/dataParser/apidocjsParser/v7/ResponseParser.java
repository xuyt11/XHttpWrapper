package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.apacer.entity.ResponseEntity;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 请求响应的解析器
 * 2016-04-05
 */
public class ResponseParser {

    /**
     * @param responseDescEls 该请求响应的描述
     * @param responseEls 请求响应报文的数据:响应头,响应体
     */
    public List<ResponseEntity> getResponses(Elements responseDescEls, Elements responseEls) {
        int responseDescSize = JsoupParserUtil.getSize(responseDescEls);
        int responseSize = JsoupParserUtil.getSize(responseEls);

        throwRuntimeExceptionIfNotSameForResponseAndDesc(responseDescSize, responseSize);

        List<ResponseEntity> responses = new ArrayList<>(responseSize);
        for (Iterator<Element> responseIterator = responseEls.iterator(), reseponseDescIterator = responseDescEls.iterator();
                responseIterator.hasNext() && reseponseDescIterator.hasNext();)
        for (int i = 0; i < responseSize; i++) {
            ResponseEntity response = getResponse(reseponseDescIterator.next(), responseIterator.next());
            responses.add(response);
        }

        return responses;
    }

    private void throwRuntimeExceptionIfNotSameForResponseAndDesc(int responseDescSize, int responseSize) {
        if (responseDescSize <= 0 || responseSize <= 0) {
            throw new RuntimeException("this method can not have response element....");
        }

        if (responseDescSize != responseSize) {
            throw new RuntimeException("the responseDesc size is not same for the response size of this method" +
                    ", responseDescSize(" + responseDescSize + ") != responseSize(" + responseSize);
        }
    }

    private ResponseEntity getResponse(Element responseDescEle, Element responseEle) {
        String responseDesc = getResponseDesc(responseDescEle);// 响应的描述
        String responseMessage = getResponseMessage(responseEle);// 响应报文

        // 默认两个(responseDesc, responseText)都有数据
        int separatorIndex = responseMessage.indexOf("{");
        String responseHeader = responseMessage.substring(0, separatorIndex).trim();
        String responseContent = responseMessage.substring(separatorIndex).trim();

        ResponseEntity response = new ResponseEntity(responseDesc, responseHeader, responseContent);
        return response;
    }

    private String getResponseDesc(Element responseDescEle) {
        return JsoupParserUtil.getText(responseDescEle);
    }

    private String getResponseMessage(Element responseEle) {
        return JsoupParserUtil.getText(responseEle);
    }

}
