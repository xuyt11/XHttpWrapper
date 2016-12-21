package cn.ytxu.http_wrapper.template_engine.parser.statement;

import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ytxu on 2016/7/17.
 * 表达式记录
 */
public abstract class StatementRecord {
    public static final String NextLine = "\n";

    protected StatementEnum statement;// 该条表达式的类型
    protected String startTagContent;// 表达式的首行
    protected List<String> contents;// 表达式的所有内容

    protected List<StatementRecord> subs;

    public StatementRecord(StatementEnum statement, String startTagContent, List<String> contents) {
        this.statement = statement;
        this.startTagContent = startTagContent;
        this.contents = contents;
    }

    /** 解析表达式 */
    public abstract void parse();

    /** 获取写入数据 */
    public abstract StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain);


    public static List<StatementRecord> getRecords(List<String> contents) {
        List<StatementRecord> records = new ArrayList<>();

        Iterator<String> iterator = contents.iterator();

        while (iterator.hasNext()) {
            String content = iterator.next();
            StatementEnum statement = StatementEnum.get(content);
            statement.getAndAddRecord(content, records, iterator);
        }
        return records;
    }

    public static void parseRecords(List<StatementRecord> records) {
        for (StatementRecord record : records) {
            record.parse();
        }
    }

    public static StringBuffer getWriteBuffer(List<StatementRecord> records, Object reflectModel, RetainModel retain) {
        StringBuffer writeBuffer = new StringBuffer();
        for (StatementRecord record : records) {
            writeBuffer.append(record.getWriteBuffer(reflectModel, retain));
        }
        return writeBuffer;
    }

}
