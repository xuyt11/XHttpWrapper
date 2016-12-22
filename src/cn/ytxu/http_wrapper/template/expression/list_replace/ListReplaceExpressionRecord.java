package cn.ytxu.http_wrapper.template.expression.list_replace;

import cn.ytxu.http_wrapper.template.expression.ExpressionEnum;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 16/12/21.
 * 替换遍历表达式的记录
 * tip: 内部子记录都是text expression record
 */
public class ListReplaceExpressionRecord extends ExpressionRecord {
    public static final Pattern[] PATTERNS = {Pattern.compile("(<t:list_replace each=\")\\w+(\" replace_key=\")\\w+(\" list_value=\")[\\p{Print}\\p{Space}]+(\">)")};

    private static final String END_TAG = "</t:list_replace>";// 结束标签

//    private static final String PATTERN_FRONT = "each=\"";
//    private static final String PATTERN_END = "\">";
//    private static final Pattern PATTERN = Pattern.compile("(each=\")\\w+(\">)");

    private LRSRParser parser;
    private List<String> subContents = new ArrayList<>();

    public ListReplaceExpressionRecord(String startLineContent) {
        super(ExpressionEnum.list_replace, startLineContent, END_TAG, true);
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
        parser = new LRSRParser(startLineContent);
        parser.parse();
    }


    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        LRSRCreater creater = new LRSRCreater(parser.getMethodName(), parser.getListValueRecord(),
                parser.getReplaceContent(), subContents);
        return creater.getWriteBuffer(reflectModel, retain);
    }
}
