package cn.ytxu.util;

import cn.ytxu.apacer.config.Config;

import java.io.*;

/**
 * Created by newchama on 16/3/29.
 */
public class FileUtil {

    public static String getClassFileName(String name) {
        String classFileName;
        if (name.contains(" ")) {// 包含有空格，需要先将空格转换为下划线，在转换为驼峰法
            classFileName = CamelCaseUtils.toCapitalizeCamelCase(
                    CamelCaseUtils.convertSpace2UnderLine(name));
        } else if (name.contains("_")) {// 包含有下划线，直接转换为驼峰法
            classFileName = CamelCaseUtils.toCapitalizeCamelCase(name);
        } else {// 首字母大写
            classFileName = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return classFileName;
    }

    /**  生成分类的包名--->v5 */
    public static String getCategoryPackageName(String name) {
        String classFileName = getClassFileName(name);
        return classFileName.substring(0, 1).toLowerCase() + classFileName.substring(1);
    }

    public static Writer getWriter(String classFileFullName, String dirPath) throws UnsupportedEncodingException, FileNotFoundException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Writer writer = new OutputStreamWriter(new FileOutputStream(
                new File(dir, classFileFullName)), Config.CharsetName);
        return writer;
    }

}
