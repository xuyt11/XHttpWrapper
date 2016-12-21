package cn.ytxu.http_wrapper.template.expression.list_single_line;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;

import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/21.
 * 单行遍历表达式的记录
 */
public class ListSingleLineExpressionRecord extends ExpressionRecord {
    public static final Pattern[] PATTERNS = {Pattern.compile("(<t:list each=\")\\w+(\")( singleLine>)")};

    private static final String END_TAG = "</t:list>";// 结束标签

//    private static final String PATTERN_FRONT = "each=\"";
//    private static final String PATTERN_END = "\">";
//    private static final Pattern PATTERN = Pattern.compile("(each=\")\\w+(\">)");

    public ListSingleLineExpressionRecord(String startLineContent, boolean isTopRecord) {
        super(ExpressionEnum.list_single_line, startLineContent, END_TAG, isTopRecord, true);
    }


    //********************** parse content to record **********************
}
