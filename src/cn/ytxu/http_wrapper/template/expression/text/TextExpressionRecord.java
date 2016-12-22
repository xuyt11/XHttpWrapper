package cn.ytxu.http_wrapper.template.expression.text;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template_engine.parser.util.ReflectiveUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/21.
 * 文本表达式的记录
 */
public class TextExpressionRecord extends ExpressionRecord {
    private static final Pattern PATTERN = Pattern.compile("(\\$\\{)\\w+(\\})");// temp:${methodName}

    private List<Range> ranges = new ArrayList<>();

    public TextExpressionRecord(String startLineContent) {
        super(ExpressionEnum.text, startLineContent, false);
    }

    @Override
    protected boolean convertContentsIfHas(ListIterator<String> contentListIterator) {
        // do nothing...
        return true;
    }

    @Override
    protected boolean isEndTagLine(String content) {
        throw new IllegalAccessError("text type not has end tag");
    }


    //********************** loop parse record **********************
    @Override
    public void parseRecordAndSubRecords() {
        Matcher matcher = PATTERN.matcher(startLineContent);
        while (matcher.find()) {
            Range range = new Range(matcher.start(), matcher.end(), matcher.group());
            ranges.add(range);
        }
    }


    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        return getNormalWriteBuffer(reflectModel, retain).append(NextLine);
    }

    public StringBuffer getNormalWriteBuffer(Object reflectModel, RetainModel retain) {
        getAndSetContent2Range(reflectModel);
        return getFragmentBuffer();
    }

    private void getAndSetContent2Range(Object reflectModel) {
        for (Range range : ranges) {
            String content = ReflectiveUtil.getString(reflectModel, range.getMethodName());
            range.setContent(content);
        }
    }

    private StringBuffer getFragmentBuffer() {
        if (hasNotNeedReplaceText()) {
            return new StringBuffer(startLineContent);
        }

        StringBuffer fragmentBuffer = new StringBuffer();
        for (int i = 0, size = ranges.size(), end = 0; i < size; i++) {
            Range range = ranges.get(i);
            if (hasNotNeedReplaceTextInFront(end, range)) {
                fragmentBuffer.append(startLineContent.substring(end, range.getStart()));
            }

            fragmentBuffer.append(range.getContent());
            end = range.getEnd();

            if (isLastRange(i, size) && hasTextBehindTheLastRange(end)) {
                fragmentBuffer.append(startLineContent.substring(end));
            }
        }

        return fragmentBuffer;
    }

    private boolean hasNotNeedReplaceText() {
        return ranges.isEmpty();
    }

    private boolean hasNotNeedReplaceTextInFront(int end, Range range) {
        return range.getStart() != end;
    }

    private boolean isLastRange(int rangeIndex, int rangeSize) {
        return rangeIndex + 1 == rangeSize;
    }

    private boolean hasTextBehindTheLastRange(int end) {
        return startLineContent.length() != end;
    }


    static class Range {
        private int start;
        private int end;
        private String methodName;// 在反射时使用: group = ${methodName}
        private String content;// 真实的、需要替换的，数据

        public Range(int start, int end, String group) {
            this.start = start;
            this.end = end;
            this.methodName = group.substring(2, group.length() - 1);
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
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
