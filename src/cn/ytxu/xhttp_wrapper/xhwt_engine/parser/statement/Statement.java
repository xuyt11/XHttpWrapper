package cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement;

import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.ForeachStatementRecord;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.ListStatementRecord;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.TextStatementRecord;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.if_else.IfElseCondition;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.if_else.IfElseStatementRecord;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.list_replace.ListReplaceStatementRecord;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.list_single_line.ListSingleLineStatementRecord;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.retain.RetainStatementRecord;

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
    foreach("循环", "</foreach>", Pattern.compile("(<foreach each=\")\\w+(\">)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> foreachContents = getContents(contentIterator);
            records.add(new ForeachStatementRecord(this, content, foreachContents));
        }
    },
    retain("保留代码区域", null, Pattern.compile("(<retain type=\")\\w+(\"/>)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            records.add(new RetainStatementRecord(this, content));
        }

        @Override
        public boolean isEndTag(String content) {
            throw new IllegalAccessError("retain type can not has end tag");
        }
    },
    list("在foreach中的循环，防止foreach循环嵌套", "</list>", Pattern.compile("(<list each=\")\\w+(\">)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> listContents = getContents(contentIterator);
            records.add(new ListStatementRecord(this, content, listContents));
        }
    },
    list_single_line("单行循环，防止foreach循环嵌套", "</list>", Pattern.compile("(<list each=\")\\w+(\")( singleLine>)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> listSingleLineContents = getContents(contentIterator);
            records.add(new ListSingleLineStatementRecord(this, content, listSingleLineContents));
        }
    },
    if_else("if else 条件判断", "</if_end>", IfElseCondition.PATTERNS) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> ifElseContents = getContents(contentIterator);
            records.add(new IfElseStatementRecord(this, content, ifElseContents));
        }
    },
    list_replace("替换数组的文本", "</list_replace>", Pattern.compile("(<list_replace each=\")\\w+(\" replace_key=\")\\w+(\" list_value=\")[\\p{Print}\\p{Space}]+(\">)")) {
        @Override
        public void getAndAddRecord(String content, List<StatementRecord> records, Iterator<String> contentIterator) {
            List<String> listReplaceContents = getContents(contentIterator);
            records.add(new ListReplaceStatementRecord(this, content, listReplaceContents));
        }
    };

    private final String tag;
    private final String endTag;// 结束标签
    private final Pattern[] patterns;// 判断是否为该分类

    Statement(String tag, String endTag, Pattern... patterns) {
        this.tag = tag;
        this.endTag = endTag;
        this.patterns = patterns;
    }

    public boolean isThisType(String content) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(content).find()) {
                return true;
            }
        }
        return false;
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

