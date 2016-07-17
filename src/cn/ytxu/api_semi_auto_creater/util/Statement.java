package cn.ytxu.api_semi_auto_creater.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 表达式枚举
 */
public enum Statement {
    text("普通的文本", null, null) {
        @Override
        public boolean isThisType(String content) {
            return false;
        }

        @Override
        public int getAndAddRecord(String content, List<StatementRecord> records, int index, int size, List<String> contents) {
            records.add(StatementRecord.getText(content));
            return index;
        }

        @Override
        public boolean isEndTag(String content) {
            throw new IllegalAccessError("text type can not has end tag");
        }
    },
    foreach("循环", Pattern.compile("(<foreach each=\")\\w+(\">)"), "</foreach>") {
        @Override
        public int getAndAddRecord(String content, List<StatementRecord> records, int index, int size, List<String> contents) {
            List<String> foreachContents = getForeachContents(index, size, contents);
//            Statement.foreach.getAndAddRecord(content, records, datas);
            records.add(StatementRecord.getForeach(content, foreachContents));
            return index + foreachContents.size() + 1;
        }

        private List<String> getForeachContents(int index, int size, List<String> contents) {
            List<String> datas = new ArrayList<>();
            for (int i = index + 1; i < size; i++) {
                String data = contents.get(index);
                if (isEndTag(data)) {
                    break;
                }
                datas.add(data);
            }
            return datas;
        }
    },
    retain("保留代码区域", Pattern.compile("(<retain type=\")\\w+(\"/>)"), null) {
        @Override
        public int getAndAddRecord(String content, List<StatementRecord> records, int index, int size, List<String> contents) {
            records.add(StatementRecord.getRetain(content));
            return index;
        }

        @Override
        public boolean isEndTag(String content) {
            throw new IllegalAccessError("retain type can not has end tag");
        }
    },
    list("在foreach中的循环，防止foreach循环嵌套", Pattern.compile("(<list each=\")\\w+(\">)"), "</list>") {
        @Override
        public int getAndAddRecord(String content, List<StatementRecord> records, int index, int size, List<String> contents) {

            return index;
        }
    },
    if_else("if else 条件判断", Pattern.compile("(<if isTure=\")\\w+(\">)"), "</if_end>") {
        @Override
        public int getAndAddRecord(String content, List<StatementRecord> records, int index, int size, List<String> contents) {

            return index;
        }
    };

    private final String tag;
    private final Pattern pattern;// 判断是否为该分类
    private final String endTag;// 结束标签

    Statement(String tag, Pattern pattern, String endTag) {
        this.tag = tag;
        this.pattern = pattern;
        this.endTag = endTag;
    }

    public boolean isThisType(String content) {
        return pattern.matcher(content).find();
    }

    /**
     * @param index
     * @param size
     * @param content
     * @param records
     * @return 返回遍历contents完后的index
     */
    public abstract int getAndAddRecord(String content, List<StatementRecord> records, int index, int size, List<String> contents);

    public boolean isEndTag(String content) {
        return endTag.equals(content.trim());
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

