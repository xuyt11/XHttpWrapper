package cn.ytxu.http_wrapper.template.expression;

import cn.ytxu.http_wrapper.template.expression.foreach.ForeachExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.if_else.IfElseExpressionRecord;
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
        public ExpressionRecord createRecord(String startLineContent) {
            return new TextExpressionRecord(startLineContent);
        }
    },
    foreach("循环", ForeachExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent) {
            return new ForeachExpressionRecord(startLineContent);
        }
    },
    retain("保留代码区域", RetainExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent) {
            return new RetainExpressionRecord(startLineContent);
        }
//
    },
    list_single_line("单行循环，防止foreach循环嵌套", ListSingleLineExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent) {
            return new ListSingleLineExpressionRecord(startLineContent);
        }
    },
    if_else("if else 条件判断", IfElseCondition.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent) {
            return new IfElseExpressionRecord(startLineContent);
        }
    },
    list_replace("替换数组的文本", ListReplaceExpressionRecord.PATTERNS) {
        @Override
        public ExpressionRecord createRecord(String startLineContent) {
            return new ListReplaceExpressionRecord(startLineContent);
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

    public abstract ExpressionRecord createRecord(String startLineContent);
}

