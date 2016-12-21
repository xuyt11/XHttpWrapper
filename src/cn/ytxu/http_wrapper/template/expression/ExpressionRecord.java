package cn.ytxu.http_wrapper.template.expression;

import java.util.List;
import java.util.ListIterator;

/**
 * Created by ytxu on 2016/12/21.
 * 表达式记录
 */
public abstract class ExpressionRecord {
    public static final String NextLine = "\n";

    protected final ExpressionEnum expressionType;// 该条表达式的类型
    protected final String startLineContent;// 表达式的首行
    private final String endTag;// 该表达式的结束标签

    private final boolean isTopRecord;// 是否为最顶部的记录，用于判断该template文件是否编写正确(主要是，判断表达式是否匹配)
    private final boolean maybeHasSubRecords;// 该表达式记录是否可能有子表达式

    private List<ExpressionRecord> subRecords;// 子表达式记录

    /**
     * 该表达式是没有结束标签的，也不能使用任何与endTag字段有关的方法，有用到的地方直接让其崩溃
     */
    public ExpressionRecord(ExpressionEnum expressionEnum, String startLineContent, boolean isTopRecord, boolean maybeHasSubRecords) {
        this(expressionEnum, startLineContent, null, isTopRecord, maybeHasSubRecords);
    }

    public ExpressionRecord(ExpressionEnum expressionType, String startLineContent, String endTag, boolean isTopRecord, boolean maybeHasSubRecords) {
        this.expressionType = expressionType;
        this.startLineContent = startLineContent;
        this.endTag = endTag;
        this.isTopRecord = isTopRecord;
        this.maybeHasSubRecords = maybeHasSubRecords;
    }

    public boolean isTopRecord() {
        return isTopRecord;
    }

    public List<ExpressionRecord> getSubRecords() {
        return subRecords;
    }


    //********************** content to record converter **********************

    public boolean hasMiddleTag() {
        return false;
    }

    public boolean isMiddleTagLine(String content) {
        return false;
    }

    protected boolean isEndTagLine(String content) {
        return endTag.equals(content.trim());
    }

    /**
     * 解析出子表达式
     * 1、判断该表达式，是否可能有子表达式；
     * 2、若有，则解析子表达式记录；
     *
     * @param contentListIterator 表达式内容的遍历器
     */
    public void convertContents2SubRecordsIfCan(ListIterator<String> contentListIterator) {
        if (!maybeHasSubRecords) {
            return;
        }

        if (contentListIterator.hasNext()) {
            convertContentsIfHas(contentListIterator);
        }

        // parse sub records end
        if (!isTopRecord) {
            String content = contentListIterator.previous();
            throw new IllegalArgumentException(content);
        }
    }

    /**
     * 若有子表达式记录，则解析
     * if is end tag, or other middle tags, set them to self,
     * otherwise, get its ExpressionEnum and then start sub parser
     *
     * @param contentListIterator 表达式内容的遍历器
     */
    protected void convertContentsIfHas(ListIterator<String> contentListIterator) {
        this.subRecords = Content2ExpressionRecordConverter.getNormal(contentListIterator, this).start();
    }


    //********************** loop parse record **********************
    public static void parseRecords(List<ExpressionRecord> records) {
        for (ExpressionRecord record : records) {
            record.parseRecordAndSubRecords();
        }
    }

    protected abstract void parseRecordAndSubRecords();

}
