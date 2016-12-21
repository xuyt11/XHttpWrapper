package cn.ytxu.http_wrapper.template_engine.parser.statement.record.if_else;

import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementEnum;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementRecord;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/7/18.
 */
public class IfElseStatementRecord extends StatementRecord {
    private static final String ElseTag = "<t:if_else>";

    private List<String> ifContents;
    private List<String> elseContents;

    private List<StatementRecord> ifRecords;
    private List<StatementRecord> elseRecords;

    private IfElseCondition ifElseCondition;
    private String methodName;

    public IfElseStatementRecord(StatementEnum statement, String startTagContent, List<String> contents) {
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
        ifElseCondition = IfElseCondition.get(startTagContent);
        methodName = ifElseCondition.getMethodName(startTagContent);
        parseSubs();
    }

    private void parseSubs() {
        parseSubRecords(ifRecords);
        parseSubRecords(elseRecords);
    }

    private void parseSubRecords(List<StatementRecord> records) {
        for (StatementRecord sub : records) {
            sub.parse();
        }
    }

    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        boolean isTrue = ifElseCondition.getBoolean(reflectModel, methodName);
        return getWriteBuffer(reflectModel, retain, isTrue ? ifRecords : elseRecords);
    }

    private StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain, List<StatementRecord> records) {
        StringBuffer buffer = new StringBuffer();
        for (StatementRecord record : records) {
            buffer.append(record.getWriteBuffer(reflectModel, retain));
        }
        return buffer;
    }

}
