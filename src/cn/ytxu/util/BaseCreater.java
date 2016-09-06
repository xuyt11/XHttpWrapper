package cn.ytxu.util;

import cn.ytxu.api_semi_auto_creater.parser.retain.RetainModel;
import cn.ytxu.api_semi_auto_creater.parser.retain.RetainParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * Created by newchama on 16/4/7.
 */
public class BaseCreater {

    public interface OnGetWriter {
        void onGetWriter(Writer writer, RetainModel retain) throws IOException;
    }

    public static void getWriter4TargetFile(String dirPath, String fileName, OnGetWriter onGetWriter) {
        Writer writer = null;
        try {
            RetainModel retain = RetainParser.getRetainByFile(fileName, dirPath);
            writer = FileUtil.getWriter(fileName, dirPath);
            if (null == onGetWriter) {
                LogUtil.w("OnGetWriter listener is null...");
                return;
            }

            onGetWriter.onGetWriter(writer, retain);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeWriter(writer);
        }
    }

    private static void closeWriter(Writer writer) {
        if (null == writer) {
            return;
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
