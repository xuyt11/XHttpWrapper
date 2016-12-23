package cn.ytxu.http_wrapper.template_engine;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template.expression.text.TextExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.creater.XHWTFileType;
import cn.ytxu.http_wrapper.template_engine.parser.model.XHWTModel;
import cn.ytxu.http_wrapper.template_engine.parser.XHWTFileParser;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;
import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/7.
 */
public class XHWTFileCreater {

    private List<VersionModel> versions;

    public XHWTFileCreater(List<VersionModel> versions) {
        this.versions = versions;
    }

    public void start() {
        createTargetFile();
    }

    private void createTargetFile() {
        for (XHWTFileType xhwtFileType : XHWTFileType.values()) {
            try {
                XHWTModel tModel = getXHWTModelByParseTemplateFile(xhwtFileType);

                List reflectDatas = xhwtFileType.getReflectiveDatas(versions);

                loopGenerateTargetFilesByReflectDatas(tModel, reflectDatas);

                LogUtil.i(XHWTFileType.class, "this template type has been successfully parsed, the type is " + xhwtFileType.name());
            } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
                LogUtil.i(XHWTFileType.class, e.getMessage());
            }
        }
    }

    private XHWTModel getXHWTModelByParseTemplateFile(XHWTFileType xhwtFileType) throws XHWTFileParser.XHWTNonNeedParsedException {
        return new XHWTFileParser(xhwtFileType).start();
    }

    private void loopGenerateTargetFilesByReflectDatas(XHWTModel tModel, List reflectDatas) {
        for (Object reflectData : reflectDatas) {
            generateTargetFileByReflectData(tModel, reflectData);
        }
    }

    private void generateTargetFileByReflectData(XHWTModel tModel, Object reflectData) {
        String dirPath = getString(tModel.getFileDir(), reflectData);
        String fileName = getString(tModel.getFileName(), reflectData);

        getRetainAndWriter4TargetFile(dirPath, fileName, (writer, retain) -> {
            StringBuffer contentBuffer = ExpressionRecord.getWriteBuffer(tModel.getRecords(), reflectData, retain);
            writer.write(contentBuffer.toString());
        });
    }

    private String getString(String content, Object reflectModel) {
        TextExpressionRecord record = new TextExpressionRecord(content);
        record.parseRecordAndSubRecords();
        return record.getWriteBuffer(reflectModel, null).toString().trim();
    }

    private void getRetainAndWriter4TargetFile(String dirPath, String fileName, OnGetWriter onGetWriter) {
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

    private interface OnGetWriter {
        void onGetWriter(Writer writer, RetainModel retain) throws IOException;
    }

}
