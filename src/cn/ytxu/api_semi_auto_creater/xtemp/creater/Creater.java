package cn.ytxu.api_semi_auto_creater.xtemp.creater;

import cn.ytxu.xhttp_wrapper.config.Suffix;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeProperty;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.api_semi_auto_creater.model.status_code.StatusCodeCategoryModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output.sub.GetOutputsThatCanGenerateResponseEntityFileUtil;
import cn.ytxu.api_semi_auto_creater.xtemp.parser.XTempModel;
import cn.ytxu.api_semi_auto_creater.xtemp.parser.XTempUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class Creater {

    private DocModel docModel;
    private String xTempPrefixName;

    public Creater(DocModel docModel, String xTempPrefixName) {
        this.docModel = docModel;
        this.xTempPrefixName = xTempPrefixName;
    }

    public void start() {
        createTargetFile();
    }

    private void createTargetFile() {
        createHttpApi(docModel, xTempPrefixName);
        createRequest(docModel, xTempPrefixName);
        createResponseEntity(docModel, xTempPrefixName);
        createStatusCode(docModel, xTempPrefixName);
        createBaseResponse(docModel, xTempPrefixName);
    }

    private void createHttpApi(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(Suffix.HttpApi, xTempPrefixName).start();

        for (VersionModel version : docModel.getVersions(true)) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, version);
        }
    }

    private void createRequest(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(Suffix.Request, xTempPrefixName).start();

        for (SectionModel section : docModel.getSections(true)) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, section);
        }
    }

    private void createResponseEntity(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(Suffix.Response, xTempPrefixName).start();

        List<ResponseModel> successResponses = getSuccessResponses(docModel);
        List<OutputParamModel> outputs = getOutputsThatCanGenerateResponseEntityFile(successResponses);

        for (OutputParamModel output : outputs) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, output);
        }
    }

    private List<ResponseModel> getSuccessResponses(DocModel docModel) {
        final String statusCodeOKNumber = StatusCodeProperty.getInstance().getOkNumber();
        List<ResponseModel> successResponses = new ArrayList<>();
        for (ResponseModel response : docModel.getResponses(true)) {
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


    private void createStatusCode(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(Suffix.StatusCode, xTempPrefixName).start();

        List<StatusCodeCategoryModel> statusCodes = StatusCodeProperty.getInstance().getStatusCodes(docModel, true);
        for (StatusCodeCategoryModel statusCode : statusCodes) {
            BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, statusCode);
        }
    }

    private void createBaseResponse(DocModel docModel, String xTempPrefixName) {
        XTempModel model = new XTempUtil(Suffix.BaseResponse, xTempPrefixName).start();
        BaseCreater.writeContent2TargetFileByXTempAndReflectModel(model, docModel);
    }

}
