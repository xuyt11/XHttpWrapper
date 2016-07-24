package cn.ytxu.api_semi_auto_creater.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by ytxu on 2016/7/24.
 * 发射调用util
 */
public class ReflectiveUtil {

    public static Object invokeMethod(Object obj, String methodName) {
        Class clazz = obj.getClass();
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            return method.invoke(obj);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("obj:" + clazz.toString() + ", methodName:" + methodName);
    }

    public static String getString(Object obj, String methodName) {
        return (String) invokeMethod(obj, methodName);
    }

    public static List<?> getList(Object obj, String methodName) {
        return (List<?>) invokeMethod(obj, methodName);
    }

    public static boolean getBoolean(Object obj, String methodName) {
        return (boolean) invokeMethod(obj, methodName);
    }


    public static void main(String... args) {

        A a = new A();
        System.out.println(getString(a, "get "));
    }

    public static class A {
        public String get() {
            return "abc get";
        }
    }
}
