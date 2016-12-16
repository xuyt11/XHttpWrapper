package cn.ytxu.http_wrapper.template_engine.parser.util;

import cn.ytxu.http_wrapper.model.BaseModel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/7/24.
 * 发射调用util
 */
public class ReflectiveUtil {

    public static Object invokeMethod(Object reflectObj, String methodName, String printTag) {
        Object realReflectObj = reflectObj;
        Method method;
        do {
            try {
                method = getMethod4CurrReflectiveObject(realReflectObj, methodName);
                break;
            } catch (NoSuchMethodException ignore) {
                try {
                    realReflectObj = getHigherLevelReflectObject(realReflectObj);
                } catch (NotCallThisMethodException e) {
                    throw new RuntimeException("error : the data tree can not call this (" + methodName + ") method," +
                            " and the reflectObj is " + reflectObj.getClass().getSimpleName());
                }
            }
        } while (true);

        try {
            return method.invoke(realReflectObj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(printTag + ", do not find this method :" + methodName);
    }

    private static Method getMethod4CurrReflectiveObject(Object reflectObj, String methodName) throws NoSuchMethodException {
        try {
            Class clazz = reflectObj.getClass();
            return clazz.getDeclaredMethod(methodName);
        } catch (NoSuchMethodException ignore) {
            try {
                Class superClazz = reflectObj.getClass().getSuperclass();// 父类
                return superClazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException e) {
                throw new NoSuchMethodException(e.getMessage());
            }
        }
    }

    private static Object getHigherLevelReflectObject(Object reflectObj) throws NotCallThisMethodException {
        if (!(reflectObj instanceof BaseModel)) {
            throw new IllegalArgumentException(reflectObj.getClass().toString() + " is not extends BaseModel, you need extends it...");
        }

        // 是数据树中的对象，则调用父对象的方法
        Object higherLevel = ((BaseModel) reflectObj).getHigherLevel();
        if (higherLevel == null) {// reflectObj is VersionModel, so it have not super base model
            throw new NotCallThisMethodException();
        }

        return higherLevel;
    }

    private static final class NotCallThisMethodException extends IllegalArgumentException {
    }


    //*********************** reflect sub type ***********************
    public static String getString(Object reflectObj, String methodName) {
        Object data = invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
        if (Objects.isNull(data)) {
            return null;
        }
        return data.toString();
    }

    public static List<?> getList(Object reflectObj, String methodName) {
        return (List<?>) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
    }

    public static boolean getBoolean(Object reflectObj, String methodName) {
        return (boolean) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
    }

    public static long getNumber(Object reflectObj, String methodName) {
        return (long) invokeMethod(reflectObj, methodName, reflectObj.getClass().getSimpleName());
    }


}
