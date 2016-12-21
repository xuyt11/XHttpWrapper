package cn.ytxu.http_wrapper.template.expression.text;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;

import java.util.ListIterator;

/**
 * Created by ytxu on 16/12/21.
 * 文本表达式的记录
 */
public class TextExpressionRecord extends ExpressionRecord {

    public TextExpressionRecord(String startLineContent, boolean isTopRecord) {
        super(ExpressionEnum.text, startLineContent, isTopRecord, false);
    }

    @Override
    protected void convertContentsIfHas(ListIterator<String> contentListIterator) {
        // do nothing...
    }

    @Override
    protected boolean isEndTagLine(String content) {
        throw new IllegalAccessError("text type not has end tag");
    }
}
