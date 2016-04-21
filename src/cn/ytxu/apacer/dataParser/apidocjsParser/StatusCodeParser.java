package cn.ytxu.apacer.dataParser.apidocjsParser;

import cn.ytxu.apacer.entity.StatusCodeEntity;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态码解析
 * 2016-03-30
 */
public class StatusCodeParser {

    public static List<StatusCodeEntity> parseStatusCodes(Element sectionEle) {
        Elements trEls = sectionEle.select("div > article > table > tbody > tr");

        if (null == trEls || trEls.size() <= 0) {
            return null;
        }

        List<StatusCodeEntity> statusCodes = new ArrayList<>(trEls.size());
        for (int i = 0, count = trEls.size(); i < count; i++) {
            StatusCodeEntity statusCode = getStatusCode(trEls, i);

            statusCodes.add(statusCode);
        }

        return statusCodes;
    }

    private static StatusCodeEntity getStatusCode(Elements trEls, int i) {
        Elements tdEls = trEls.get(i).select("td");
        if (null == tdEls) {
            throw new RuntimeException("tdEls is null, in the index is " + i);
        }

        if (tdEls.size() != 2) {
            throw new RuntimeException("the size of tdEls is not 2, in the index is " + i);
        }

        String statusCodeName = tdEls.get(0).text().trim();
//            (0, '')
//            (1, '登录状态已过期，请重新登入')
        String str = tdEls.get(1).text().trim();
        if (null == str) {
            throw new RuntimeException("the description message is null, in the index is " + i);
        }

        // 真有不是以)结尾的状态码描述:第五,六两个就是
//        if (!str.startsWith("(") || !str.endsWith(")")) {
//            throw new RuntimeException("the description message is not starts with '(' or ends with ')', in the index is " + i);
//        }

        // 去除掉()
//        str = str.substring(1, str.length()-2);
        str = str.substring(1);// 真有不是以)结尾的状态码描述:第五,六两个就是

        int separatorIndex = str.indexOf(",");
        if (separatorIndex <= 0) {// 小于0:没有找到; 等于0:没有statusCode
            throw new RuntimeException("the separatorIndex is not bigger 0, in the index is " + i);
        }

        String statusCodeStr = str.substring(0, separatorIndex).trim();
        try {
            Long.parseLong(statusCodeStr);
        } catch (NumberFormatException e) {// 不能转换成数字,有问题
            e.printStackTrace();
            throw new RuntimeException("the status code string can not convert long type value, in the index is " + i);
        }

        String statusCodeDesc = str.substring(separatorIndex+1).trim();

        return new StatusCodeEntity(statusCodeName, statusCodeDesc, statusCodeStr);
    }


}
