package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

/**
 * Created by ytxu on 2016/7/18.
 */
public class RetainStatementRecord extends StatementRecord {

    public RetainStatementRecord(Statement statement, String startTagContent) {
        super(statement, startTagContent, null);
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
