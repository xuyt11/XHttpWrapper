package cn.ytxu.http_wrapper.template.expression.retain;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;

import java.util.ListIterator;
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

    public RetainExpressionRecord(String startLineContent, boolean isTopRecord) {
        super(ExpressionEnum.retain, startLineContent, isTopRecord, false);
    }


    //********************** parse content to record **********************
    @Override
    protected void convertContentsIfHas(ListIterator<String> contentListIterator) {
        // do nothing...
    }

    @Override
    protected boolean isEndTagLine(String content) {
        throw new IllegalAccessError("retain type not has end tag");
    }
}
