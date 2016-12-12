package cn.ytxu.xhttp_wrapper.xhwt_engine.creater;

import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.retain.RetainParser;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.model.XHWTModel;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.StatementRecord;
import cn.ytxu.xhttp_wrapper.xhwt_engine.parser.statement.record.TextStatementRecord;
import cn.ytxu.util.FileUtil;
import cn.ytxu.util.LogUtil;

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


    public static void writeContent2TargetFileByXTempAndReflectModel(XHWTModel model, Object reflectModel) {
        String dirPath = getString(model.getFileDir(), reflectModel);
        String fileName = getString(model.getFileName(), reflectModel);

        getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainModel retain) -> {
            StringBuffer contentBuffer = StatementRecord.getWriteBuffer(model.getRecords(), reflectModel, retain);
            writer.write(contentBuffer.toString());
        });
    }

    private static String getString(String content, Object reflectModel) {
        TextStatementRecord record = new TextStatementRecord(null, content);
        record.parse();
        return record.getWriteBuffer(reflectModel, null).toString().trim();
    }

    private static void getWriter4TargetFile(String dirPath, String fileName, OnGetWriter onGetWriter) {
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
