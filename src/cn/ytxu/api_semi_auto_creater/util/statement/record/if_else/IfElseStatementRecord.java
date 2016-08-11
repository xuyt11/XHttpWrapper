package cn.ytxu.api_semi_auto_creater.util.statement.record.if_else;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.api_semi_auto_creater.util.ReflectiveUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/7/18.
 */
public class IfElseStatementRecord extends StatementRecord {
    private static final String ElseTag = "<if_else>";

    private List<String> ifContents;
    private List<String> elseContents;

    private List<StatementRecord> ifRecords;
    private List<StatementRecord> elseRecords;

    private String methodName;

    public IfElseStatementRecord(Statement statement, String startTagContent, List<String> contents) {
        super(statement, startTagContent, contents);
        setupData();
    }

    private void setupData() {
        try {
            int elseTagIndex = getElseTagIndex();
            setupIfElseData(elseTagIndex);
        } catch (RuntimeException ignore) {// 只有if表达式
            setupIfData();
        }
    }

    private int getElseTagIndex() {
        for (int i = 0, size = contents.size(); i < size; i++) {
            String content = contents.get(i);
            if (isElseTag(content)) {
                return i;
            }
        }
        throw new RuntimeException("not have else statement");
    }

    private boolean isElseTag(String content) {
        return ElseTag.equals(content.trim());
    }

    private void setupIfElseData(int elseTagIndex) {
        ifContents = contents.subList(0, elseTagIndex);
        elseContents = contents.subList(elseTagIndex + 1, contents.size());
        getIfRecords();
        getElseRecords();
    }

    private void getIfRecords() {
        if (ifContents != null) {
            this.ifRecords = StatementRecord.getRecords(ifContents);
        }
    }

    private void getElseRecords() {
        if (elseContents != null) {
            this.elseRecords = StatementRecord.getRecords(elseContents);
        }
    }

    private void setupIfData() {
        ifContents = contents;
        getIfRecords();
        elseRecords = Collections.EMPTY_LIST;
    }


    @Override
    public void parse() {
        methodName = Condition.Boolean.getMethodName(startTagContent);
    }

    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainEntity retain) {
        boolean isTrue = ReflectiveUtil.getBoolean(reflectModel, methodName);
        return getWriteBuffer(reflectModel, retain, isTrue ? ifRecords : elseRecords);
    }

    private StringBuffer getWriteBuffer(Object reflectModel, RetainEntity retain, List<StatementRecord> records) {
        StringBuffer buffer = new StringBuffer();
        for (StatementRecord record : records) {
            buffer.append(record.getWriteBuffer(reflectModel, retain));
        }
        return buffer;
    }


    public static enum Condition {
        Boolean("布尔类型判断", "isTrue=\"", "\"", Pattern.compile("(isTrue=\")\\w+(\")"));
//        String("字符串类型判断");

        private final String tag;
        private final String pattern_front;
        private final String pattern_end;
        private final Pattern pattern;

        Condition(String tag, String pattern_front, String pattern_end, Pattern pattern) {
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
    }
}
