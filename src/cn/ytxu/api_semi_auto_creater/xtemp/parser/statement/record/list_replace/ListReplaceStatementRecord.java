package cn.ytxu.api_semi_auto_creater.xtemp.parser.statement.record.list_replace;

import cn.ytxu.api_semi_auto_creater.xtemp.parser.statement.record.retain.RetainModel;
import cn.ytxu.api_semi_auto_creater.xtemp.parser.statement.Statement;
import cn.ytxu.api_semi_auto_creater.xtemp.parser.statement.StatementRecord;

import java.util.List;

/**
 * Created by ytxu on 2016/7/18.
 * 在内部没有任何其他标签，只是纯文本替换，所以内部都是直接转换成TextStatementRecord
 */
public class ListReplaceStatementRecord extends StatementRecord {

    private LRSRParser parser;

    public ListReplaceStatementRecord(Statement statement, String startTagContent, List<String> contents) {
        super(statement, startTagContent, contents);
    }

    @Override
    public void parse() {
        parser = new LRSRParser(startTagContent);
        parser.parse();
    }

    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        LRSRCreater creater = new LRSRCreater(parser.getMethodName(), parser.getListValueRecord(),
                parser.getReplaceContent(), contents);
        return creater.getWriteBuffer(reflectModel, retain);
    }

}
