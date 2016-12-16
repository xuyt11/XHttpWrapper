package cn.ytxu.http_wrapper.template_engine.parser.statement.record.helper;

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

}
