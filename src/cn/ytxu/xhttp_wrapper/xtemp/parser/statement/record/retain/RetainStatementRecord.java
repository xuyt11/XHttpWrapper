package cn.ytxu.xhttp_wrapper.xtemp.parser.statement.record.retain;

import cn.ytxu.xhttp_wrapper.common.enums.RetainType;
import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.Statement;
import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.StatementRecord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/7/18.
 */
public class RetainStatementRecord extends StatementRecord {
    private static final String PATTERN_FRONT = "type=\"";
    private static final String PATTERN_END = "\"";
    private static final Pattern PATTERN = Pattern.compile("(type=\")\\w+(\")");

    private RetainType type;

    public RetainStatementRecord(Statement statement, String startTagContent) {
        super(statement, startTagContent, null);
    }


    @Override
    public void parse() {
        String retainTypeName = getRetainTypeName();
        type = RetainType.get(retainTypeName);
    }

    private String getRetainTypeName() {
        Matcher matcher = PATTERN.matcher(startTagContent);
        // be sure to match, but also need call matcher.find()
        matcher.find();
        String group = matcher.group();
        int methodNameStart = PATTERN_FRONT.length();
        int methodNameEnd = group.length() - PATTERN_END.length();
        return group.substring(methodNameStart, methodNameEnd);
    }

    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        return type.getFormatRetainContent(retain);
    }

}
