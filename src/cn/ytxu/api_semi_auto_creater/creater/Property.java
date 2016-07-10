package cn.ytxu.api_semi_auto_creater.creater;

import cn.ytxu.api_semi_auto_creater.config.OSPlatform;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/7/10.
 * 解析*-property.xml文件中的数据
 */
public class Property {
    private static Map<String, Property> data;

    private String key;
    private String desc;
    private String val;
    private List<Value> values;

    static class Value {
        private String key;// 不同平台
        private String val;
    }

    static {
        new PropertyParser().init();
    }

    public static String getValue(String key) {
        Property property = data.get(key);
        if (property == null) {// not found target property
            throw new RuntimeException("can not found target property, key:" + key);
        }

        if (property.val != null) {
            return property.val;
        }

        String osName = OSPlatform.getCurrentOSPlatform().getOsName();
        for (Value value : property.values) {
            if (osName.equals(value.key)) {
                return value.val;
            }
        }
        throw new RuntimeException("can not found valite data in this os");
    }

    static class PropertyParser {
        private static final String PropertyXmlName = "NewChama-android-property.xml";

        private Property property;
        private Property.Value value;

        public void init() {
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

        private InputStream getXml() {
            InputStream xml = Property.class.getClassLoader().getResourceAsStream(PropertyXmlName);
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

        private void initData() {
            data = new HashMap<>();
        }

        private void initProperty(XmlPullParser pullParser) {
            property = new Property();
            property.key = pullParser.getAttributeValue(null, "key");
            property.desc = pullParser.getAttributeValue(null, "desc");
            property.val = pullParser.getAttributeValue(null, "val");
        }

        private void setValues(XmlPullParser pullParser) throws XmlPullParserException, IOException {
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

        private void addValues() {
            property.values.add(value);
            value = null;
        }

        private void addProperty() {
            convert();

            data.put(property.key, property);
            property = null;
        }

        /**
         * 转换为真实可用的东东
         */
        private void convert() {
            if (notHasPreviousElement()) {
                return;
            }

            if (property.val != null) {
                property.val = new RegConvert(property.val).start();
            } else {// property.values must be have
                List<Value> values = property.values;
                for (Value value : values) {
                    value.val = new RegConvert(value.val).start();
                }
            }
        }

        private boolean notHasPreviousElement() {
            return data.size() <= 0;
        }

        private void close(InputStream xml) throws IOException {
            if (xml != null) {
                xml.close();
            }
        }

    }

    static class RegConvert {
        private static final String regex = "\\$\\{\\w+\\}";

        private Pattern p = Pattern.compile(regex);
        private String str;
        private List<String> groups;

        public RegConvert(String str) {
            this.str = str;
            groups = new ArrayList<>();
        }

        public String start() {
            findAndAddMatchedGroups();
            loopConvert();
            return str;
        }

        private void findAndAddMatchedGroups() {
            Matcher m = p.matcher(str);
            while (m.find()) {
                addGroup(m);
            }
        }

        private void addGroup(Matcher m) {
            int start = m.start();
            int end = m.end();
            String group = m.group();
            System.out.println("group start:" + start + ", group end:" + end + ", group:" + group);
            groups.add(group);
        }

        private void loopConvert() {
            for (String group : groups) {
                convert(group);
            }
        }

        private void convert(String group) {
            String key = group.substring(2, group.length() - 1);
            String value;
            try {
                value = getValue(key);
            } catch (Exception ignore) {
                return;
            }

            str = str.replace(group, value);
        }

    }

}
