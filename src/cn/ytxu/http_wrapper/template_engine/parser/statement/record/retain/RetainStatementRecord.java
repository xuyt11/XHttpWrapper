package cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain;

import cn.ytxu.http_wrapper.common.enums.RetainType;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementEnum;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementRecord;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/7/18.
 */
public class RetainStatementRecord extends StatementRecord {
    private static final String PATTERN_FRONT = "type=\"";
    private static final String PATTERN_END = "\"";
    private static final Pattern PATTERN = Pattern.compile("(type=\")\\w+(\")");
//    private static final String PATTERN_FRONT = "<t:retain type=\"";
//    private static final String PATTERN_END = "\"/>";
//    private static final Pattern PATTERN = Pattern.compile("(<t:retain type=\")\\w+(\"/>)");


    private RetainType type;

    public RetainStatementRecord(StatementEnum statement, String startTagContent) {
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
