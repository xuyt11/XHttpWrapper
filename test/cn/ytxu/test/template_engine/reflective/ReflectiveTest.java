package cn.ytxu.test.template_engine.reflective;

import cn.ytxu.http_wrapper.template_engine.parser.util.ReflectiveUtil;

/**
 * Created by Administrator on 2016/12/16.
 */
public class ReflectiveTest {

    public static void main(String... args) {

        A a = new A();
        System.out.println(ReflectiveUtil.getString(a, "get "));
    }

    public static class A {
        public String get() {
            return "abc get";
        }
    }
}
