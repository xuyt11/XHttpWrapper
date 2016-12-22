package cn.ytxu.http_wrapper.template.expression;

import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * Created by ytxu on 2016/12/21.
 * 表达式记录
 */
public abstract class ExpressionRecord {
    public static final String NextLine = "\n";

    protected final ExpressionEnum expressionType;// 该条表达式的类型
    protected final String startLineContent;// 表达式的首行
    private final String endTag;// 该表达式的结束标签

    private final boolean maybeHasSubRecords;// 该表达式记录是否可能有子表达式

    private List<ExpressionRecord> subRecords;// 子表达式记录

    /**
     * 该表达式是没有结束标签的，也不能使用任何与endTag字段有关的方法，有用到的地方直接让其崩溃
     */
    public ExpressionRecord(ExpressionEnum expressionEnum, String startLineContent, boolean maybeHasSubRecords) {
        this(expressionEnum, startLineContent, null, maybeHasSubRecords);
    }

    public ExpressionRecord(ExpressionEnum expressionType, String startLineContent, String endTag, boolean maybeHasSubRecords) {
        this.expressionType = expressionType;
        this.startLineContent = startLineContent;
        this.endTag = endTag;
        this.maybeHasSubRecords = maybeHasSubRecords;
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

    public boolean hasEndTagLine() {
        return Objects.nonNull(endTag);
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
     * @return convertEndTagLine 是否解析到了end tag
     */
    public boolean convertContents2SubRecordsIfCan(ListIterator<String> contentListIterator) {
        if (!maybeHasSubRecords) {
            return true;
        }

        if (contentListIterator.hasNext()) {
            boolean convertEndTagLine = convertContentsIfHas(contentListIterator);
            if (convertEndTagLine) {
                return true;
            }
            throw new NonHaveEndTagException("this expression(" + startLineContent + "), have not end tag");
        }

        // 到模板文件最后一行了，却不能解析到endtagline
        throw new NonHaveEndTagException("move to end line, but this expression(" + startLineContent + "), have not end tag");
    }

    /**
     * 若有子表达式记录，则解析
     * if is end tag, or other middle tags, set them to self,
     * otherwise, get its ExpressionEnum and then start sub parser
     *
     * @param contentListIterator 表达式内容的遍历器
     * @return convertEndTagLine 是否解析到了end tag
     */
    protected boolean convertContentsIfHas(ListIterator<String> contentListIterator) {
        return new Content2ExpressionRecordConverter.Normal(contentListIterator, this, new Content2ExpressionRecordConverter.Callback() {
            @Override
            public void middleTagLine(String content, List<ExpressionRecord> records) {
            }

            @Override
            public void endTagLine(List<ExpressionRecord> records) {
                ExpressionRecord.this.subRecords = records;
            }
        }).start();
    }

    public static final class NonHaveEndTagException extends IllegalArgumentException {
        public NonHaveEndTagException(String s) {
            super(s);
        }
    }

    //********************** loop parse record(解析表达式) **********************
    public static void parseRecords(List<ExpressionRecord> records) {
        for (ExpressionRecord record : records) {
            record.parseRecordAndSubRecords();
        }
    }

    /**
     * 解析表达式
     */
    public abstract void parseRecordAndSubRecords();


    //********************** 获取写入数据**********************
    public static StringBuffer getWriteBuffer(List<ExpressionRecord> records, Object reflectModel, RetainModel retain) {
        StringBuffer writeBuffer = new StringBuffer();
        for (ExpressionRecord record : records) {
            writeBuffer.append(record.getWriteBuffer(reflectModel, retain));
        }
        return writeBuffer;
    }

    /**
     * 获取写入数据
     */
    public abstract StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain);

}
