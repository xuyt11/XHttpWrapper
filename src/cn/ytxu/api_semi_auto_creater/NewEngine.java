package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.api_semi_auto_creater.config.Suffix;
import cn.ytxu.api_semi_auto_creater.config.property.status_code.StatusCodeProperty;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.api_semi_auto_creater.parser.StatusCodeParser;
import cn.ytxu.api_semi_auto_creater.parser.request.RequestParser;
import cn.ytxu.api_semi_auto_creater.parser.base.BaseParser;
import cn.ytxu.api_semi_auto_creater.parser.response.ResponseParser;
import cn.ytxu.api_semi_auto_creater.parser.response.output.sub.GetOutputsThatCanGenerateResponseEntityFileUtil;
import cn.ytxu.api_semi_auto_creater.xtemp_parser.XTempModel;
import cn.ytxu.api_semi_auto_creater.xtemp_parser.XTempUtil;
import cn.ytxu.api_semi_auto_creater.xtemp_parser.statement.StatementRecord;
import cn.ytxu.api_semi_auto_creater.xtemp_parser.statement.record.TextStatementRecord;
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
        parseStatusCodes(docModel);
        parseRequests(docModel);
        parseResponses(docModel);
        return docModel;
    }

    private static void parseStatusCodes(DocModel docModel) {
        List<SectionModel> statusCodes = StatusCodeProperty.getInstance().getStatusCodes(docModel);
        for (SectionModel statusCode : statusCodes) {
            for (RequestModel request : statusCode.getRequests()) {
                new StatusCodeParser(request).start();
            }
        }
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
        return Property.getFilterProperty().getVersionsAfterFilter(docModel);
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
        XTempModel model = new XTempUtil(Suffix.HttpApi, xTempPrefixName).start();

        for (VersionModel version : getVersionsAfterFilter(docModel)) {
            writeContent2TargetFileByXTempAndReflectModel(model, version);
        }
    }

    private static void writeContent2TargetFileByXTempAndReflectModel(XTempModel model, Object reflectModel) {
        String dirPath = getString(model.getFileDir(), reflectModel);
        String fileName = getString(model.getFileName(), reflectModel);

        BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
            StringBuffer contentBuffer = StatementRecord.getWriteBuffer(model.getRecords(), reflectModel, retain);
            writer.write(contentBuffer.toString());
        });
    }

    private static String getString(String content, Object reflectModel) {
        TextStatementRecord record = new TextStatementRecord(null, content);
        record.parse();
        return record.getWriteBuffer(reflectModel, null).toString().trim();
    }

    private static void createRequest(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(Suffix.Request, xTempPrefixName).start();

        for (SectionModel section : getSections(docModel)) {
            writeContent2TargetFileByXTempAndReflectModel(model, section);
        }
    }

    private static void createResponseEntity(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(Suffix.Response, xTempPrefixName).start();

        List<ResponseModel> successResponses = getSuccessResponses(docModel);
        List<OutputParamModel> outputs = getOutputsThatCanGenerateResponseEntityFile(successResponses);

        for (OutputParamModel output : outputs) {
            writeContent2TargetFileByXTempAndReflectModel(model, output);
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


}
