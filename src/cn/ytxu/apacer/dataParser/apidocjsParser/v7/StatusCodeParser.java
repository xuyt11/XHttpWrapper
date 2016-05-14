package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.apacer.entity.StatusCodeEntity;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 状态码解析
 * 2016-03-30
 */
public class StatusCodeParser {
    private static final String CSS_QUERY_GET_ALL_FIELD = "div > article > table > tbody > tr";
    private static final String CSS_QUERY_GET_CONTENT = "td";

    public static List<StatusCodeEntity> parseStatusCodes(Element sectionEle) {
        Elements statusCodeEles = JsoupParserUtil.getEles(sectionEle, CSS_QUERY_GET_ALL_FIELD);

        if (null == statusCodeEles || statusCodeEles.size() <= 0) {
            return null;
        }

        List<StatusCodeEntity> statusCodes = new ArrayList<>(statusCodeEles.size());

        for (Iterator<Element> iterator = statusCodeEles.iterator(); iterator.hasNext(); ) {
            StatusCodeEntity statusCode = getStatusCode(iterator.next());
            statusCodes.add(statusCode);
        }

        return statusCodes;
    }

    /**
     * status code descprition:<br>
     * (0, '')<br>
     * (1, '登录状态已过期，请重新登入')<br>
     * (5, '服务器错误') # 5XX 服务器错误
     */
    private static StatusCodeEntity getStatusCode(Element statusCodeEle) {
        Elements tdEls = getTdEles(statusCodeEle);

        String statusCodeName = getStatusCodeName(tdEls);
        String description = getDescription(tdEls);
        int separatorIndex = getSeparatorIndex(description);
        String statusCodeDesc = getStatusCodeDesc(description, separatorIndex);
        String statusCodeNumber = getStatusCodeNumber(description, separatorIndex);

        return new StatusCodeEntity(statusCodeName, statusCodeDesc, statusCodeNumber);
    }

    private static Elements getTdEles(Element statusCodeEle) {
        Elements tdEls = JsoupParserUtil.getEles(statusCodeEle, CSS_QUERY_GET_CONTENT);
        if (null == tdEls) {
            throw new RuntimeException("tdEls is null");
        }

        if (tdEls.size() != 2) {
            throw new RuntimeException("the size of tdEls is not 2");
        }
        return tdEls;
    }

    private static String getStatusCodeName(Elements tdEls) {
        return JsoupParserUtil.getText(tdEls.get(0));
    }

    private static String getDescription(Elements tdEls) {
        String description = JsoupParserUtil.getText(tdEls.get(1));
        if (null == description) {
            throw new RuntimeException("the description message is null");
        }
        description = description.substring(1);// 真有不是以)结尾的状态码描述:第五,六两个就是
        return description;
    }

    private static int getSeparatorIndex(String description) {
        int separatorIndex = description.indexOf(",");
        if (separatorIndex <= 0) {// 小于0:没有找到; 等于0:没有statusCode
            throw new RuntimeException("the separatorIndex is not bigger 0");
        }
        return separatorIndex;
    }

    private static String getStatusCodeDesc(String description, int separatorIndex) {
        return description.substring(separatorIndex + 1).trim();
    }

    private static String getStatusCodeNumber(String description, int separatorIndex) {
        String statusCodeStr = description.substring(0, separatorIndex).trim();
        try {
            Long.parseLong(statusCodeStr);
        } catch (NumberFormatException e) {// 不能转换成数字,有问题
            e.printStackTrace();
            throw new RuntimeException("the status code string can not convert long type value");
        }
        return statusCodeStr;
    }

}
