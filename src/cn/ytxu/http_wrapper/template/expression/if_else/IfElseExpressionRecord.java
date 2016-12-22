package cn.ytxu.http_wrapper.template.expression.if_else;

import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.if_else.IfElseCondition;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/21.
 * 条件判断表达式的记录
 */
public class IfElseExpressionRecord extends ExpressionRecord {

    private static final Pattern ifElsePattern = Pattern.compile("(<t:if_else \\w+>)");
    private static final String ELSE_TAG = "<t:else>";
    private static final String END_TAG = "</t:if_end>";// 结束标签

    private LinkedList<Relation> relations = new LinkedList<>();

    public IfElseExpressionRecord(String startLineContent) {
        super(ExpressionEnum.if_else, startLineContent, END_TAG, true);
        relations.add(new Relation(startLineContent, ConditionType.IF));
    }


    //********************** parse content to record **********************

    @Override
    public boolean hasMiddleTag() {
        return true;
    }

    @Override
    public boolean isMiddleTagLine(String content) {
        content = content.trim();
        return ifElsePattern.matcher(content).find() || ELSE_TAG.equals(content);
    }

    @Override
    protected boolean convertContentsIfHas(ListIterator<String> contentListIterator) {
        return new Content2ExpressionRecordConverter.Normal(contentListIterator, this, new Content2ExpressionRecordConverter.Callback() {
            @Override
            public void middleTagLine(String content, List<ExpressionRecord> records) {
                relations.getLast().setRecords(records);

                ConditionType conditionType;
                if (ELSE_TAG.equals(content.trim())) {// else tag
                    conditionType = ConditionType.ELSE;
                } else {// if_else tag
                    conditionType = ConditionType.IF_ELSE;
                }
                relations.add(new Relation(content, conditionType));
            }

            @Override
            public void endTagLine(List<ExpressionRecord> records) {
                relations.getLast().setRecords(records);
            }
        }).start();
    }


    public static final class Relation {
        final String conditionLine;
        final ConditionType conditionType;
        final IfElseCondition condition;
        final String methodName;

        List<ExpressionRecord> records = Collections.EMPTY_LIST;

        public Relation(String conditionLine, ConditionType conditionType) {
            this.conditionLine = conditionLine;
            this.conditionType = conditionType;
            // parse record
            if (conditionType == ConditionType.ELSE) {
                // tip: else condition not have IfElseCondition and the reflect methodName
                this.condition = null;
                this.methodName = null;
            } else {
                this.condition = IfElseCondition.get(conditionLine);
                this.methodName = condition.getMethodName(conditionLine);
            }
        }

        public void setRecords(List<ExpressionRecord> records) {
            this.records = records;
        }
    }

    /**
     * if else条件表达式中的三种条件
     */
    public enum ConditionType {
        IF, IF_ELSE, ELSE
    }


    //********************** loop parse record **********************
    @Override
    public void parseRecordAndSubRecords() {
        for (Relation relation : relations) {
            for (ExpressionRecord record : relation.records) {
                record.parseRecordAndSubRecords();
            }
        }
    }


    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        for (Relation relation : relations) {
            if (relation.conditionType == ConditionType.ELSE) {
                return getWriteBuffer(reflectModel, retain, relation.records);
            }

            boolean isThisCondition = relation.condition.getBoolean(reflectModel, relation.methodName);
            if (isThisCondition) {
                return getWriteBuffer(reflectModel, retain, relation.records);
            }
        }
        // not have else condition, and not have one condition is true
        return new StringBuffer();
    }

    private StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain, List<ExpressionRecord> records) {
        StringBuffer buffer = new StringBuffer();
        for (ExpressionRecord record : records) {
            buffer.append(record.getWriteBuffer(reflectModel, retain));
        }
        return buffer;
    }
}
