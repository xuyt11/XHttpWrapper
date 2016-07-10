package cn.ytxu.api_semi_auto_creater.creater;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytxu on 2016/7/10.
 * 解析*-property.xml文件中的数据
 */
public class Property {
    private String key;
    private String desc;
    private String val;
    private List<Value> values;

    public static class Value {
        private String key;// 不同平台
        private String val;
    }

    private static final String PropertyXmlName = "NewChama-android-property.xml";

    private static Map<String, Property> data;
    private static Property property;
    private static Value value;

    public static void init() {
        try {
            InputStream xml = getXml();
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

    private static InputStream getXml() {
        InputStream xml = Property.class.getClassLoader().getResourceAsStream(PropertyXmlName);
        return xml;
    }

    private static XmlPullParser getPullParser(InputStream xml) throws XmlPullParserException {
        // 获得pull解析器工厂
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        // 获取XmlPullParser的实例
        XmlPullParser pullParser = factory.newPullParser();
        // 设置需要解析的XML数据
        pullParser.setInput(xml, "UTF-8");
        return pullParser;
    }

    private static void loopParse(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        int event = pullParser.getEventType();// 取得事件
        while (event != XmlPullParser.END_DOCUMENT) {// 若为解析到末尾，文档结束
            String nodeName = pullParser.getName();// 节点名称
            parse(pullParser, event, nodeName);
            event = pullParser.next(); // 下一个标签
        }
    }

    private static void parse(XmlPullParser pullParser, int event, final String nodeName) throws XmlPullParserException, IOException {
        switch (event) {
            case XmlPullParser.START_DOCUMENT: // 文档开始
                initData();
                break;
            case XmlPullParser.START_TAG: // 标签开始
                if ("property".equals(nodeName)) {
                    initProperty(pullParser);
                } else if ("value".equals(nodeName)) {
                    setValues(pullParser);
                }
                break;
            case XmlPullParser.END_TAG: // 标签结束
                if ("property".equals(nodeName)) {
                    addProperty();
                } else if ("value".equals(nodeName)) {
                    addValues();
                }
                break;
        }
    }

    private static void initData() {
        data = new HashMap<>();
    }

    private static void initProperty(XmlPullParser pullParser) {
        property = new Property();
        property.key = pullParser.getAttributeValue(null, "key");
        property.desc = pullParser.getAttributeValue(null, "desc");
        property.val = pullParser.getAttributeValue(null, "val");
    }

    private static void setValues(XmlPullParser pullParser) throws XmlPullParserException, IOException {
        if (property.values == null) {
            property.values = new ArrayList<>();
        }
        value = new Value();
        value.key = pullParser.getAttributeValue(null, "key");
        value.val = pullParser.getAttributeValue(null, "val");
//        value.key = pullParser.getAttributeValue(0);
//        value.val = pullParser.getAttributeValue(1);
//        value.val = pullParser.nextText();
    }

    private static void addValues() {
        property.values.add(value);
        value = null;
    }

    private static void addProperty() {
        data.put(property.key, property);
        property = null;
    }

    private static void close(InputStream xml) throws IOException {
        if (xml != null) {
            xml.close();
        }
    }


    public static void get(String key) {
        // TODO implement
//        return data.get(key);
        return;
    }
}
