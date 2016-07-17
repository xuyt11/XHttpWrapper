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
        for (int i = 0, size = contents.size(); i < size; i++) {
            String content = contents.get(i);
            Statement statement = Statement.get(content);
            statement.getAndAddRecord(content, records, i, size, contents);
            switch (statement) {
                case list: {
                    // TODO
                }
                break;
                case if_else: {
                    // TODO
                }
                break;
                case list_replace: {
                    // TODO
                }
                break;
                case text:
                case retain:
                case foreach: {
                }
                break;
                default:
                    throw new IllegalStateException("unknow statement type :" + statement);
            }

        }
    }


}
