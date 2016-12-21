package cn.ytxu.http_wrapper.template.expression.foreach;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;

import java.util.List;
import java.util.ListIterator;
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

    public ForeachExpressionRecord(String startLineContent, boolean isTopRecord) {
        super(ExpressionEnum.foreach, startLineContent, END_TAG, isTopRecord, true);
    }


    //********************** parse content to record **********************


    //********************** loop parse record **********************
    @Override
    protected void parseRecordAndSubRecords() {

    }
}
