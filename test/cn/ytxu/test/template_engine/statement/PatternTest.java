package cn.ytxu.test.template_engine.statement;

import cn.ytxu.http_wrapper.template_engine.parser.statement.record.helper.PatternHelper;

import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/12/16.
 */
public class PatternTest {

    private static final PatternHelper.PatternModel EACH_MODEL
            = new PatternHelper.PatternModel("each=\"", "\"", Pattern.compile("(each=\")\\w+(\")"));
    private static final PatternHelper.PatternModel VALUE_MODEL
            = new PatternHelper.PatternModel("value=\"", "\"", Pattern.compile("(value=\")\\.+(\")"));

    // start end 可以没有
    private static final PatternHelper.PatternModel START_MODEL
            = new PatternHelper.PatternModel("start=\"", "\"", Pattern.compile("(start=\")\\w+(\")"));
    private static final PatternHelper.PatternModel END_MODEL
            = new PatternHelper.PatternModel("end=\"", "\"", Pattern.compile("(end=\")\\w+(\")"));

    private static final String startTagContent = "\t    <t:list each=\"RESTful_fields\" singleLine start=\"   \t    \" value=\"String ${RESTful_field_name}, \" end=\"\"/>";


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
