package cn.ytxu.api_semi_auto_creater.util.statement;

import cn.ytxu.api_semi_auto_creater.util.statement.record.*;
import cn.ytxu.api_semi_auto_creater.util.statement.record.if_else.IfElseStatementRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 表达式枚举
 */
public enum Statement {
    text("普通的文本", null, null) {
        @Override
        public boolean isThisType(String content) {
            return false;
        }

        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            records.add(new TextStatementRecord(this, content));
        }

        @Override
        public boolean isEndTag(String content) {
            throw new IllegalAccessError("text type can not has end tag");
        }
    },
    foreach("循环", Pattern.compile("(<foreach each=\")\\w+(\">)"), "</foreach>") {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> foreachContents = getContents(contentIterator);
            records.add(new ForeachStatementRecord(this, content, foreachContents));
        }
    },
    retain("保留代码区域", Pattern.compile("(<retain type=\")\\w+(\"/>)"), null) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            records.add(new RetainStatementRecord(this, content));
        }

        @Override
        public boolean isEndTag(String content) {
            throw new IllegalAccessError("retain type can not has end tag");
        }
    },
    list("在foreach中的循环，防止foreach循环嵌套", Pattern.compile("(<list each=\")\\w+(\">)"), "</list>") {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> listContents = getContents(contentIterator);
            records.add(new ListStatementRecord(this, content, listContents));
        }
    },
    list_single_line("单行循环，防止foreach循环嵌套", Pattern.compile("(<list each=\")\\w+(\")( singleLine).+(/>)"), null){
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            records.add(new ListSingleLineStatementRecord(this, content));
        }
    },
    if_else("if else 条件判断", Pattern.compile("(<if isTure=\")\\w+(\">)"), "</if_end>") {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> ifElseContents = getContents(contentIterator);
            records.add(new IfElseStatementRecord(this, content, ifElseContents));
        }
    },
    list_replace("替换数组的文本", Pattern.compile("(<list_replace each=\")\\w+(\" replace_key=\")\\w+(\" list_value=\")\\w+(\">)"), "</list_replace>") {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> listReplaceContents = getContents(contentIterator);
            records.add(new ListReplaceStatementRecord(this, content, listReplaceContents));
        }
    };

    private final String tag;
    private final Pattern pattern;// 判断是否为该分类
    private final String endTag;// 结束标签

    Statement(String tag, Pattern pattern, String endTag) {
        this.tag = tag;
        this.pattern = pattern;
        this.endTag = endTag;
    }

    public boolean isThisType(String content) {
        return pattern.matcher(content).find();
    }

    /**
     * @param content
     * @param records
     */
    public abstract void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator);

    List<String> getContents(Iterator<String> contentIterator) {
        List<String> contents = new ArrayList<>();
        while (contentIterator.hasNext()) {
            String content = contentIterator.next();
            if (isEndTag(content)) {
                break;
            }
            contents.add(content);
        }
        return contents;
    }

    boolean isEndTag(String content) {
        return endTag.equals(content.trim());
    }


    public static Statement get(String content) {
        for (Statement s : Statement.values()) {
            if (s.isThisType(content)) {
                return s;
            }
        }
        return text;
    }

}

