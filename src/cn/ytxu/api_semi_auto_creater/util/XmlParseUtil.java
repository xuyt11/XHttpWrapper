package cn.ytxu.api_semi_auto_creater.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ytxu on 2016/7/16.
 */
public class XmlParseUtil {
    private static final String PropertyXmlName = "NewChama-android-property.xml";
    private static final String HttpApiXmlName = "NewChama-android-publicApi-builder.xml";

    private String xmlName;
    private Callback callback;

    public XmlParseUtil() {
    }

    public void parseProperty(Callback callback) {
        init(PropertyXmlName, callback);
    }

    public void parseHttpApi(Callback callback) {
        init(HttpApiXmlName, callback);
    }

    private void init(String xmlName, Callback callback) {
        this.xmlName = xmlName;
        this.callback = callback;
        InputStream xml = getXml();
        parseInputStream(xml, callback);
    }

    public void parseInputStream(InputStream xml, Callback callback) {
        try {
            XmlPullParser pullParser = getPullParser(xml);
            loopParse(pullParser);
            close(xml);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private InputStream getXml() {
        InputStream xml = this.getClass().getClassLoader().getResourceAsStream(xmlName);
        return xml;
    }

    private XmlPullParser getPullParser(InputStream xml) throws XmlPullParserException {
        // 获得pull解析器工厂
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        // 获取XmlPullParser的实例
        XmlPullParser pullParser = factory.newPullParser();
        // 设置需要解析的XML数据
        pullParser.setInput(xml, "UTF-8");
        return pullParser;
    }

    private void loopParse(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        int event = pullParser.getEventType();// 取得事件
        while (event != XmlPullParser.END_DOCUMENT) {// 若为解析到末尾，文档结束
            String nodeName = pullParser.getName();// 节点名称
            parse(pullParser, event, nodeName);
            event = pullParser.next(); // 下一个标签
        }
    }

    private void parse(XmlPullParser pullParser, int event, final String nodeName) throws XmlPullParserException, IOException {
        if (callback == null) {
            return;
        }
        switch (event) {
            case XmlPullParser.START_DOCUMENT: // 文档开始
                callback.startDocument(pullParser, nodeName);
                break;
            case XmlPullParser.START_TAG: // 标签开始
                callback.startTag(pullParser, nodeName);
                break;
            case XmlPullParser.END_TAG: // 标签结束
                callback.endTag(pullParser, nodeName);
                break;
        }
    }

    private void close(InputStream xml) throws IOException {
        if (xml != null) {
            xml.close();
        }
    }


    public interface Callback {
        /**
         * 文档开始
         */
        void startDocument(XmlPullParser pullParser, String nodeName);

        /**
         * 标签开始
         */
        void startTag(XmlPullParser pullParser, String nodeName);

        /**
         * 标签结束
         */
        void endTag(XmlPullParser pullParser, String nodeName);
    }

}

