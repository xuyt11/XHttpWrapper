package cn.ytxu.xhttp_wrapper.xtemp.parser;

import cn.ytxu.xhttp_wrapper.config.XHWTFileType;
import cn.ytxu.xhttp_wrapper.xtemp.parser.model.XTempModel;
import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.StatementRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/7/17.
 * 模板工具类
 */
public class XTempUtil {

    private final String filePath;// 模板文件的路径

    /**
     * @param xhwtFileType
     * @param xhwtConfigPath       配置文件的路径
     */
    public XTempUtil(XHWTFileType xhwtFileType, String xhwtConfigPath) {
        this.filePath = xhwtFileType.getFilePath(xhwtConfigPath);
    }

    public XTempModel start() throws XHWTTemplateFileNotExistsException {
        if (!new File(filePath).exists()) {
            throw new XHWTTemplateFileNotExistsException(filePath);
        }

        List<String> contents = getContents();
        XTempModel model = new XTempParser(contents).start();
        List<StatementRecord> records = parseStatementRecordsByXTempModel(model);
        model.setRecords(records);
        return model;
    }

    public static final class XHWTTemplateFileNotExistsException extends Exception {
        public XHWTTemplateFileNotExistsException(String filePath) {
            super("this template file is not exists, filePath:" + filePath);
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

    private List<StatementRecord> parseStatementRecordsByXTempModel(XTempModel model) {
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
