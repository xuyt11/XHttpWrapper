package cn.ytxu.xhttp_wrapper.apidocjs.parser;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.RequestParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code.StatusCodeParser;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.util.FileUtil;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.config.property.config.CompileModelType;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class Parser {
    private List<VersionModel> versions;

    public Parser() {
    }

    public List<VersionModel> start() throws IOException {
        List<ApiDataBean> apiDatas = getApiDatasFromFile();
        versions = getVersionModelsByApiDatas(apiDatas);

        parseStatusCode();
        parseRequest();
//        parseResponses();
//        parseResponseSErrors();

        return versions;
    }

    private List<ApiDataBean> getApiDatasFromFile() throws IOException {
        // 1 get api_data.json path
        String apiDataPath = Property.getApidocProperty().getApiDataJsonPath();
        // 2 get json data from file
        String apiDataJsonStr = FileUtil.getContent(apiDataPath);
        // 3 get java object array by json data
        return JSON.parseArray(apiDataJsonStr, ApiDataBean.class);
    }

    private List<VersionModel> getVersionModelsByApiDatas(List<ApiDataBean> apiDatas) {
        String compileModelName = Property.getConfigProperty().getCompileModelName();
        CompileModelType compileModel = CompileModelType.getByName(compileModelName);
        return compileModel.generateApiTreeDependentByCompileModel(apiDatas);
    }

    private void parseStatusCode() {
        versions.forEach(version -> {
            List<StatusCodeGroupModel> statusCodeGroups = version.getStatusCodeGroups();
            new StatusCodeParser(statusCodeGroups).start();
        });
    }

    private void parseRequest() {
        versions.forEach(version -> {
            List<RequestGroupModel> requestGroups = version.getRequestGroups();
            new RequestParser(requestGroups).start();
        });
    }

}
