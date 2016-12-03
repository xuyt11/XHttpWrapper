package cn.ytxu.xhttp_wrapper.apidocjs.parser;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.RequestParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.ResponseSErrorParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code.StatusCodeParser;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.common.CompileModel;
import cn.ytxu.xhttp_wrapper.model.ModelHelper;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class ApidocjsDataParser {
    private List<VersionModel> versions;

    public ApidocjsDataParser() {
    }

    public List<VersionModel> start() throws IOException {
        ModelHelper.reload();
        ApidocjsHelper.reload();
        List<ApiDataBean> apiDatas = ApidocjsHelper.getApiDatasFromFile();
        versions = createVersionModelsByApiDatas(apiDatas);

        parseStatusCode();
        parseRequest();
//        parseResponses();
        parseResponseSErrors();

        return versions;
    }

    private List<VersionModel> createVersionModelsByApiDatas(List<ApiDataBean> apiDatas) {
        CompileModel compileModel = ConfigWrapper.getBaseConfig().getCompileModelType();
        return compileModel.createApiDatasFromApidocJsData(apiDatas);
    }

    private void parseStatusCode() {
        for (VersionModel version : versions) {
            List<StatusCodeGroupModel> statusCodeGroups = version.getStatusCodeGroups();
            new StatusCodeParser(statusCodeGroups).start();
        }
    }

    private void parseRequest() {
        for (VersionModel version : versions) {
            List<RequestGroupModel> requestGroups = version.getRequestGroups();
            new RequestParser(requestGroups).start();
        }
    }

    private void parseResponseSErrors() {
        for (VersionModel version : versions) {
            new ResponseSErrorParser(version).start();
        }
    }

}
