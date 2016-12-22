package cn.ytxu.http_wrapper.template.expression;

import java.util.*;

/**
 * Created by ytxu on 16/12/21.
 * 表达式的解析器
 * 分析template文件的内容，获取到所有的表达式记录
 */
public class Content2ExpressionRecordConverter {

    protected final ListIterator<String> contentListIterator;// 表达式内容(template文件的内容)的遍历器
    protected final ExpressionRecord parentERecord;// 在records中所有的record的parent
    protected final LinkedList<ExpressionRecord> records;// 表达式的记录(分析后所有的表达式记录)
    protected final Callback callback;//

    protected Content2ExpressionRecordConverter(ListIterator<String> contentListIterator, ExpressionRecord parentERecord, Callback callback) {
        this.contentListIterator = contentListIterator;
        this.parentERecord = parentERecord;
        this.callback = callback;
        this.records = new LinkedList<>();
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


    public static final class Top extends Content2ExpressionRecordConverter {

        /**
         * @param contents template文件中全部的内容(去除头部信息的)
         * @return
         */
        public Top(List<String> contents) {
            super(contents.listIterator(), null, null);
        }

        public List<ExpressionRecord> start() {
            while (contentListIterator.hasNext()) {
                String content = contentListIterator.next();
                ExpressionEnum expression = ExpressionEnum.getByStartLineContent(content);
                ExpressionRecord record = expression.createRecord(content);
                records.add(record);
                record.convertContents2SubRecordsIfCan(contentListIterator);
            }
            return records;
        }

    }

    public static final class Normal extends Content2ExpressionRecordConverter {

        public Normal(ListIterator<String> contentListIterator, ExpressionRecord parentERecord, Callback callback) {
            super(contentListIterator, parentERecord, callback);
            if (Objects.isNull(parentERecord)) {
                throw new NullPointerException("u must setup parent expression record...");
            }
        }

        /**
         * 不是第一级的表达式，所以有parent expression record
         *
         * @return 是否解析到了endTagLine; (对于没有endtag的expression，直接返回true；否则，判断是否真的解析到了这行)
         */
        public boolean start() {
            if (!parentERecord.hasEndTagLine()) {// 没有结束标签，直接返回；不需要再做遍历解析了；也不需要回调callback的endtagLine，因为没有中间的expression
                return true;
            }

            while (contentListIterator.hasNext()) {
                String content = contentListIterator.next();

                // check is the parent expression middle tag
                if (parentERecord.hasMiddleTag() && parentERecord.isMiddleTagLine(content)) {
                    callback.middleTagLine(content, new ArrayList<>(records));
                    records.clear();
                    continue;
                }

                if (parentERecord.isEndTagLine(content)) {
                    callback.endTagLine(records);
                    return true;
                }

                ExpressionEnum expression = ExpressionEnum.getByStartLineContent(content);
                ExpressionRecord record = expression.createRecord(content);
                records.add(record);
                record.convertContents2SubRecordsIfCan(contentListIterator);
            }

            throw new IllegalArgumentException("this expression(" + parentERecord.startLineContent + ") has not end tag....");
        }

    }
}
