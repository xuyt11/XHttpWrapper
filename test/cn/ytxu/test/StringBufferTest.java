package cn.ytxu.test;

/**
 * Created by ytxu on 2016/7/24.
 */
public class StringBufferTest {

    public static void main(String... args) {

        StringBuffer buffer = new StringBuffer();
        String s = null;
        buffer.append(s);
        s = "111";
        buffer.append(s);
        s = null;
        buffer.append(s);
        s = "222";
        buffer.append(s);

        System.out.println(buffer.toString());


        buffer = new StringBuffer();
        buffer.append(new StringBuffer());
        s = "111";
        buffer.append(s);
        buffer.append(new StringBuffer());
        s = "222";
        buffer.append(s);

        System.out.println(buffer.toString());

    }
}
