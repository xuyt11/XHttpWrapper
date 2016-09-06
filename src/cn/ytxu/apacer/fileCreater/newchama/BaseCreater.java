package cn.ytxu.apacer.fileCreater.newchama;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.util.FileUtil;
import cn.ytxu.util.LogUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by newchama on 16/4/7.
 */
public class BaseCreater {

    private static final String Table = "\t";
    private static List<String> tables = null;

    static {
        if (null == tables) {
            tables = new ArrayList<>(20);

            String tab = "";
            tables.add(tab);// index==0

            for (int i = 1; i < 20; i++) {
                tab += Table;
                tables.add(tab);
            }
        }
    }

    /**
     * 获取制表符,根据index
     * @param index index must smaller 20
     */
    public static String getTable(int index) {
        return tables.get(index);
    }


    //*********************** get writer ***********************
//    BaseCreater.getWriter4TargetFile(fileName, dirPath, new BaseCreater.OnGetWriter(){
//        @Override
//        public void onGetWriter(Writer writer) throws IOException {
//
//        }
//    });

    public static void closeWriter(Writer writer) {
        if (null == writer) {
            return;
        }

        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public interface OnGetWriter {
        void onGetWriter(Writer writer, RetainEntity retain) throws IOException;
    }

    public static void getWriter4TargetFile(String dirPath, String fileName, OnGetWriter onGetWriter) {
        Writer writer = null;
        try {
            RetainEntity retain = RetainEntity.getRetainEntity(fileName, dirPath);
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
}
