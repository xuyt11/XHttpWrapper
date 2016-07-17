package cn.ytxu.api_semi_auto_creater.util;

import java.util.List;

/**
 * Created by ytxu on 2016/7/17.
 * 表达式记录
 */
public class StatementRecord {

    private List<StatementRecord> subs;

    /**
     * 表达式枚举
     */
    public enum Statement {
        text("普通的文本"),
        foreach("循环"),
        retain("保留代码区域"),
        list("在foreach中的循环，防止foreach循环嵌套"),
        if_else("if else 条件判断");

        private final String tag;

        Statement(String tag) {
            this.tag = tag;
        }

    }


}
