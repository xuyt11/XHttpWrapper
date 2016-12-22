package cn.ytxu.http_wrapper.template.expression.list_single_line;

import cn.ytxu.http_wrapper.template.expression.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.helper.PatternHelper;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/8/12.
 * list single line 表达式记录的解析器
 */
public class LSLSRParser {
    // methodName
    private static final PatternHelper.PatternModel EACH_MODEL
            = new PatternHelper.PatternModel("each=\"", "\"", Pattern.compile("(each=\")\\w+(\")"));

    // eachTemp 必须要有, start end 可以没有
    public enum SubContentType {
        eachTemp("遍历的数据模板，一定要有的",
                new PatternHelper.PatternModel("</t:eachTemp value=\"", "\"/>",
                        Pattern.compile("(</t:eachTemp value=\")[\\p{Print}\\p{Space}]+(\"/>)"))),
        start("数据填充后，插入到首位",
                new PatternHelper.PatternModel("</t:start value=\"", "\"/>",
                        Pattern.compile("(</t:start value=\")[\\p{Print}\\p{Space}]+(\"/>)"))),
        end("数据填充后，添加到末尾",
                new PatternHelper.PatternModel("</t:end value=\"", "\"/>",
                        Pattern.compile("(</t:end value=\")[\\p{Print}\\p{Space}]+(\"/>)")));

        private final String tag;
        private final PatternHelper.PatternModel patternModel;// 判断是否为该分类

        SubContentType(String tag, PatternHelper.PatternModel patternModel) {
            this.tag = tag;
            this.patternModel = patternModel;
        }
    }


    private String startTagContent;
    private List<String> contents;

    private String methodName;
    private TextExpressionRecord eachTempStatementRecord;
    private String start, end;// 在遍历完成后，需要将start+values+end拼接返回

    public LSLSRParser(String startTagContent, List<String> contents) {
        this.startTagContent = startTagContent;
        this.contents = contents;
    }

    public void parse() {
        methodName = PatternHelper.getPatternValue(EACH_MODEL, startTagContent);

        parsePatternValue(SubContentType.eachTemp, new GetPatternValueCallback() {
            @Override
            public void get(String patternValue) {
                eachTempStatementRecord = new TextExpressionRecord(patternValue);
                eachTempStatementRecord.parseRecordAndSubRecords();
            }

            @Override
            public void unGet() {
                // can not happen
            }
        });

        parsePatternValue(SubContentType.start, new GetPatternValueCallback() {
            @Override
            public void get(String patternValue) {
                start = patternValue;
            }

            @Override
            public void unGet() {
                start = "";
            }
        });

        parsePatternValue(SubContentType.end, new GetPatternValueCallback() {
            @Override
            public void get(String patternValue) {
                end = patternValue;
            }

            @Override
            public void unGet() {
                end = "";
            }
        });
    }

    private void parsePatternValue(SubContentType type, GetPatternValueCallback callback) {
        try {
            String content = getTargetContentByPatternModel(type.patternModel);
            contents.remove(content);
            String patternValue = PatternHelper.getPatternValue(type.patternModel, content);
            callback.get(patternValue);
        } catch (TargetContentNotFoundException e) {
            callback.unGet();
        }
    }

    private String getTargetContentByPatternModel(PatternHelper.PatternModel model) {
        for (String content : contents) {
            boolean hasMatch = PatternHelper.matchThisPattern(model, content);
            if (hasMatch) {
                return content;
            }
        }
        throw new TargetContentNotFoundException();
    }

    public String getMethodName() {
        return methodName;
    }

    public String getStart() {
        return start;
    }

    public TextExpressionRecord getEachTempStatementRecord() {
        return eachTempStatementRecord;
    }

    public String getEnd() {
        return end;
    }


    private static class TargetContentNotFoundException extends RuntimeException {
    }

    private interface GetPatternValueCallback {
        void get(String patternValue);

        void unGet();
    }

}
