package cn.ytxu.api_semi_auto_creater.util.statement.record.list_single_line;

import cn.ytxu.api_semi_auto_creater.util.statement.record.helper.PatternHelper;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/8/12.
 * list single line 表达式记录的解析器
 */
public class LSLSRParser {
    // methodName eachObject 必须要有
    private static final PatternHelper.PatternModel EACH_MODEL
            = new PatternHelper.PatternModel("each=\"", "\"", Pattern.compile("(each=\")\\w+(\")"));
    private static final PatternHelper.PatternModel VALUE_MODEL
            = new PatternHelper.PatternModel("value=\"", "\"", Pattern.compile("(value=\")\\.+(\")"));

    // start end 可以没有
    private static final PatternHelper.PatternModel START_MODEL
            = new PatternHelper.PatternModel("start=\"", "\"", Pattern.compile("(start=\")\\w+(\")"));
    private static final PatternHelper.PatternModel END_MODEL
            = new PatternHelper.PatternModel("end=\"", "\"", Pattern.compile("(end=\")\\w+(\")"));

    public enum SubContentType {
        eachTemp("遍历的数据模板，一定要有的", Pattern.compile("(<eachTemp value=\")\\.+(\"/>)")),
        start("数据填充后，插入到首位", Pattern.compile("(<start value=\")\\.+(\"/>)")),
        end("数据填充后，添加到末尾", Pattern.compile("(<end value=\")\\.+(\"/>)"));

        private final String tag;
        private final Pattern pattern;// 判断是否为该分类

        SubContentType(String tag, Pattern pattern) {
            this.tag = tag;
            this.pattern = pattern;
        }


    }


    private String startTagContent;
    private List<String> contents;

    private String methodName;
    private String start, value, end;// 在遍历完成后，需要将start+values+end拼接返回

    public LSLSRParser(String startTagContent, List<String> contents) {
        this.startTagContent = startTagContent;
        this.contents = contents;
    }

    public void parse() {
        methodName = PatternHelper.getPatternValue(EACH_MODEL, startTagContent);
        value = PatternHelper.getPatternValue(VALUE_MODEL, startTagContent);

        boolean hasStart = PatternHelper.matchThisPattern(START_MODEL, startTagContent);
        if (hasStart) {
            start = PatternHelper.getPatternValue(START_MODEL, startTagContent);
        }

        boolean hasEnd = PatternHelper.matchThisPattern(END_MODEL, startTagContent);
        if (hasEnd) {
            end = PatternHelper.getPatternValue(END_MODEL, startTagContent);
        }
    }

    public String getMethodName() {
        return methodName;
    }

    public String getStart() {
        return start;
    }

    public String getValue() {
        return value;
    }

    public String getEnd() {
        return end;
    }
}
