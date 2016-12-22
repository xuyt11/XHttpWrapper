package cn.ytxu.http_wrapper.template.expression.list_single_line;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template_engine.parser.util.ReflectiveUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/21.
 * 单行遍历表达式的记录
 */
public class ListSingleLineExpressionRecord extends ExpressionRecord {
    public static final Pattern[] PATTERNS = {Pattern.compile("(<t:list each=\")\\w+(\")( singleLine>)")};

    private static final String END_TAG = "</t:list>";// 结束标签

//    private static final String PATTERN_FRONT = "each=\"";
//    private static final String PATTERN_END = "\">";
//    private static final Pattern PATTERN = Pattern.compile("(each=\")\\w+(\">)");

    private LSLSRParser parser;
    private List<String> subContents = new ArrayList<>();

    public ListSingleLineExpressionRecord(String startLineContent) {
        super(ExpressionEnum.list_single_line, startLineContent, END_TAG, true);
    }


    //********************** parse content to record **********************
    @Override
    protected boolean convertContentsIfHas(ListIterator<String> contentListIterator) {
        while (contentListIterator.hasNext()) {
            String content = contentListIterator.next();
            if (isEndTagLine(content)) {
                return true;
            }
            subContents.add(content);
        }
        return false;
    }


    //********************** loop parse record **********************
    @Override
    public void parseRecordAndSubRecords() {
        parser = new LSLSRParser(startLineContent, subContents);
        parser.parse();
    }


    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        List subModels = ReflectiveUtil.getList(reflectModel, parser.getMethodName());
        StringBuffer listSingleLineBuffer = new StringBuffer();

        if (subModels == null || subModels.isEmpty()) {
            return listSingleLineBuffer;
        }

        for (Object subModel : subModels) {
            TextExpressionRecord record = parser.getEachTempStatementRecord();
            StringBuffer buffer = record.getNormalWriteBuffer(subModel, retain);
            listSingleLineBuffer.append(buffer);
        }

        if (Objects.nonNull(parser.getStart())) {
            listSingleLineBuffer.insert(0, parser.getStart());
        }

        if (Objects.nonNull(parser.getEnd())) {
            listSingleLineBuffer.append(parser.getEnd());
        }

        return listSingleLineBuffer.append(NextLine);
    }
}
