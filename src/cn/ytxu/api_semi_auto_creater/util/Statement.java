package cn.ytxu.api_semi_auto_creater.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * 表达式枚举
 */
public enum Statement {
    text("普通的文本", null) {
        @Override
        public boolean isThisType(String content) {
            return false;
        }

        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records) {
            StatementRecord record = StatementRecord.getText(content);
            records.add(record);
        }

        @Override
        public boolean isEndTag(String content) {
            throw new IllegalAccessError("text type can not has end tag");
        }
    },
    foreach("循环", Pattern.compile("(<foreach each=\")\\w+(\">)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records) {

        }

        @Override
        public boolean isEndTag(String content) {
            return false;
        }
    },
    retain("保留代码区域", Pattern.compile("(<retain type=\")\\w+(\"/>)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records) {
            StatementRecord record = StatementRecord.getRetain(content);
            records.add(record);
        }
        @Override
        public boolean isEndTag(String content) {
            throw new IllegalAccessError("retain type can not has end tag");
        }
    },
    list("在foreach中的循环，防止foreach循环嵌套", Pattern.compile("(<list each=\")\\w+(\">)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records) {

        }
        @Override
        public boolean isEndTag(String content) {
            return false;
        }
    },
    if_else("if else 条件判断", Pattern.compile("(<if isTure=\")\\w+(\">)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records) {

        }
        @Override
        public boolean isEndTag(String content) {
            return false;
        }
    };

    private final String tag;
    private final Pattern pattern;// 判断是否为该分类

    Statement(String tag, Pattern pattern) {
        this.tag = tag;
        this.pattern = pattern;
    }

    public boolean isThisType(String content) {
        return pattern.matcher(content).find();
    }

    public abstract void getAndAddRecord(String content, List<StatementRecord> records);

    public abstract boolean isEndTag(String content);

    public static Statement get(String content) {
        for (Statement s : Statement.values()) {
            if (s.isThisType(content)) {
                return s;
            }
        }
        return text;
    }

}

