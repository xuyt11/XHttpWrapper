package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.api_semi_auto_creater.util.ReflectiveUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/7/18.
 * 整体逻辑与Foreach一样，只是我定义的是foreach能包含list、list_replace、list_singleLine等等遍历标签
 */
public class ListStatementRecord extends StatementRecord {
    private static final String PATTERN_FRONT = "each=\"";
    private static final String PATTERN_END = "\">";
    private static final Pattern PATTERN = Pattern.compile("(each=\")\\w+(\">)");

    private String methodName;

    public ListStatementRecord(Statement statement, String startTagContent, List<String> contents) {
        super(statement, startTagContent, contents);
        if (contents != null) {
            this.subs = StatementRecord.getRecords(contents);
        }
    }


    @Override
    public void parse() {
        getMethodName();
        parseSubs();
    }

    private void getMethodName() {
        Matcher matcher = PATTERN.matcher(startTagContent);
        // be sure to match, but also need call matcher.find()
        matcher.find();
        String group = matcher.group();
        int methodNameStart = PATTERN_FRONT.length();
        int methodNameEnd = group.length() - PATTERN_END.length();
        methodName = group.substring(methodNameStart, methodNameEnd);
    }

    private void parseSubs() {
        for (StatementRecord sub : subs) {
            sub.parse();
        }
    }

    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainEntity retain) {
        List subModels = ReflectiveUtil.getList(reflectModel, methodName);
        StringBuffer foreachBuffer = new StringBuffer();
        for (Object subModel : subModels) {
            for (StatementRecord sub : subs) {
                foreachBuffer.append(sub.getWriteBuffer(subModel, retain));
            }
        }
        return foreachBuffer;
    }
    
}
