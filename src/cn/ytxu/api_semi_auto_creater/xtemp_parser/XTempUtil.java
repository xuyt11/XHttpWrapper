package cn.ytxu.api_semi_auto_creater.xtemp_parser;

import cn.ytxu.api_semi_auto_creater.config.Suffix;

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
     * @param suffix
     * @param tempPrefixName 需要解析的模板文件的名称
     */
    public XTempUtil(Suffix suffix, String tempPrefixName) {
        this.tempFileName = suffix.getTempFileName(tempPrefixName);
    }

    public XTempModel start() {
        List<String> contents = getContents();
        return new XTempModel(contents);
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
