package cn.ytxu.http_wrapper.template.expression.foreach;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template_engine.parser.util.ReflectiveUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/21.
 * 遍历表达式的记录
 */
public class ForeachExpressionRecord extends ExpressionRecord {
    public static final Pattern[] PATTERNS = {Pattern.compile("(<t:foreach each=\")\\w+(\">)")};// 判断是否为该分类(开始标签)

    private static final String END_TAG = "</t:foreach>";// 结束标签

    // each tag
    private static final String PATTERN_FRONT = "each=\"";
    private static final String PATTERN_END = "\">";
    private static final Pattern PATTERN = Pattern.compile("(each=\")\\w+(\">)");

    private String methodName;

    public ForeachExpressionRecord(String startLineContent) {
        super(ExpressionEnum.foreach, startLineContent, END_TAG, true);
    }


    //********************** parse content to record **********************


    //********************** loop parse record **********************
    @Override
    public void parseRecordAndSubRecords() {
        getMethodName();
        parseSubs();
    }

    private void getMethodName() {
        Matcher matcher = PATTERN.matcher(startLineContent);
        // be sure to match, but also need call matcher.find()
        matcher.find();
        String group = matcher.group();
        int methodNameStart = PATTERN_FRONT.length();
        int methodNameEnd = group.length() - PATTERN_END.length();
        methodName = group.substring(methodNameStart, methodNameEnd);
    }

    private void parseSubs() {
        for (ExpressionRecord sub : getSubRecords()) {
            sub.parseRecordAndSubRecords();
        }
    }

    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        List subModels = ReflectiveUtil.getList(reflectModel, methodName);
        StringBuffer foreachBuffer = new StringBuffer();
        for (Object subModel : subModels) {
            for (ExpressionRecord sub : getSubRecords()) {
                foreachBuffer.append(sub.getWriteBuffer(subModel, retain));
            }
        }
        return foreachBuffer;
    }
}
