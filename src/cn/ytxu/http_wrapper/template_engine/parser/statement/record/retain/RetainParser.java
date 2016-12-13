package cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.enums.RetainType;

import java.io.*;

/**
 * 自动生成文件中需要保留的数据:有可能是我自己后面加的,也有可能是其他coder加的<br>
 * 例如:简化网络请求中参数的长度<br>
 * 2016-04-18<br>
 * version v6
 */
public class RetainParser {

    /**
     * 1、先要判断目标文件是否存在；<br>
     * 2、若存在，在判断该文件是否有数据；<br>
     * 3、若有数据，则打开输入流，获取到以上几个需要保留（retain）的数据；-->输出为一个对象<br>
     * 4、在解析文档对象，并输出文件数据时，将几个分类的数据，插入其中；
     */
    public static RetainModel getRetainByFile(String dirPath, String fileName) {
        File targetFile = new File(dirPath, fileName);
        if (!targetFile.exists()) {
            return RetainModel.EmptyRetain;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(targetFile)));
            return parserReaderAndGetRetain(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            LogUtil.e("target file`s name is " + fileName);
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
        RetainModel retain = new RetainModel();
        String strLine;
        while (null != (strLine = reader.readLine())) {
            if (!strLine.contains(RetainType.StartTag)) {
                continue;
            }

            RetainType retainType = RetainType.getByTag(strLine);
            StringBuffer retainContent = getRetainData(reader);
            retainType.appendRetainContent(retain, retainContent);
        }

        return retain;
    }

    private static StringBuffer getRetainData(BufferedReader reader) {
        StringBuffer sb = new StringBuffer();
        String strLine;
        try {
            while (null != (strLine = reader.readLine())) {
                if (strLine.contains(RetainType.EndTag)) {
                    break;
                }
                sb.append(strLine).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb;
    }

}