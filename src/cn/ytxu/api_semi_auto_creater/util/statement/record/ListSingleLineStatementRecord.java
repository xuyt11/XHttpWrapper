package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.List;

/**
 * Created by ytxu on 2016/8/11.
 * 单行的循环
 */
public class ListSingleLineStatementRecord extends StatementRecord {

    private String start, value, end;// 在遍历完成后，需要将start+values+end拼接返回

    public ListSingleLineStatementRecord(Statement statement, String startTagContent) {
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