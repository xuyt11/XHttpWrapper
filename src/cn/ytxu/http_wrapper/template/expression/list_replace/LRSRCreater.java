package cn.ytxu.http_wrapper.template.expression.list_replace;

import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template_engine.parser.util.ReflectiveUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/14.
 * ListReplaceStatementRecord的代码生成器
 */
public class LRSRCreater {
    private String methodName;
    private TextExpressionRecord listValueRecord;
    private String replaceContent;
    private List<String> contents;

    public LRSRCreater(String methodName, TextExpressionRecord listValueRecord, String replaceContent, List<String> contents) {
        this.methodName = methodName;
        this.listValueRecord = listValueRecord;
        this.replaceContent = replaceContent;
        this.contents = contents;
    }

    public StringBuffer getWriteBuffer(Object reflectModel, RetainModel retain) {
        List subModels = ReflectiveUtil.getList(reflectModel, methodName);
        if (subModels == null || subModels.isEmpty()) {
            return new StringBuffer();
        }

        // 需要在解析完成listValue之后，才能生成subs，因为需要替换replaceContent
        String listValue = parseAndGetListValue(retain, subModels);
        List<String> newContents = getNewContents(listValue);
        List<ExpressionRecord> subs = createSubs(newContents);

        return getListReplaceBufferBySubs(reflectModel, retain, subs);
    }

    private String parseAndGetListValue(RetainModel retain, List subModels) {
        StringBuffer listValueBuffer = new StringBuffer();
        TextExpressionRecord record = listValueRecord;
        for (Object subModel : subModels) {
            listValueBuffer.append(record.getNormalWriteBuffer(subModel, retain));
        }
        return listValueBuffer.toString();
    }

    private List<String> getNewContents(String listValue) {
        List<String> newContents = new ArrayList<>(contents.size());
        for (String content : contents) {
            String newContent;
            if (content.contains(replaceContent)) {
                newContent = content.replace(replaceContent, listValue);
            } else {
                newContent = content;
            }
            newContents.add(newContent);
        }
        return newContents;
    }

    private List<ExpressionRecord> createSubs(List<String> newContents) {
        List<ExpressionRecord> subs = new ArrayList<>(newContents.size());
        for (String content : newContents) {
            TextExpressionRecord tsr = new TextExpressionRecord(content);
            tsr.parseRecordAndSubRecords();
            subs.add(tsr);
        }
        return subs;
    }

    private StringBuffer getListReplaceBufferBySubs(Object reflectModel, RetainModel retain, List<ExpressionRecord> subs) {
        StringBuffer listReplaceBuffer = new StringBuffer();
        for (ExpressionRecord sub : subs) {
            listReplaceBuffer.append(sub.getWriteBuffer(reflectModel, retain));
        }
        return listReplaceBuffer;
    }

}
