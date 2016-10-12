package cn.ytxu.xhttp_wrapper.xtemp.parser.util;

import cn.ytxu.xhttp_wrapper.model.BaseModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by ytxu on 2016/7/24.
 * 发射调用util
 */
public class ReflectiveUtil {

    public static Object invokeMethod(Object reflectObj, String methodName, String printTag) {
        Class clazz = reflectObj.getClass();
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            return method.invoke(reflectObj);
        } catch (NoSuchMethodException ignore) {
            Object reflectSuper = invokeMethodFromSuper(reflectObj, methodName, printTag);
            if (reflectSuper != null) return reflectSuper;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(printTag + ", do not find this method :" + methodName);
    }

    private static Object invokeMethodFromSuper(Object reflectObj, String methodName, String printTag) {
        if (!(reflectObj instanceof BaseModel)) {
            throw new IllegalArgumentException(reflectObj.getClass().toString() + " is not extends BaseModel, you need extends it...");
        }

        // 是数据树中的对象，则调用父对象的方法
        Object reflectSuper = ((BaseModel) reflectObj).getHigherLevel();
        if (reflectSuper == null) {// reflectObj is DocModel, so it have not super base model
            System.out.println("error : the data tree can not call this " + methodName + " method, and return a blank string...");
            return null;
        }

        return invokeMethod(reflectSuper, methodName, printTag);
    }

    public static String getString(Object reflectObj, String methodName) {
        return (String) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
    }

    public static List<?> getList(Object reflectObj, String methodName) {
        return (List<?>) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
    }

    public static boolean getBoolean(Object reflectObj, String methodName) {
        return (boolean) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
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
