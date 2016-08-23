package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.api_semi_auto_creater.parser.request.RequestParser;
import cn.ytxu.api_semi_auto_creater.parser.base.BaseParser;
import cn.ytxu.api_semi_auto_creater.parser.response.ResponseParser;
import cn.ytxu.api_semi_auto_creater.util.XTempModel;
import cn.ytxu.api_semi_auto_creater.util.XTempUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;
import cn.ytxu.api_semi_auto_creater.util.statement.record.TextStatementRecord;
import cn.ytxu.util.LogUtil;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class NewEngine {

    public static void main(String... args) {
        long start = System.currentTimeMillis();

        Property.load();

        DocModel docModel = new BaseParser().start();
        parseRequests(docModel);
        parseResponses(docModel);

        create(docModel);

        long end = System.currentTimeMillis();
        LogUtil.w("duration time is " + (end - start));
    }

    private static void parseRequests(DocModel docModel) {
        for (RequestModel request : getRequests(docModel)) {
            new RequestParser(request).start();
        }
    }

    private static void parseResponses(DocModel docModel) {
        for (RequestModel request : getRequests(docModel)) {
            for (ResponseModel response : request.getResponses()) {
                new ResponseParser(response).start();
            }
        }
    }

    private static List<RequestModel> getRequests(DocModel docModel) {
        List<RequestModel> requests = new ArrayList<>();
        for (VersionModel version : docModel.getVersions()) {
            for (SectionModel section : version.getSections()) {
                requests.addAll(section.getRequests());
            }
        }
        return requests;
    }

    public static void create(DocModel docModel) {
        createHttpApi(docModel);
        createRequest(docModel);
//        responseCreate();
    }

    private static void createHttpApi(DocModel docModel) {
        XTempModel model = new XTempUtil(XTempUtil.Suffix.HttpApi).start();
        List<StatementRecord> records = StatementRecord.getRecords(model.getContents());
        StatementRecord.parseRecords(records);

        for (VersionModel version : docModel.getVersions()) {
            String dirPath = getString(model.getFileDir(), version);
            String fileName = getString(model.getFileName(), version);

            BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
                StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, version, retain);
                writer.write(contentBuffer.toString());
            });
        }
    }

    private static void createRequest(DocModel docModel) {
        XTempModel model = new XTempUtil(XTempUtil.Suffix.Request).start();
        List<StatementRecord> records = StatementRecord.getRecords(model.getContents());
        StatementRecord.parseRecords(records);

        for (VersionModel version : docModel.getVersions()) {
            for (SectionModel section : version.getSections()) {
                String dirPath = getString(model.getFileDir(), section);
                String fileName = getString(model.getFileName(), section);

                BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
                    StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, section, retain);
                    writer.write(contentBuffer.toString());
                });
            }
        }
    }

    private static String getString(String content, Object reflectModel) {
        TextStatementRecord record = new TextStatementRecord(null, content);
        record.parse();
        return record.getWriteBuffer(reflectModel, null).toString().trim();
    }


}
