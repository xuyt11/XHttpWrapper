package cn.ytxu.test.template_engine.statement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/12/16.
 */
public class ListSingleLineParserTest {

    public static void main(String... args) {
        Pattern p = Pattern.compile("(</t:eachTemp value=')\\.+('>)");
        Matcher m = p.matcher("</t:eachTemp value='${input_type} ${input_name}, '>");
        System.out.println("find " + m.find());

        p = Pattern.compile("(eachTemp value=')[\\p{Punct}\\s\\w]+('>)");
        m = p.matcher("</t:eachTemp value='${input_type} ${input_name}, '>");
        System.out.println("find 1" + m.find());

        p = Pattern.compile("(eachTemp value=')[\\p{Print}\\p{Space}]+('>)");
        m = p.matcher("</t:eachTemp value='${input_type} ${input_name}, '>");
        System.out.println("find 12" + m.find());

        p = Pattern.compile("(eachTemp value=')[\\p{Print}\\p{Blank}]+('>)");
        m = p.matcher("</t:eachTemp value='${input_type} ${input_name}, '>");
        System.out.println("find 123" + m.find());

    }
}
