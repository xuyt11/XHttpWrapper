package cn.ytxu.api_semi_auto_creater.util;

import cn.ytxu.api_semi_auto_creater.util.statement.StatementEngine;

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

    private String tempFileName;// 模板文件的全称

    /**
     * 需要解析的文件的后缀
     */
    private enum Suffix {
        HttpApi("xha"),
        Request("xreq"),
        Response("xres");

        private final String name;

        Suffix(String name) {
            this.name = name;
        }

        /**
         * @param tempPrefixName 前缀名
         * @return temp文件的名称
         */
        public String getTempFileName(String tempPrefixName) {
            return tempPrefixName + "." + name;
        }

    }

    /**
     * @param suffix
     * @param tempName 需要解析的模板文件的名称
     */
    public XTempUtil(Suffix suffix, String tempName) {
        this.tempFileName = suffix.getTempFileName(tempName);
    }

    private void start() {
        List<String> contents = getContents();
        new StatementEngine(contents).start();
    }

    private List<String> getContents() {
        BufferedReader reader = null;
        try {
            reader = getReader();
            return getContents(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeReader(reader);
        }
    }

    private BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(
                this.getClass().getClassLoader().getResourceAsStream(tempFileName)));
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


    public static void main(String... args) {
        String regStr = "<foreach each=\"sections\">";
        Pattern p = Pattern.compile("(<foreach each=\")\\w+(\">)");
        Matcher m = p.matcher(regStr);
        while (m.find()) {
            System.out.println(m.group());
        }


        System.out.println(regStr.replace(regStr, "asdfads"));
    }

}
