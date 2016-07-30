package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.List;

/**
 * Created by ytxu on 2016/7/18.
 */
public class IfElseStatementRecord extends StatementRecord {
    private static final String ElseTag = "<if_else>";

    private List<String> ifContents;
    private List<String> elseContents;

    private List<StatementRecord> ifRecords;
    private List<StatementRecord> elseRecords;

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

    private void setupIfData() {
        ifContents = contents;
        getIfRecords();
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


    @Override
    public void parse() {
        // TODO
    }

    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainEntity retain) {
        return null;
    }

}
