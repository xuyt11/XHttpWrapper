package cn.ytxu.http_wrapper.template_engine.parser.expression;

import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementEnum;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementRecord;

import java.beans.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ytxu on 2016/12/21.
 * 表达式记录
 */
public abstract class ExpressionRecord {
    public static final String NextLine = "\n";

    protected StatementEnum statementEnum;// 该条表达式的类型
    protected String startTagContent;// 表达式的首行

    protected List<StatementRecord> subs;// 表达式记录的子表达式记录
    private boolean isTopRecord = false;// 是否为最顶部的记录


    public ExpressionRecord(StatementEnum statementEnum, String startTagContent, List<StatementRecord> subs, boolean isTopRecord) {
        this.statementEnum = statementEnum;
        this.startTagContent = startTagContent;
        this.subs = subs;
        this.isTopRecord = isTopRecord;
    }


    /**
     * 分析template文件的内容，获取到所有的语句记录
     *
     * @param contents template文件的内容
     * @return 分析后所有的语句记录
     */
    public static List<StatementRecord> analysis(List<String> contents) {
        List<StatementRecord> records = new ArrayList<>();
        final Iterator<String> iterator = contents.iterator();

        while (iterator.hasNext()) {
            String content = iterator.next();
            boolean isStartLine = Statement.isStartLine(content);
            if (isStartLine) {
                Statement statement = Statement.getByStartLine(content);

            }
        }
        return records;
    }
}
