package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.api_semi_auto_creater.parser.request.RequestParser;
import cn.ytxu.api_semi_auto_creater.parser.base.BaseParser;
import cn.ytxu.api_semi_auto_creater.parser.response.ResponseParser;
import cn.ytxu.api_semi_auto_creater.parser.response.output.sub.GetOutputsThatCanGenerateResponseEntityFileUtil;
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
    // 配置文件与xtemp模板文件的前缀名称(可以有多个目标版本)
    private static final String[] XTEMP_PREFIX_NAMES = {"NewChama-android"};//, "NewChama-ios"};

    public static void main(String... args) {
        for (int i = 0; i < XTEMP_PREFIX_NAMES.length; i++) {
            long start = System.currentTimeMillis();
            final String xTempPrefixName = XTEMP_PREFIX_NAMES[i];
            Property.load(xTempPrefixName);
            DocModel docModel = parseApiDocJs();
            createTargetFile(docModel, xTempPrefixName);

            long end = System.currentTimeMillis();
            LogUtil.w("duration time is " + (end - start));
        }
    }

    private static DocModel parseApiDocJs() {
        DocModel docModel = new BaseParser().start();
        parseRequests(docModel);
        parseResponses(docModel);
        return docModel;
    }

    private static void parseRequests(DocModel docModel) {
        for (RequestModel request : getRequests(docModel)) {
            new RequestParser(request).start();
        }
    }

    private static List<RequestModel> getRequests(DocModel docModel) {
        List<RequestModel> requests = new ArrayList<>();
        for (SectionModel section : getSections(docModel)) {
            requests.addAll(section.getRequests());
        }
        return requests;
    }

    private static List<SectionModel> getSections(DocModel docModel) {
        List<SectionModel> sections = new ArrayList<>();
        for (VersionModel version : getVersionsAfterFilter(docModel)) {
            sections.addAll(version.getSections());
        }
        return sections;
    }

    private static List<VersionModel> getVersionsAfterFilter(DocModel docModel) {
        return Property.getFilterRequestHeaderProperty().getVersionsAfterFilter(docModel);
    }

    private static void parseResponses(DocModel docModel) {
        for (ResponseModel response : getResponses(docModel)) {
            new ResponseParser(response).start();
        }
    }

    private static List<ResponseModel> getResponses(DocModel docModel) {
        List<ResponseModel> responses = new ArrayList<>();
        for (RequestModel request : getRequests(docModel)) {
            responses.addAll(request.getResponses());
        }
        return responses;
    }

    private static void createTargetFile(DocModel docModel, String xTempPrefixName) {
        createHttpApi(docModel, xTempPrefixName);
        createRequest(docModel, xTempPrefixName);
        createResponseEntity(docModel, xTempPrefixName);
        // TODO create status code file, base response entity file
    }

    private static void createHttpApi(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(XTempUtil.Suffix.HttpApi, xTempPrefixName).start();
        List<StatementRecord> records = StatementRecord.getRecords(model.getContents());
        StatementRecord.parseRecords(records);

        for (VersionModel version : getVersionsAfterFilter(docModel)) {
            String dirPath = getString(model.getFileDir(), version);
            String fileName = getString(model.getFileName(), version);

            BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
                StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, version, retain);
                writer.write(contentBuffer.toString());
            });
        }
    }

    private static void createRequest(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(XTempUtil.Suffix.Request, xTempPrefixName).start();
        List<StatementRecord> records = StatementRecord.getRecords(model.getContents());
        StatementRecord.parseRecords(records);

        for (SectionModel section : getSections(docModel)) {
            String dirPath = getString(model.getFileDir(), section);
            String fileName = getString(model.getFileName(), section);

            BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
                StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, section, retain);
                writer.write(contentBuffer.toString());
            });
        }
    }

    private static void createResponseEntity(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(XTempUtil.Suffix.Response, xTempPrefixName).start();
        List<StatementRecord> records = StatementRecord.getRecords(model.getContents());
        StatementRecord.parseRecords(records);

        List<ResponseModel> successResponses = getSuccessResponses(docModel);
        List<OutputParamModel> outputs = getOutputsThatCanGenerateResponseEntityFile(successResponses);

        for (OutputParamModel output : outputs) {
            String dirPath = getString(model.getFileDir(), output);
            String fileName = getString(model.getFileName(), output);

            BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
                StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, output, retain);
                writer.write(contentBuffer.toString());
            });
        }
    }

    private static List<ResponseModel> getSuccessResponses(DocModel docModel) {
        List<ResponseModel> successResponses = new ArrayList<>();
        for (ResponseModel response : getResponses(docModel)) {
            // TODO need set to properties file
            if ("0".equals(response.getStatusCode())) {// it`s succes response
                successResponses.add(response);
            }
        }
        return successResponses;
    }

    private static List<OutputParamModel> getOutputsThatCanGenerateResponseEntityFile(List<ResponseModel> successResponses) {
        List<OutputParamModel> outputs = new ArrayList<>();
        for (ResponseModel response : successResponses) {
            List<OutputParamModel> outputs4ThisResponse = new GetOutputsThatCanGenerateResponseEntityFileUtil(response).start();
            outputs.addAll(outputs4ThisResponse);
        }
        return outputs;
    }

    private static String getString(String content, Object reflectModel) {
        TextStatementRecord record = new TextStatementRecord(null, content);
        record.parse();
        return record.getWriteBuffer(reflectModel, null).toString().trim();
    }


}
