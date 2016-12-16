package cn.ytxu.test.template_engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/12/16.
 * target file --> XHWTFileParser
 */
public class FileParserTest {

    public static void main(String... args) {
        String regStr = "<t:foreach each=\"sections\">";
        Pattern p = Pattern.compile("(<t:foreach each=\")\\w+(\">)");
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
