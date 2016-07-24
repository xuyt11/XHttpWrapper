package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.api_semi_auto_creater.util.ReflectiveUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/7/18.
 */
public class ForeachStatementRecord extends StatementRecord {
    private static final String PATTERN_FRONT = "each=\"";
    private static final String PATTERN_END = "\">";
    private static final Pattern PATTERN = Pattern.compile("(each=\")\\w+(\">)");

    private String methodName;

    public ForeachStatementRecord(Statement statement, String startTagContent, List<String> contents) {
        super(statement, startTagContent, contents);
        if (contents != null) {
            this.subs = StatementRecord.getRecords(contents);
        }
    }

    @Override
    public void parse() {
        Matcher matcher = PATTERN.matcher(startTagContent);
        // be sure to match, so not need judge matcher.find()
        String group = matcher.group();
        int methodNameStart = PATTERN_FRONT.length();
        int methodNameEnd = group.length() - PATTERN_END.length();
        methodName = group.substring(methodNameStart, methodNameEnd);
    }

    @Override
    public StringBuffer getWriteBuffer(Object model) {
        List subModels = ReflectiveUtil.getList(model, methodName);
        StringBuffer foreachBuffer = new StringBuffer();
        for (Object subModel : subModels) {
            for (StatementRecord sub : subs) {
                foreachBuffer.append(sub.getWriteBuffer(subModel));
            }
        }
        return foreachBuffer;
    }


}
