package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.List;

/**
 * Created by ytxu on 2016/7/18.
 */
public class ListReplaceStatementRecord extends StatementRecord {

    public ListReplaceStatementRecord(Statement statement, String startTagContent, List<String> contents) {
        super(statement, startTagContent, contents);
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
