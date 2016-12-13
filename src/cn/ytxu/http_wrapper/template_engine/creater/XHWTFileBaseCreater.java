package cn.ytxu.http_wrapper.template_engine.creater;

import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainParser;
import cn.ytxu.http_wrapper.template_engine.parser.model.XHWTModel;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.TextStatementRecord;
import cn.ytxu.http_wrapper.common.util.FileUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Objects;

/**
 * Created by newchama on 16/4/7.
 */
public class XHWTFileBaseCreater {

    public interface OnGetWriter {
        void onGetWriter(Writer writer, RetainModel retain) throws IOException;
    }


    public static void writeContent2TargetFileByXTempAndReflectModel(XHWTModel model, Object reflectModel) {
        String dirPath = getString(model.getFileDir(), reflectModel);
        String fileName = getString(model.getFileName(), reflectModel);

        getRetainAndWriter4TargetFile(dirPath, fileName, (writer, retain) -> {
            StringBuffer contentBuffer = StatementRecord.getWriteBuffer(model.getRecords(), reflectModel, retain);
            writer.write(contentBuffer.toString());
        });
    }

    private static String getString(String content, Object reflectModel) {
        TextStatementRecord record = new TextStatementRecord(null, content);
        record.parse();
        return record.getWriteBuffer(reflectModel, null).toString().trim();
    }

    private static void getRetainAndWriter4TargetFile(String dirPath, String fileName, OnGetWriter onGetWriter) {
        if (Objects.isNull(onGetWriter)) {
            throw new RuntimeException("OnGetWriter listener is null...");
        }

        Writer writer = null;
        try {
            RetainModel retain = RetainParser.getRetainByFile(dirPath, fileName);
            String fileCharset = ConfigWrapper.getBaseConfig().getCreateFileCharset();
            writer = FileUtil.getWriter(dirPath, fileName, fileCharset);
            onGetWriter.onGetWriter(writer, retain);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (Objects.nonNull(writer)) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
