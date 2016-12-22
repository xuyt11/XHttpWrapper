package cn.ytxu.http_wrapper.template.expression.list_replace;

import cn.ytxu.http_wrapper.template.expression.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.helper.PatternHelper;

import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/8/14.
 */
public class LRSRParser {

    private String startTagContent;
    private String methodName, replaceKey;
    private TextExpressionRecord listValueRecord;

    private enum Attr {
        each("需要循环的list",
                new PatternHelper.PatternModel("each=\"", "\"",
                        Pattern.compile("(each=\")\\w+(\")"))),
        replace_key("需要在contents中替换的字符串",
                new PatternHelper.PatternModel("replace_key=\"", "\"",
                        Pattern.compile("(replace_key=\")\\w+(\")"))),
        list_value("遍历的数据模板",
                new PatternHelper.PatternModel("list_value=\"", "\">",
                        Pattern.compile("(list_value=\")[\\p{Print}\\p{Space}]+(\">)")));

        private final String tag;
        private final PatternHelper.PatternModel patternModel;

        Attr(String tag, PatternHelper.PatternModel patternModel) {
            this.tag = tag;
            this.patternModel = patternModel;
        }
    }

    public LRSRParser(String startTagContent) {
        this.startTagContent = startTagContent;
    }

    public void parse() {
        methodName = PatternHelper.getPatternValue(Attr.each.patternModel, startTagContent);
        replaceKey = PatternHelper.getPatternValue(Attr.replace_key.patternModel, startTagContent);

        String listValue = PatternHelper.getPatternValue(Attr.list_value.patternModel, startTagContent);
        listValueRecord = new TextExpressionRecord(listValue);
        listValueRecord.parseRecordAndSubRecords();
    }

    public String getMethodName() {
        return methodName;
    }

    public TextExpressionRecord getListValueRecord() {
        return listValueRecord;
    }

    public String getReplaceContent() {
        return "${" + replaceKey + "}";
    }
}
