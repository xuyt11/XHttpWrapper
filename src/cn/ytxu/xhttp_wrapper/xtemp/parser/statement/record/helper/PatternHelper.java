package cn.ytxu.xhttp_wrapper.xtemp.parser.statement.record.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/8/12.
 */
public class PatternHelper {
    private PatternHelper() {
    }

    public static boolean matchThisPattern(PatternModel model, String startTagContent) {
        Matcher matcher = model.pattern.matcher(startTagContent);
        return matcher.find();
    }

    public static String getPatternValue(PatternModel model, String startTagContent) {
        Matcher matcher = model.pattern.matcher(startTagContent);
        // be sure to match, but also need call matcher.find()
        if (!matcher.find()) {
            System.out.println("front : " + model.front + ", start tag content :" + startTagContent);
        }
        String group = matcher.group();
        int methodNameStart = model.front.length();
        int methodNameEnd = group.length() - model.end.length();
        return group.substring(methodNameStart, methodNameEnd);
    }

    public static class PatternModel {
        private String front, end;
        private Pattern pattern;

        public PatternModel(String front, String end, Pattern pattern) {
            this.front = front;
            this.end = end;
            this.pattern = pattern;
        }
    }


    private static final PatternModel EACH_MODEL
            = new PatternModel("each=\"", "\"", Pattern.compile("(each=\")\\w+(\")"));
    private static final PatternModel VALUE_MODEL
            = new PatternModel("value=\"", "\"", Pattern.compile("(value=\")\\.+(\")"));

    // start end 可以没有
    private static final PatternModel START_MODEL
            = new PatternModel("start=\"", "\"", Pattern.compile("(start=\")\\w+(\")"));
    private static final PatternModel END_MODEL
            = new PatternModel("end=\"", "\"", Pattern.compile("(end=\")\\w+(\")"));

    private static final String startTagContent = "\t    <list each=\"RESTful_fields\" singleLine start=\"   \t    \" value=\"String ${RESTful_field_name}, \" end=\"\"/>";

    public static void main(String... args) {
        String methodName = PatternHelper.getPatternValue(EACH_MODEL, startTagContent);
        String value = PatternHelper.getPatternValue(VALUE_MODEL, startTagContent);

        boolean hasStart = PatternHelper.matchThisPattern(START_MODEL, startTagContent);
        if (hasStart) {
            String start = PatternHelper.getPatternValue(START_MODEL, startTagContent);
        }

        boolean hasEnd = PatternHelper.matchThisPattern(END_MODEL, startTagContent);
        if (hasEnd) {
            String end = PatternHelper.getPatternValue(END_MODEL, startTagContent);
        }

    }

}
