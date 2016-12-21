package cn.ytxu.http_wrapper.template.expression.if_else;

import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter.Callback;
import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ytxu on 16/12/21.
 * 条件判断表达式的记录
 * 一共有6种状态：
 * 1, if if_end; 只有if判断；
 * 2, if else if_end; 有if判断和else条件
 * 3, if if_else if_end; 只有一个if_else判断，但没有else条件
 * 4, if if_else else if_end; 只有一个if_else判断，有else条件
 * 5, if if_else ... if_else if_end; 有多个if_else判断，但没有else条件
 * 6, if if_else ... if_else else if_end; 有多个if_else判断，有else条件
 */
public class IfElseExpressionRecord extends ExpressionRecord implements Callback {

    private static final String IfElseTag = "<t:if_else>";
    private static final String ElseTag = "<t:else>";
    private static final String END_TAG = "</t:if_end>";// 结束标签

    private List<ConditionType> conditionTypes = new ArrayList<>();
    private ArrayList<Relation> relations = new ArrayList<>();

    public IfElseExpressionRecord(String startLineContent, boolean isTopRecord) {
        super(ExpressionEnum.if_else, startLineContent, END_TAG, isTopRecord, true);
        relations.add(new Relation(startLineContent));
        conditionTypes.add(ConditionType.IF);
    }


    //********************** parse content to record **********************

    @Override
    public boolean hasMiddleTag() {
        return true;
    }

    @Override
    public boolean isMiddleTagLine(String content) {
        content = content.trim();
        return IfElseTag.equals(content) || ElseTag.equals(content);
    }

    @Override
    protected void convertContentsIfHas(ListIterator<String> contentListIterator) {
        List<ExpressionRecord> elseRecords = Content2ExpressionRecordConverter.getNormal(contentListIterator, this, this).start();
        if (ifRelation.records == Collections.EMPTY_LIST) {

        }
    }

    @Override
    public void middleTagLine(String content, List<ExpressionRecord> records) {
        conditionTypes.
        if (ElseTag.equals(content.trim())) {

        } else if (IfElseTag.equals(content.trim())) {

        } else {// error
        }
    }

    public static final class Relation {
        String conditionLine;
        List<ExpressionRecord> records = Collections.EMPTY_LIST;

        public Relation(String conditionLine) {
            this.conditionLine = conditionLine;
        }

        public void setRecords(List<ExpressionRecord> records) {
            this.records = records;
        }


    }

    /**
     * if else条件表达式中的三种条件
     */
    public enum ConditionType {
        IF,
        IF_ELSE,
        ELSE
    }

    @Override
    protected void parseRecordAndSubRecords() {

    }
}
