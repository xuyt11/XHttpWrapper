package cn.ytxu.api_semi_auto_creater.creater;

import cn.ytxu.api_semi_auto_creater.config.OSPlatform;
import cn.ytxu.api_semi_auto_creater.util.XmlParseUtil;
import org.xmlpull.v1.XmlPullParser;

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
            throw new NullPointerException("can not found target property, key:" + key);
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
        throw new IllegalStateException("can not found valite data in this os");
    }

    static class PropertyParser extends XmlParseUtil implements XmlParseUtil.Callback {
        private Property property;
        private Property.Value value;

        public void init() {
            parseProperty(this);
        }

        @Override
        public void startDocument(XmlPullParser pullParser, String nodeName) {
            initData();
        }

        @Override
        public void startTag(XmlPullParser pullParser, String nodeName) {
            if ("property".equals(nodeName)) {
                initProperty(pullParser);
            } else if ("value".equals(nodeName)) {
                setValues(pullParser);
            }
        }

        @Override
        public void endTag(XmlPullParser pullParser, String nodeName) {
            if ("property".equals(nodeName)) {
                addProperty();
            } else if ("value".equals(nodeName)) {
                addValues();
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

        private void setValues(XmlPullParser pullParser) {
            if (property.values == null) {
                property.values = new ArrayList<>();
            }
            value = new Value();
            value.key = pullParser.getAttributeValue(null, "key");
            value.val = pullParser.getAttributeValue(null, "val");
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
            } catch (NullPointerException ignore) {
                return;
            }

            str = str.replace(group, value);
        }

    }

}
