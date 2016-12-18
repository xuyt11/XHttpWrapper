package cn.ytxu.http_wrapper.template_engine.creater;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub.GetOutputsThatCanGenerateResponseEntityFileUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.*;

/**
 * Created by ytxu on 16/12/14.
 */
public class ReflectiveDataConvertor {

    public static List getHttpApiReflectiveDatas(List<VersionModel> versions) {
        return VersionModel.getVersions(versions);
    }

    public static List getRequestReflectiveDatas(List<VersionModel> versions) {
        return VersionModel.getRequestGroups(versions);
    }

    public static List getRequestParamReflectiveDatas(List<VersionModel> versions) {
        List reflectiveDatas = new ArrayList();
        for (RequestModel request : VersionModel.getRequests(versions)) {
            if (request.needGenerateOptionalRequestMethod()) {
                reflectiveDatas.add(request);
            }
        }
        return reflectiveDatas;
    }

    public static List getResponseReflectiveDatas(List<VersionModel> versions) {
        return getOutputsThatCanGenerateResponseEntityFile(getOKResponses(versions));
    }

    private static List<ResponseModel> getOKResponses(List<VersionModel> versions) {
        final String statusCodeOKNumber = ConfigWrapper.getStatusCode().getOkNumber();
        List<ResponseModel> successResponses = new ArrayList<>();
        for (ResponseModel response : VersionModel.getSuccessResponses(versions)) {
            if (statusCodeOKNumber.equals(response.getStatusCode())) {// it`s ok response
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

    public static List getBaseResponseReflectiveDatas(List<VersionModel> versions, boolean isPolymerization) {
        if (!isPolymerization) {
            return versions;
        }

        // it`s multi version, but use the single base response
        List<OutputParamModel> subsOfErrors = new ArrayList<>(20);
        for (VersionModel version : versions) {
            subsOfErrors.addAll(version.getSubsOfErrors());
        }

        String errorFieldsName = getErrorFieldsName(subsOfErrors);
        subsOfErrors = new ArrayList<>(new HashSet(subsOfErrors));// deduplicated
        String errorFieldsName4Deduplicated = getErrorFieldsName(subsOfErrors);
        Collections.sort(subsOfErrors);
        String errorFieldsName4Sorted = getErrorFieldsName(subsOfErrors);

        VersionModel subsOfErrorsSVersion = new VersionModel("subs of errors`s version");
        subsOfErrorsSVersion.setSubsOfErrors(subsOfErrors);
        return Arrays.asList(subsOfErrorsSVersion);
    }

    /**
     * for debug
     */
    private static String getErrorFieldsName(List<OutputParamModel> subsOfErrors) {
        String errorFieldsName = "";
        for (OutputParamModel subsOfError : subsOfErrors) {
            errorFieldsName += subsOfError.getName() + ", ";
        }
        return errorFieldsName;
    }

    public static List getStatusCodeReflectiveDatas(List<VersionModel> versions) {
        return ConfigWrapper.getStatusCode().getStatusCodeGroups(versions);
    }

}
