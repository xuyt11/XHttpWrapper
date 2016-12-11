package cn.ytxu.xhttp_wrapper.xtemp.creater;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.message_type.json.output.sub.GetOutputsThatCanGenerateResponseEntityFileUtil;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.config.XHWTFileType;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;
import cn.ytxu.xhttp_wrapper.model.response.OutputParamModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.xtemp.parser.model.XTempModel;
import cn.ytxu.xhttp_wrapper.xtemp.parser.XTempUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class ApiFileCreater {

    private List<VersionModel> versions;
    private String xhwtConfigPath;

    public ApiFileCreater(List<VersionModel> versions, String xhwtConfigPath) {
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
        XTempModel model;
        try {
            model = new XTempUtil(XHWTFileType.HttpApi, xhwtConfigPath).start();
        } catch (XTempUtil.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }
        for (VersionModel version : VersionModel.getVersions(versions)) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, version);
        }
    }

    private void createRequest() {
        XTempModel model;
        try {
            model = new XTempUtil(XHWTFileType.Request, xhwtConfigPath).start();
        } catch (XTempUtil.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }
        for (RequestGroupModel requestGroup : VersionModel.getRequestGroups(versions)) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, requestGroup);
        }
    }

    private void createResponseEntity() {
        XTempModel model;
        try {
            model = new XTempUtil(XHWTFileType.Response, xhwtConfigPath).start();
        } catch (XTempUtil.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }
        List<ResponseModel> okResponses = getOKResponses();
        for (OutputParamModel output : getOutputsThatCanGenerateResponseEntityFile(okResponses)) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, output);
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
        XTempModel model;
        try {
            model = new XTempUtil(XHWTFileType.StatusCode, xhwtConfigPath).start();
        } catch (XTempUtil.XHWTTemplateFileNotExistsException e) {
            e.printStackTrace();
            return;
        }

        List<StatusCodeGroupModel> statusCodeGroups = ConfigWrapper.getStatusCode().getStatusCodeGroups(versions);
        for (StatusCodeGroupModel statusCodeGroup : statusCodeGroups) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, statusCodeGroup);
        }
    }

    private void createBaseResponse() {
        XTempModel model;
        try {
            model = new XTempUtil(XHWTFileType.BaseResponse, xhwtConfigPath).start();
        } catch (XTempUtil.XHWTTemplateFileNotExistsException e) {
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

        BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, subsOfErrorsSVersion);
    }

}
