package cn.ytxu.apacer.entity;

import cn.ytxu.util.LogUtil;

import java.io.*;

/**
 * 自动生成文件中需要保留的数据:有可能是我自己后面加的,也有可能是其他coder加的<br>
 *     例如:简化网络请求中参数的长度<br>
 * 2016-04-18<br>
 * version v6
 *
 */
public class RetainEntity {
//    要将文件中添加了数据保留：下面是保留的格式：
    //** ytxu.retain-start *//** ytxu.import */
    //** ytxu.retain-end */
    //** ytxu.retain-start *//** ytxu.field */
    //** ytxu.retain-end */
    //** ytxu.retain-start *//** ytxu.method */
    //** ytxu.retain-end */
    //** ytxu.retain-start *//** ytxu.other */
    //** ytxu.retain-end */

    private static final String StartTag = "//** ytxu.retain-start */";
    private static final String EndTag = "//** ytxu.retain-end */";
    private static final String CategoryImportTag = "//** ytxu.import */";
    private static final String CategoryFieldTag = "//** ytxu.field */";
    private static final String CategoryMethodTag = "//** ytxu.method */";
    private static final String CategoryOtherTag = "//** ytxu.other */";

    private StringBuffer importSb;// 需要保留的import语句
    private StringBuffer fieldSb;// 需要保留的所有字段
    private StringBuffer methodSb;// 需要保留的所有方法
    private StringBuffer otherSb;// 需要保留的其他东东

    private RetainEntity() {super();}


    //********************* parser file data, and create RetainEntity *********************
    /**
     * 1、先要判断目标文件是否存在；<br>
     * 2、若存在，在判断该文件是否有数据；<br>
     * 3、若有数据，则打开输入流，获取到以上几个需要保留（retain）的数据；-->输出为一个对象<br>
     * 4、在解析文档对象，并输出文件数据时，将几个分类的数据，插入其中；
     * */
    public static RetainEntity getRetainEntity(String classFileFullName, String dirPath) {
        File targetFile = new File(dirPath, classFileFullName);
        if (!targetFile.exists()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)));
            return parserReaderAndGetRetain(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtil.e("target file`s name is " + classFileFullName);
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
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

    private static RetainEntity parserReaderAndGetRetain(BufferedReader reader) throws IOException {
        StringBuffer importSb = new StringBuffer();
        StringBuffer fieldSb = new StringBuffer();
        StringBuffer methodSb = new StringBuffer();
        StringBuffer otherSb = new StringBuffer();

        String strLine;
        while (null != (strLine = reader.readLine())) {
            if (!strLine.contains(StartTag)) {
                continue;
            }

            if (strLine.contains(CategoryImportTag)) {
                importSb.append(getRetainData(reader));
            } else if (strLine.contains(CategoryFieldTag)) {
                fieldSb.append(getRetainData(reader));
            } else if (strLine.contains(CategoryMethodTag)) {
                methodSb.append(getRetainData(reader));
            } else {// contains other tag or not, but it is all other category retain data
                otherSb.append(getRetainData(reader));
            }
        }

        RetainEntity retain = new RetainEntity();
        retain.importSb = importSb;
        retain.fieldSb = fieldSb;
        retain.methodSb = methodSb;
        retain.otherSb = otherSb;
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


    //********************* getter *********************
    public StringBuffer getImportData() {
        return getData(CategoryImportTag, importSb);
    }

    public StringBuffer getFieldData() {
        return getData(CategoryFieldTag, fieldSb);
    }

    public StringBuffer getMethodData() {
        return getData(CategoryMethodTag, methodSb);
    }

    public StringBuffer getOtherData() {
        return getData(CategoryOtherTag, otherSb);
    }

    private StringBuffer getData(String categoryTag, StringBuffer input) {
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
