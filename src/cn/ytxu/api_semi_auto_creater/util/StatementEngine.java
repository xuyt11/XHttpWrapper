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

    public void start() {
        for (String content : contents) {
            Statement statement = Statement.get(content);
            switch (statement) {
                case text:
                    StatementRecord record = StatementRecord.getText(content);
                    records.add(record);
                    break;
                case foreach:
                    break;
                case retain:
                    break;
                case list:
                    break;
                case if_else:
                    break;
                default:
                    throw new IllegalStateException("unknow statement type :" + statement);
            }

        }
    }


}
