package cn.ytxu.http_wrapper.template.expression;

import cn.ytxu.http_wrapper.template.expression.foreach.ForeachExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.if_else.IfElseExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.list.ListExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.list_replace.ListReplaceExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.list_single_line.ListSingleLineExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.retain.RetainExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.if_else.IfElseCondition;

import java.util.regex.Pattern;

/**
 * 表达式枚举
 */
public enum ExpressionEnum {
    text("普通的文本") {
        @Override
        protected boolean isThisType(String content) {
            return false;
        }

        @Override
        public ExpressionRecord createRecord(String startLineContent, boolean isTopRecord) {
            return new TextExpressionRecord(startLineContent, isTopRecord);
        }
    },
    foreach("循环", ForeachExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent, boolean isTopRecord) {
            return new ForeachExpressionRecord(startLineContent, isTopRecord);
        }
    },
    retain("保留代码区域", RetainExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent, boolean isTopRecord) {
            return new RetainExpressionRecord(startLineContent, isTopRecord);
        }
//
    },
    list("在foreach中的循环，防止foreach循环嵌套", ListExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent, boolean isTopRecord) {
            return new ListExpressionRecord(startLineContent, isTopRecord);
        }
    },
    list_single_line("单行循环，防止foreach循环嵌套", ListSingleLineExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent, boolean isTopRecord) {
            return new ListSingleLineExpressionRecord(startLineContent, isTopRecord);
        }
    },
    if_else("if else 条件判断", IfElseCondition.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent, boolean isTopRecord) {
            return new IfElseExpressionRecord(startLineContent, isTopRecord);
        }
    },
    list_replace("替换数组的文本", ListReplaceExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent, boolean isTopRecord) {
            return new ListReplaceExpressionRecord(startLineContent, isTopRecord);
        }
    };

    private final String tag;
    private final Pattern[] patterns;// 判断是否为该分类

    ExpressionEnum(String tag, Pattern... patterns) {
        this.tag = tag;
        this.patterns = (patterns == null ? new Pattern[0] : patterns);
    }


    public static ExpressionEnum getByStartLineContent(String content) {
        for (ExpressionEnum expression : ExpressionEnum.values()) {
            if (expression.isThisType(content)) {
                return expression;
            }
        }
        return text;
    }

    protected boolean isThisType(String content) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(content).find()) {
                return true;
            }
        }
        return false;
    }

    public abstract ExpressionRecord createRecord(String startLineContent, boolean isTopRecord);
}

