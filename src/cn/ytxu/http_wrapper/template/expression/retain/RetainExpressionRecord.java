package cn.ytxu.http_wrapper.template.expression.retain;

import cn.ytxu.http_wrapper.common.enums.RetainType;
import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;

import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/21.
 * 保留区域表达式的记录
 */
public class RetainExpressionRecord extends ExpressionRecord {
    public static final Pattern[] PATTERNS = {Pattern.compile("(<t:retain type=\")\\w+(\"/>)")};

    // type tag
    private static final String PATTERN_FRONT = "type=\"";
    private static final String PATTERN_END = "\"";
    private static final Pattern PATTERN = Pattern.compile("(type=\")\\w+(\")");

    private RetainType type;

    public RetainExpressionRecord(String startLineContent) {
        super(ExpressionEnum.retain, startLineContent, false);
    }


    //********************** parse content to record **********************
    @Override
    protected boolean convertContentsIfHas(ListIterator<String> contentListIterator) {
        // do nothing...
        return true;
    }

    @Override
    protected boolean isEndTagLine(String content) {
        throw new IllegalAccessError("retain type not has end tag");
    }


    //********************** loop parse record **********************
    @Override
    public void parseRecordAndSubRecords() {
        String retainTypeName = getRetainTypeName();
        type = RetainType.get(retainTypeName);
    }

    private String getRetainTypeName() {
        Matcher matcher = PATTERN.matcher(startLineContent);
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
