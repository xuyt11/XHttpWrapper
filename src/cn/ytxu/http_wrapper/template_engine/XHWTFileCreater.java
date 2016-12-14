package cn.ytxu.http_wrapper.template_engine;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub.GetOutputsThatCanGenerateResponseEntityFileUtil;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.request.RequestGroupModel;
import cn.ytxu.http_wrapper.model.request.RequestModel;
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
        createRequestParam();
        createResponseEntity();
        createStatusCode();
        createBaseResponse();
    }

    private void createHttpApi() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.HttpApi).start();
        } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
            LogUtil.i(XHWTFileCreater.class, e.getMessage());
            return;
        }
        for (VersionModel version : VersionModel.getVersions(versions)) {
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, version);
        }
        LogUtil.i(XHWTFileCreater.class, "this template type has been successfully parsed, the type is " + XHWTFileType.HttpApi.name());
    }

    private void createRequest() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.Request).start();
        } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
            LogUtil.i(XHWTFileCreater.class, e.getMessage());
            return;
        }
        for (RequestGroupModel requestGroup : VersionModel.getRequestGroups(versions)) {
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, requestGroup);
        }
        LogUtil.i(XHWTFileCreater.class, "this template type has been successfully parsed, the type is " + XHWTFileType.Request.name());
    }

    private void createRequestParam() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.RequestParam).start();
        } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
            LogUtil.i(XHWTFileCreater.class, e.getMessage());
            return;
        }
        for (RequestModel request : VersionModel.getRequests(versions)) {
            if (!request.needGenerateOptionalRequestMethod()) {
                continue;
            }
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, request);
        }
        LogUtil.i(XHWTFileCreater.class, "this template type has been successfully parsed, the type is " + XHWTFileType.RequestParam.name());
    }

    private void createResponseEntity() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.Response).start();
        } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
            LogUtil.i(XHWTFileCreater.class, e.getMessage());
            return;
        }
        List<ResponseModel> okResponses = getOKResponses();
        for (OutputParamModel output : getOutputsThatCanGenerateResponseEntityFile(okResponses)) {
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, output);
        }
        LogUtil.i(XHWTFileCreater.class, "this template type has been successfully parsed, the type is " + XHWTFileType.Response.name());
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
            model = new XHWTFileParser(XHWTFileType.StatusCode).start();
        } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
            LogUtil.i(XHWTFileCreater.class, e.getMessage());
            return;
        }

        List<StatusCodeGroupModel> statusCodeGroups = ConfigWrapper.getStatusCode().getStatusCodeGroups(versions);
        for (StatusCodeGroupModel statusCodeGroup : statusCodeGroups) {
            XHWTFileBaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, statusCodeGroup);
        }
        LogUtil.i(XHWTFileCreater.class, "this template type has been successfully parsed, the type is " + XHWTFileType.StatusCode.name());
    }

    private void createBaseResponse() {
        XHWTModel model;
        try {
            model = new XHWTFileParser(XHWTFileType.BaseResponse).start();
        } catch (XHWTFileParser.XHWTNonNeedParsedException e) {
            LogUtil.i(XHWTFileCreater.class, e.getMessage());
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
        LogUtil.i(XHWTFileCreater.class, "this template type has been successfully parsed, the type is " + XHWTFileType.StatusCode.name());
    }

}
