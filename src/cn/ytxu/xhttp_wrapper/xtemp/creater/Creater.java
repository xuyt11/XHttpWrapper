package cn.ytxu.xhttp_wrapper.xtemp.creater;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.json.output.sub.GetOutputsThatCanGenerateResponseEntityFileUtil;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.config.Suffix;
import cn.ytxu.xhttp_wrapper.model.VersionModel;
import cn.ytxu.xhttp_wrapper.model.response.OutputParamModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.xtemp.parser.XTempModel;
import cn.ytxu.xhttp_wrapper.xtemp.parser.XTempUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class Creater {

    private List<VersionModel> versions;
    private String xTempPrefixName;

    public Creater(List<VersionModel> versions, String xTempPrefixName) {
        this.versions = versions;
        this.xTempPrefixName = xTempPrefixName;
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
        XTempModel model = new XTempUtil(Suffix.HttpApi, xTempPrefixName).start();
        VersionModel.getVersions(versions).forEach(version ->
                BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, version)
        );
    }

    private void createRequest() {
        XTempModel model = new XTempUtil(Suffix.Request, xTempPrefixName).start();
        VersionModel.getSections(versions).forEach(section ->
                BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, section)
        );
    }

    private void createResponseEntity() {
        XTempModel model = new XTempUtil(Suffix.Response, xTempPrefixName).start();
        List<ResponseModel> okResponses = getOKResponses();
        getOutputsThatCanGenerateResponseEntityFile(okResponses).forEach(output ->
                BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, output)
        );
    }

    private List<ResponseModel> getOKResponses() {
        final String statusCodeOKNumber = ConfigWrapper.getStatusCode().getOkNumber();
        List<ResponseModel> successResponses = new ArrayList<>();
        for (ResponseModel response : VersionModel.getResponses(versions)) {
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
        XTempModel model = new XTempUtil(Suffix.StatusCode, xTempPrefixName).start();

        List<StatusCodeGroupModel> statusCodeGroups = ConfigWrapper.getStatusCode().getStatusCodeGroups(versions);
        for (StatusCodeGroupModel statusCodeGroup : statusCodeGroups) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, statusCodeGroup);
        }
    }

    private void createBaseResponse() {
        XTempModel model = new XTempUtil(Suffix.BaseResponse, xTempPrefixName).start();
        BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, VersionModel.NON_VERSION_MODEL);
    }

}
