package cn.ytxu.api_semi_auto_creater.util;

import java.util.ArrayList;
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

    public List<StatementRecord> start() {
        for (int i = 0, size = contents.size(); i < size; i++) {
            String content = contents.get(i);
            Statement statement = Statement.get(content);
            i = statement.getAndAddRecord(content, records, i, size, contents);
        }
        return records;
    }


}
