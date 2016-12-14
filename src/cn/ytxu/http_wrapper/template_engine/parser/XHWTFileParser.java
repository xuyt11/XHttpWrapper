package cn.ytxu.http_wrapper.template_engine.parser;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.config.property.template_file_info.TemplateFileInfoWrapper;
import cn.ytxu.http_wrapper.template_engine.XHWTFileType;
import cn.ytxu.http_wrapper.template_engine.parser.model.XHWTModel;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List<StatementRecord> records = parseStatementRecordsByXHWTModel(model);
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
            closeReader(reader);
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

    private void closeReader(BufferedReader reader) {
        if (null == reader) {
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<StatementRecord> parseStatementRecordsByXHWTModel(XHWTModel model) {
        List<StatementRecord> records = StatementRecord.getRecords(model.getContents());
        StatementRecord.parseRecords(records);
        return records;
    }

    public static void main(String... args) {
        String regStr = "<foreach each=\"sections\">";
        Pattern p = Pattern.compile("(<foreach each=\")\\w+(\">)");
        Matcher m = p.matcher(regStr);
        while (m.find()) {
            System.out.println(m.group());
        }


        System.out.println(regStr.replace(regStr, "asdfads"));

        System.out.println("======================");


        String regStr2 = "${version_code} ${s }";
        Pattern p2 = Pattern.compile("(\\$\\{)\\w+(\\})");
        Matcher m2 = p2.matcher(regStr2);
        while (m2.find()) {
            System.out.println("2group count:" + m2.groupCount());
            System.out.println("2group start:" + m2.start());
            System.out.println("2group end:" + m2.end());
            System.out.println(m2.group());
        }

    }

}
