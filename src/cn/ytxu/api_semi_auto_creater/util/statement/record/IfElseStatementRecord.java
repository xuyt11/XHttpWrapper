package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementEngine;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.List;

/**
 * Created by ytxu on 2016/7/18.
 */
public class IfElseStatementRecord extends StatementRecord {

    public IfElseStatementRecord(Statement statement, String startTagContent, List<String> contents) {
        super(statement, startTagContent, contents);
        if (contents != null) {
            this.subs = new StatementEngine(contents).start();
        }
    }



    @Override
    public void parse() {
        // TODO
    }

}
