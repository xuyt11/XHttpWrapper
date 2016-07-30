package cn.ytxu.api_semi_auto_creater.util.statement;

import cn.ytxu.apacer.entity.RetainEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ytxu on 2016/7/17.
 * 表达式记录
 */
public abstract class StatementRecord {
    protected static final String NextLine = "\n";

    protected Statement statement;// 该条表达式的类型
    protected String startTagContent;// 表达式的首行
    protected List<String> contents;// 表达式的所有内容

    protected List<StatementRecord> subs;

    public StatementRecord(Statement statement, String startTagContent, List<String> contents) {
        this.statement = statement;
        this.startTagContent = startTagContent;
        this.contents = contents;
    }

    /** 解析表达式 */
    public abstract void parse();

    /** 获取写入数据 */
    public abstract StringBuffer getWriteBuffer(Object reflectModel, RetainEntity retain);


    public static List<StatementRecord> getRecords(List<String> contents) {
        List<StatementRecord> records = new ArrayList<>();

        Iterator<String> iterator = contents.iterator();

        while (iterator.hasNext()) {
            String content = iterator.next();
            Statement statement = Statement.get(content);
            statement.getAndAddRecord(content, records, iterator);
        }
        return records;
    }

    public static void parseRecords(List<StatementRecord> records) {
        for (StatementRecord record : records) {
            record.parse();
        }
    }

    public static StringBuffer getWriteBuffer(List<StatementRecord> records, Object reflectModel, RetainEntity retain) {
        StringBuffer writeBuffer = new StringBuffer();
        for (StatementRecord record : records) {
            writeBuffer.append(record.getWriteBuffer(reflectModel, retain));
        }
        return writeBuffer;
    }

}
