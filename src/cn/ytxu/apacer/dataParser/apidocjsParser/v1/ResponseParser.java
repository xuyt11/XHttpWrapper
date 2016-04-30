package cn.ytxu.apacer.dataParser.apidocjsParser.v1;

import cn.ytxu.apacer.entity.ResponseEntity;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求响应的解析器
 * 2016-04-05
 */
public class ResponseParser {

    /**
     *
     * @param categoryIndex
     * @param methodIndex
     * @param responseDescEls 该请求响应的描述
     * @param responseEls 请求响应报文的数据:响应头,响应体
     */
    public static List<ResponseEntity> parser(int categoryIndex, int methodIndex, Elements responseDescEls, Elements responseEls) {
        int responseDescSize = (null == responseDescEls) ? 0 : responseDescEls.size();
        int responseSize = (null == responseEls) ? 0 : responseEls.size();

        if (responseDescSize <= 0 || responseSize <= 0) {
            throw new RuntimeException("this method can not have response element...., categoryIndex:" + categoryIndex + ", and the methodIndex:" + methodIndex);
        }

        if (responseDescSize != responseSize) {
            throw new RuntimeException("the responseDesc size is not same for the response size of this method" +
                    ", responseDescSize(" + responseDescSize + ") != responseSize(" + responseSize
                    + "), categoryIndex:" + categoryIndex + ", and the methodIndex:" + methodIndex);
        }

        List<ResponseEntity> responses = new ArrayList<>();
        for (int i = 0; i < responseDescSize; i++) {
            responses.add(parserOneResponse(categoryIndex, methodIndex, i, responseDescEls, responseEls));
        }

        return responses;
    }

    private static ResponseEntity parserOneResponse(int categoryIndex, int methodIndex, int responseIndex, Elements responseDescEls, Elements responseEls) {
        String responseDesc = responseDescEls.get(responseIndex).text().trim();// 响应的描述
        String responseText = responseEls.get(responseIndex).text().trim();// 响应报文

        // 默认两个(responseDesc, responseText)都有数据
        int separatorIndex = responseText.indexOf("{");
        String responseHeader = responseText.substring(0, separatorIndex).trim();
        String responseContent = responseText.substring(separatorIndex).trim();

        ResponseEntity response = new ResponseEntity(responseDesc, responseHeader, responseContent);
        return response;
    }

}
