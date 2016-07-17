package cn.ytxu.api_semi_auto_creater.util;

import java.util.List;

/**
 * Created by ytxu on 2016/7/17.
 * 表达式记录
 */
public class StatementRecord {

    private Statement statement;// 该条表达式的类型
    private String content;// 表达式的首行
    private List<String> contents;// 表达式的所有内容

    private List<StatementRecord> subs;

    public static StatementRecord getText(String content) {
        StatementRecord record = new StatementRecord();
        record.statement = Statement.text;
        record.content = content;
        return record;
    }

    public static StatementRecord getRetain(String content) {
        StatementRecord record = new StatementRecord();
        record.statement = Statement.retain;
        record.content = content;
        return record;
    }

    public static StatementRecord getForeach(String content, List<String> foreachContents) {
        StatementRecord record = new StatementRecord();
        record.statement = Statement.foreach;
        record.content = content;
        record.contents = foreachContents;
        return record;
    }
}
