package cn.ytxu.xhttp_wrapper.xtemp.parser.statement.record.list_single_line;

import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.record.TextStatementRecord;
import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.record.retain.RetainModel;
import cn.ytxu.xhttp_wrapper.xtemp.parser.util.ReflectiveUtil;
import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.Statement;
import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.StatementRecord;
import cn.ytxu.util.ListUtil;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/8/11.
 * 单行的循环
 */
public class ListSingleLineStatementRecord extends StatementRecord {

    private LSLSRParser parser;

    public ListSingleLineStatementRecord(Statement statement, String startTagContent, List<String> listSingleLineContents) {
        super(statement, startTagContent, listSingleLineContents);
        if (contents.size() < 1) {
            throw new IllegalArgumentException("list single line statement record must have one sub...");
        }
        parser = new LSLSRParser(startTagContent, contents);
    }

    @Override
    public void parse() {
        parser.parse();
    }

    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        List subModels = ReflectiveUtil.getList(reflectModel, parser.getMethodName());
        StringBuffer listSingleLineBuffer = new StringBuffer();

        if (ListUtil.isEmpty(subModels)) {
            return listSingleLineBuffer;
        }

        for (Object subModel : subModels) {
            TextStatementRecord record = parser.getEachTempStatementRecord();
            StringBuffer buffer = record.getNormalWriteBuffer(subModel, retain);
            listSingleLineBuffer.append(buffer);
        }

        if (Objects.nonNull(parser.getStart())) {
            listSingleLineBuffer.insert(0, parser.getStart());
        }

        if (Objects.nonNull(parser.getEnd())) {
            listSingleLineBuffer.append(parser.getEnd());
        }

        return listSingleLineBuffer.append(NextLine);
    }
}