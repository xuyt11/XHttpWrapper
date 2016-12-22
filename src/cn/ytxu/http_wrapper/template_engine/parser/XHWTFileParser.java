package cn.ytxu.http_wrapper.template_engine.parser;

import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.config.property.template_file_info.TemplateFileInfoWrapper;
import cn.ytxu.http_wrapper.template.expression.Content2ExpressionRecordConverter;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.creater.XHWTFileType;
import cn.ytxu.http_wrapper.template_engine.parser.model.XHWTModel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/7/17.
 * 模板工具类
 */
public class XHWTFileParser {

    private final XHWTFileType xhwtFileType;
    private String filePath;// 模板文件的路径

    public XHWTFileParser(XHWTFileType xhwtFileType) {
        this.xhwtFileType = xhwtFileType;
    }

    public XHWTModel start() throws XHWTNonNeedParsedException {
        try {
            this.filePath = ConfigWrapper.getTemplateFileInfo().getTemplateFileAbsolutePath(xhwtFileType);
        } catch (TemplateFileInfoWrapper.NonNeedParseTheTemplateFileException e) {
            throw new XHWTNonNeedParsedException(xhwtFileType);
        }

        List<String> contents = getContents();
        XHWTModel model = new XHWTContentParser(contents).start();
        List<ExpressionRecord> records = parseStatementRecordsByXHWTModel(model);
        model.setRecords(records);
        return model;
    }

    public static final class XHWTNonNeedParsedException extends Exception {
        public XHWTNonNeedParsedException(XHWTFileType xhwtFileType) {
            super("this template type not need parsed, the type is " + xhwtFileType.name());
        }
    }

    private List<String> getContents() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            return getContents(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private List<String> getContents(BufferedReader reader) throws IOException {
        List<String> contents = new ArrayList<>();
        String strLine;
        while (null != (strLine = reader.readLine())) {
            contents.add(strLine);
        }
        return contents;
    }

    private List<ExpressionRecord> parseStatementRecordsByXHWTModel(XHWTModel model) {
        List<ExpressionRecord> records = new Content2ExpressionRecordConverter.Top(model.getContents()).start();
        ExpressionRecord.parseRecords(records);

//        List<StatementRecord> records = StatementRecord.getRecords(model.getContents());
//        StatementRecord.parseRecords(records);
        return records;
    }

}
