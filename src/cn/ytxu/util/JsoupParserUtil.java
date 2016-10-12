package cn.ytxu.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.File;
import java.io.IOException;

/**
 * Created by ytxu on 2016/5/14.
 */
public class JsoupParserUtil {
    /** 编码格式 */
    public static final String CharsetName = "UTF-8";

    public static Document getDocument(String apiDocHtmlPath) {
//		Connection conn = Jsoup.connect(ApiEnitity.ApiDocUrl);
//		conn.userAgent(UserAgentConfig.getWithRandom());
        try {
            Document doc = Jsoup.parse(new File(apiDocHtmlPath), CharsetName);
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ytxu document is null, so can not run...");
        }
    }

    public static Elements getEles(Document doc, String cssQuery) {
        return Selector.select(cssQuery, doc);
    }

    public static Elements getEles(Elements eles, String cssQuery) {
        return Selector.select(cssQuery, eles);
    }

    public static Elements getEles(Element ele, String cssQuery) {
        return Selector.select(cssQuery, ele);
    }

    public static Element getFirstEle(Document doc, String cssQuery) {
        return getEles(doc, cssQuery).first();
    }

    public static Element getFirstEle(Elements eles, String cssQuery) {
        return getEles(eles, cssQuery).first();
    }

    public static Element getFirstEle(Element ele, String cssQuery) {
        return getEles(ele, cssQuery).first();
    }


    public static String getText(Element ele) {
        return ele.text().trim();
    }

    public static String getText(TextNode textNode) {
        return textNode.text().trim();
    }

    public static String getAttr(Element ele, String attributeKey) {
        return ele.attr(attributeKey).trim();
    }




}
