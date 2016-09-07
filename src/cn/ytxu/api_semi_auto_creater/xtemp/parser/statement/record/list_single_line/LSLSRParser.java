package cn.ytxu.api_semi_auto_creater.xtemp.parser.statement.record.list_single_line;

import cn.ytxu.api_semi_auto_creater.xtemp.parser.statement.Statement;
import cn.ytxu.api_semi_auto_creater.xtemp.parser.statement.record.TextStatementRecord;
import cn.ytxu.api_semi_auto_creater.xtemp.parser.statement.record.helper.PatternHelper;

import java.util.List;
import java.util.regex.Matcher;
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
                new PatternHelper.PatternModel("<eachTemp value=\"", "\"/>",
                        Pattern.compile("(<eachTemp value=\")[\\p{Print}\\p{Space}]+(\"/>)"))),
        start("数据填充后，插入到首位",
                new PatternHelper.PatternModel("<start value=\"", "\"/>",
                        Pattern.compile("(<start value=\")[\\p{Print}\\p{Space}]+(\"/>)"))),
        end("数据填充后，添加到末尾",
                new PatternHelper.PatternModel("<end value=\"", "\"/>",
                        Pattern.compile("(<end value=\")[\\p{Print}\\p{Space}]+(\"/>)")));

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
    private TextStatementRecord eachTempStatementRecord;
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
                eachTempStatementRecord = new TextStatementRecord(Statement.text, patternValue);
                eachTempStatementRecord.parse();
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

    public TextStatementRecord getEachTempStatementRecord() {
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


    public static void main(String... args) {
        Pattern p = Pattern.compile("(<eachTemp value=')\\.+('>)");
        Matcher m = p.matcher("<eachTemp value='${input_type} ${input_name}, '>");
        System.out.println("find " + m.find());

        p = Pattern.compile("(eachTemp value=')[\\p{Punct}\\s\\w]+('>)");
        m = p.matcher("<eachTemp value='${input_type} ${input_name}, '>");
        System.out.println("find 1" + m.find());

        p = Pattern.compile("(eachTemp value=')[\\p{Print}\\p{Space}]+('>)");
        m = p.matcher("<eachTemp value='${input_type} ${input_name}, '>");
        System.out.println("find 12" + m.find());

        p = Pattern.compile("(eachTemp value=')[\\p{Print}\\p{Blank}]+('>)");
        m = p.matcher("<eachTemp value='${input_type} ${input_name}, '>");
        System.out.println("find 123" + m.find());

    }
}
