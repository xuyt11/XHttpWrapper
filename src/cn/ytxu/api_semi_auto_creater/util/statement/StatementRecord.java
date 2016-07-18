package cn.ytxu.api_semi_auto_creater.util.statement;

import java.util.List;

/**
 * Created by ytxu on 2016/7/17.
 * 表达式记录
 */
public class StatementRecord {

    protected Statement statement;// 该条表达式的类型
    protected String startTagContent;// 表达式的首行
    protected List<String> contents;// 表达式的所有内容

    protected List<StatementRecord> subs;

    public StatementRecord(Statement statement, String startTagContent, List<String> contents) {
        this.statement = statement;
        this.startTagContent = startTagContent;
        this.contents = contents;
    }


}
