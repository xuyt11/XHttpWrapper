package cn.ytxu.http_wrapper.template.expression.list_replace;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;

import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/21.
 * 替换遍历表达式的记录
 */
public class ListReplaceExpressionRecord extends ExpressionRecord {
    public static final Pattern[] PATTERNS = {Pattern.compile("(<t:list_replace each=\")\\w+(\" replace_key=\")\\w+(\" list_value=\")[\\p{Print}\\p{Space}]+(\">)")};

    private static final String END_TAG = "</t:list_replace>";// 结束标签

//    private static final String PATTERN_FRONT = "each=\"";
//    private static final String PATTERN_END = "\">";
//    private static final Pattern PATTERN = Pattern.compile("(each=\")\\w+(\">)");

    public ListReplaceExpressionRecord(String startLineContent, boolean isTopRecord) {
        super(ExpressionEnum.list_replace, startLineContent, END_TAG, isTopRecord, true);
    }


    //********************** parse content to record **********************
}
