package cn.ytxu.api_semi_auto_creater.util.statement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ytxu on 2016/7/17.
 * 表达式记录引擎
 */
public class StatementEngine {

    private List<String> contents;
    private List<StatementRecord> records;

    public StatementEngine(List<String> contents) {
        this.contents = contents;
        this.records = new ArrayList<>();
    }

    public void start() {
        get();
        parse();
    }

    public void get() {
        Iterator<String> iterator = contents.iterator();
        while (iterator.hasNext()) {
            String content = iterator.next();
            Statement statement = Statement.get(content);
            statement.getAndAddRecord(content, records, iterator);
        }
    }

    public void parse() {
        for (StatementRecord record : records) {
            record.parse();

        }
    }

}
