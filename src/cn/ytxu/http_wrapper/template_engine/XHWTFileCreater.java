package cn.ytxu.http_wrapper.template_engine;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub.GetOutputsThatCanGenerateResponseEntityFileUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.request.RequestGroupModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.http_wrapper.template_engine.creater.XHWTFileBaseCreater;
import cn.ytxu.http_wrapper.template_engine.parser.model.XHWTModel;
import cn.ytxu.http_wrapper.template_engine.parser.XHWTFileParser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class XHWTFileCreater {

    private List<VersionModel> versions;
    private String xhwtConfigPath;

    public XHWTFileCreater(List<VersionModel> versions, String xhwtConfigPath) {
        this.versions = versions;
        this.xhwtConfigPath = xhwtConfigPath;
    }

    public void start() {
        createTargetFile();
    }

    private void createTargetFile() {
        createHttpApi();
        createRequest();
        createResponseEntity();
        createStatusCode();
        createBaseResponse();
    }

    private void createHttpApi() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.HttpApi, xhwtConfigPath).start();
        } catch (XHWTFileParser.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }
        for (VersionModel version : VersionModel.getVersions(versions)) {
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, version);
        }
    }

    private void createRequest() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.Request, xhwtConfigPath).start();
        } catch (XHWTFileParser.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }
        for (RequestGroupModel requestGroup : VersionModel.getRequestGroups(versions)) {
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, requestGroup);
        }
    }

    private void createResponseEntity() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.Response, xhwtConfigPath).start();
        } catch (XHWTFileParser.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }
        List<ResponseModel> okResponses = getOKResponses();
        for (OutputParamModel output : getOutputsThatCanGenerateResponseEntityFile(okResponses)) {
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, output);
        }
    }

    private List<ResponseModel> getOKResponses() {
        final String statusCodeOKNumber = ConfigWrapper.getStatusCode().getOkNumber();
        List<ResponseModel> successResponses = new ArrayList<>();
        for (ResponseModel response : VersionModel.getSuccessResponses(versions)) {
            if (statusCodeOKNumber.equals(response.getStatusCode())) {// it`s ok response
                successResponses.add(response);
            }
        }
        return successResponses;
    }

    private List<OutputParamModel> getOutputsThatCanGenerateResponseEntityFile(List<ResponseModel> successResponses) {
        List<OutputParamModel> outputs = new ArrayList<>();
        for (ResponseModel response : successResponses) {
            List<OutputParamModel> outputs4ThisResponse = new GetOutputsThatCanGenerateResponseEntityFileUtil(response).start();
            outputs.addAll(outputs4ThisResponse);
        }
        return outputs;
    }

    private void createStatusCode() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.StatusCode, xhwtConfigPath).start();
        } catch (XHWTFileParser.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }

        List<StatusCodeGroupModel> statusCodeGroups = ConfigWrapper.getStatusCode().getStatusCodeGroups(versions);
        for (StatusCodeGroupModel statusCodeGroup : statusCodeGroups) {
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, statusCodeGroup);
        }
    }

    private void createBaseResponse() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.BaseResponse, xhwtConfigPath).start();
        } catch (XHWTFileParser.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }

        // it`s multi version, but use the single base response
        List<OutputParamModel> subsOfErrors = new ArrayList<>(20);
        for (VersionModel version : versions) {
            subsOfErrors.addAll(version.getSubsOfErrors());
        }
        subsOfErrors = new ArrayList<>(new HashSet(subsOfErrors));// deduplicated

        VersionModel subsOfErrorsSVersion = new VersionModel("subs of errors`s version");
        subsOfErrorsSVersion.setSubsOfErrors(subsOfErrors);

        XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, subsOfErrorsSVersion);
    }

}
