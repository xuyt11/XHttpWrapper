package cn.ytxu.api_semi_auto_creater.util.statement.record;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.api_semi_auto_creater.util.ReflectiveUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.Statement;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;
import cn.ytxu.api_semi_auto_creater.util.statement.record.list_replace.LRSRParser;
import cn.ytxu.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/7/18.
 * 在内部没有任何其他标签，只是纯文本替换，所以内部都是直接转换成TextStatementRecord
 */
public class ListReplaceStatementRecord extends StatementRecord {

    private LRSRParser parser;

    public ListReplaceStatementRecord(Statement statement, String startTagContent, List<String> contents) {
        super(statement, startTagContent, contents);
    }


    @Override
    public void parse() {
        parser = new LRSRParser(startTagContent);
        parser.parse();
    }


    @Override
    public StringBuffer getWriteBuffer(Object reflectModel, RetainEntity retain) {
        List subModels = ReflectiveUtil.getList(reflectModel, parser.getMethodName());
        if (ListUtil.isEmpty(subModels)) {
            return new StringBuffer();
        }

        // 需要在解析完成listValue之后，才能生成subs，因为需要替换replaceContent
        String listValue = parseAndGetListValue(retain, subModels);
        List<String> newContents = getNewContents(listValue);
        createSubs(newContents);

        StringBuffer listReplaceBuffer = new StringBuffer();
        for (StatementRecord sub : subs) {
            listReplaceBuffer.append(sub.getWriteBuffer(reflectModel, retain));
        }

        return listReplaceBuffer;
    }

    private String parseAndGetListValue(RetainEntity retain, List subModels) {
        StringBuffer listValueBuffer = new StringBuffer();
        TextStatementRecord record = parser.getListValueRecord();
        for (Object subModel : subModels) {
            listValueBuffer.append(record.getNormalWriteBuffer(subModel, retain));
        }
        return listValueBuffer.toString();
    }

    private List<String> getNewContents(String listValue) {
        String replaceContent = parser.getReplaceContent();
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

    private void createSubs(List<String> newContents) {
        subs = new ArrayList<>(newContents.size());
        for (String content : newContents) {
            TextStatementRecord tsr = new TextStatementRecord(Statement.text, content);
            tsr.parse();
            subs.add(tsr);
        }
    }

}
