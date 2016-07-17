package cn.ytxu.api_semi_auto_creater.util;

import java.util.regex.Pattern;

/**
 * 表达式枚举
 */
public enum Statement {
    text("普通的文本", null) {
        @Override
        public boolean isThisType(String content) {
            return false;
        }
    },
    foreach("循环", Pattern.compile("(<foreach each=\")\\w+(\">)")) {
    },
    retain("保留代码区域", Pattern.compile("(<retain type=\")\\w+(\"/>)")) {
    },
    list("在foreach中的循环，防止foreach循环嵌套", Pattern.compile("(<list each=\")\\w+(\">)")) {
    },
    if_else("if else 条件判断", Pattern.compile("(<if isTure=\")\\w+(\">)")) {
    };

    private final String tag;
    private final Pattern pattern;// 判断是否为该分类

    Statement(String tag, Pattern pattern) {
        this.tag = tag;
        this.pattern = pattern;
    }

    public boolean isThisType(String content) {
        return pattern.matcher(content).find();
    }

    public static Statement get(String content) {
        for (Statement s : Statement.values()) {
            if (s.isThisType(content)) {
                return s;
            }
        }
        return text;
    }
}

