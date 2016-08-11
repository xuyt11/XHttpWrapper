package cn.ytxu.api_semi_auto_creater.util.statement.record.if_else;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * if else 表达式的判断类型枚举
 */
public enum IfElseCondition {
    Boolean("布尔类型判断", "isTrue=\"", "\"", Pattern.compile("(isTrue=\")\\w+(\")"));
//        String("字符串类型判断");

    private final String tag;
    private final String pattern_front;
    private final String pattern_end;
    private final Pattern pattern;

    IfElseCondition(String tag, String pattern_front, String pattern_end, Pattern pattern) {
        this.tag = tag;
        this.pattern_front = pattern_front;
        this.pattern_end = pattern_end;
        this.pattern = pattern;
    }

    public String getMethodName(String startTagContent) {
        Matcher matcher = pattern.matcher(startTagContent);
        // be sure to match, but also need call matcher.find()
        matcher.find();
        String group = matcher.group();
        int methodNameStart = pattern_front.length();
        int methodNameEnd = group.length() - pattern_end.length();
        String methodName = group.substring(methodNameStart, methodNameEnd);
        return methodName;
    }

    public static IfElseCondition get(String startTagContent) {
        for (IfElseCondition condition : IfElseCondition.values()) {
            if (condition.isThisConditionType(startTagContent)) {
                return condition;
            }
        }
        throw new IllegalStateException("can not find condition, and this content is " + startTagContent);
    }

    /**
     * 是否为该判断类型
     */
    private boolean isThisConditionType(String startTagContent) {
        Matcher matcher = pattern.matcher(startTagContent);
        return matcher.find();
    }
}