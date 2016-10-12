package cn.ytxu.xhttp_wrapper.xtemp.parser.statement.record.retain;

import cn.ytxu.util.LogUtil;

import java.io.*;

/**
 * 自动生成文件中需要保留的数据:有可能是我自己后面加的,也有可能是其他coder加的<br>
 * 例如:简化网络请求中参数的长度<br>
 * 2016-04-18<br>
 * version v6
 */
public class RetainParser {
    private static final String StartTag = "//** ytxu.retain-start */";
    private static final String EndTag = "//** ytxu.retain-end */";
    static final String CategoryImportTag = "//** ytxu.import */";
    static final String CategoryFieldTag = "//** ytxu.field */";
    static final String CategoryMethodTag = "//** ytxu.method */";
    static final String CategoryOtherTag = "//** ytxu.other */";

    /**
     * 1、先要判断目标文件是否存在；<br>
     * 2、若存在，在判断该文件是否有数据；<br>
     * 3、若有数据，则打开输入流，获取到以上几个需要保留（retain）的数据；-->输出为一个对象<br>
     * 4、在解析文档对象，并输出文件数据时，将几个分类的数据，插入其中；
     */
    public static RetainModel getRetainByFile(String classFileFullName, String dirPath) {
        File targetFile = new File(dirPath, classFileFullName);
        if (!targetFile.exists()) {
            return RetainModel.EmptyRetain;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)));
            return parserReaderAndGetRetain(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtil.e("target file`s name is " + classFileFullName);
            return RetainModel.EmptyRetain;
        } catch (IOException e) {
            e.printStackTrace();
            return RetainModel.EmptyRetain;
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static RetainModel parserReaderAndGetRetain(BufferedReader reader) throws IOException {
        StringBuffer importSb = new StringBuffer();
        StringBuffer fieldSb = new StringBuffer();
        StringBuffer methodSb = new StringBuffer();
        StringBuffer otherSb = new StringBuffer();

        String strLine;
        while (null != (strLine = reader.readLine())) {
            if (!strLine.contains(StartTag)) {
                continue;
            }

            StringBuffer retain = getRetainData(reader);
            if (strLine.contains(CategoryImportTag)) {
                importSb.append(getData(CategoryImportTag, retain));
            } else if (strLine.contains(CategoryFieldTag)) {
                fieldSb.append(getData(CategoryFieldTag, retain));
            } else if (strLine.contains(CategoryMethodTag)) {
                methodSb.append(getData(CategoryMethodTag, retain));
            } else {// contains other tag or not, but it is all other category retain data
                otherSb.append(getData(CategoryOtherTag, retain));
            }
        }

        RetainModel retain = new RetainModel();
        retain.setImportSb(importSb);
        retain.setFieldSb(fieldSb);
        retain.setMethodSb(methodSb);
        retain.setOtherSb(otherSb);
        return retain;
    }

    private static StringBuffer getRetainData(BufferedReader reader) {
        StringBuffer sb = new StringBuffer();
        String strLine;
        try {
            while (null != (strLine = reader.readLine())) {
                if (strLine.contains(EndTag)) {
                    break;
                }
                sb.append(strLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

    static StringBuffer getData(String categoryTag, StringBuffer input) {
        StringBuffer rtn = new StringBuffer();
        // retain start tag
        rtn.append(StartTag).append(categoryTag).append("\n");
        // retain data
        if (null != input && input.length() > 0) {
            rtn.append(input);
        }
        // retain end tag
        rtn.append(EndTag).append("\n");
        return rtn;
    }

}