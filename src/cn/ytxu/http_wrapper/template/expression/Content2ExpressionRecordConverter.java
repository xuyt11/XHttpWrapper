package cn.ytxu.http_wrapper.template.expression;

import java.util.*;

/**
 * Created by ytxu on 16/12/21.
 * 表达式的解析器
 * 分析template文件的内容，获取到所有的表达式记录
 */
public class Content2ExpressionRecordConverter {

    private final ListIterator<String> contentListIterator;// 表达式内容(template文件的内容)的遍历器
    private final boolean isTopRecord;// 是否为最顶部的记录
    private final ExpressionRecord parentERecord;// 在records中所有的record的parent
    private final LinkedList<ExpressionRecord> records;// 表达式的记录(分析后所有的表达式记录)
    private final Callback callback;//


    /**
     * @param contents template文件中全部的内容(去除头部信息的)
     * @return
     */
    public static Content2ExpressionRecordConverter getTop(List<String> contents, Callback callback) {
        return new Content2ExpressionRecordConverter(contents.listIterator(), true, null, callback);
    }

    public static Content2ExpressionRecordConverter getNormal(ListIterator<String> contentListIterator, ExpressionRecord parentERecord, Callback callback) {
        if (Objects.isNull(parentERecord)) {
            throw new NullPointerException("u must setup parent expression record...");
        }
        return new Content2ExpressionRecordConverter(contentListIterator, false, parentERecord, callback);
    }

    private Content2ExpressionRecordConverter(ListIterator<String> contentListIterator, boolean isTopRecord, ExpressionRecord parentERecord, Callback callback) {
        this.contentListIterator = contentListIterator;
        this.isTopRecord = isTopRecord;
        this.parentERecord = parentERecord;
        this.callback = callback;
        this.records = new LinkedList<>();
    }

    public boolean start() {
        if (isTopRecord) {// 第一级的表达式
            return start4Top();
        } else {
            return start4Normal();
        }
    }

    private boolean start4Top() {
        while (contentListIterator.hasNext()) {
            String content = contentListIterator.next();
            ExpressionEnum expression = ExpressionEnum.getByStartLineContent(content);
            ExpressionRecord record = expression.createRecord(content, isTopRecord);
            records.add(record);
            record.convertContents2SubRecordsIfCan(contentListIterator);
        }
        callback.endTagLine(records);
        return true;
    }

    /**
     * 不是第一级的表达式，所以有parent expression record
     *
     * @return 是否解析到了endTagLine; (对于没有endtag的expression，直接返回true；否则，判断是否真的解析到了这行)
     */
    private boolean start4Normal() {
        if (!parentERecord.hasEndTagLine()) {// 没有结束标签，直接返回；不需要再做遍历解析了；也不需要回调callback的endtagLine，因为没有中间的expression
            return true;
        }

        while (contentListIterator.hasNext()) {
            String content = contentListIterator.next();
            checkMiddleTagLine(content);

            if (checkEndTagLine(content)) {
                callback.endTagLine(records);
                return true;
            }

            ExpressionEnum expression = ExpressionEnum.getByStartLineContent(content);
            ExpressionRecord record = expression.createRecord(content, isTopRecord);
            records.add(record);
            record.convertContents2SubRecordsIfCan(contentListIterator);
        }

        throw new IllegalArgumentException("this expression(" + parentERecord.startLineContent + ") has not end tag....");
    }

    private void checkMiddleTagLine(String content) {
        // check is the parent expression middle tag
        if (!parentERecord.hasMiddleTag()) {
            return;
        }

        // if it`s true, stop convert
        // otherwise, again
        if (parentERecord.isMiddleTagLine(content)) {
            callback.middleTagLine(content, records);
            records.clear();
        }
    }

    private boolean checkEndTagLine(String content) {
        // 1. the top record has not parent expression, so need not check
        if (isTopRecord) {
            return false;
        }

        // 2. check is the parent expression end tag
        return parentERecord.isEndTagLine(content);
    }

    /**
     * 若是有中间标签(middleTag)的表达式，callback不能为null
     */
    public interface Callback {
        /**
         * 碰到了表达式中间的那些标签
         *
         * @param content 中间标签的content，没有trim，没有做任何操作的数据
         * @param records 该中间标签之上的所有的记录
         */
        void middleTagLine(String content, List<ExpressionRecord> records);

        /**
         * 解析到了结束标签
         *
         * @param records 解析到的所有记录
         */
        void endTagLine(List<ExpressionRecord> records);
    }
}
