package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.api_semi_auto_creater.util.ReflectiveUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/7/18.
 */
public class TextStatementRecord extends StatementRecord {
    private static final Pattern PATTERN = Pattern.compile("(\\$\\{)\\w+(\\})");// temp:${methodName}

    private List<Range> ranges = new ArrayList<>();

    public TextStatementRecord(Statement statement, String startTagContent) {
        super(statement, startTagContent, null);
    }


    @Override
    public void parse() {
        Matcher matcher = PATTERN.matcher(startTagContent);
        while (matcher.find()) {
            Range range = new Range(matcher.start(), matcher.end(), matcher.group());
            ranges.add(range);
        }
    }


    @Override
    public StringBuffer getWriteBuffer(Object model) {
        for (Range range : ranges) {
            String content = ReflectiveUtil.getString(model, range.getMethodName());
            range.setContent(content);
        }

        StringBuffer fragmentBuffer = new StringBuffer();
        for (int i = 0, size = ranges.size(), end = 0; i < size; i++) {
            Range range = ranges.get(i);
            if (range.getStart() != end) {
                fragmentBuffer.append(startTagContent.substring(end, range.getStart()));
            }

            fragmentBuffer.append(range.getContent());
            end = range.getEnd();

            if (i + 1 == size) {// is last range
                if(startTagContent.length() != end) {
                    fragmentBuffer.append(startTagContent.substring(end));
                }
            }
        }

        return fragmentBuffer;
    }

    static class Range {
        private int start;
        private int end;
        private String group;// ${methodName}，在替换时使用
        private String methodName;// 在反射时使用
        private String content;// 真实的、需要替换的，数据

        public Range(int start, int end, String group) {
            this.start = start;
            this.end = end;
            this.group = group;
            this.methodName = group.substring(2, group.length() - 1);
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }

        public String getGroup() {
            return group;
        }

        public String getMethodName() {
            return methodName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }


}
