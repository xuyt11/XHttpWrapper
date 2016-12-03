package cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data;

import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

/**
 * Created by Administrator on 2016/12/1.
 */
public class ApiDataHelper {

    private static ApiDataHelper instance;

    public static void reload() {
        instance = new ApiDataHelper();
    }

    private ApiDataHelper() {
    }

    public static ApiDataHelper getInstance() {
        return instance;
    }

    public boolean isAStatusCodeGroup(ApiDataBean apiData) {
        return ConfigWrapper.getStatusCode().isStatusCodeGroup(apiData.getGroup());
    }

    public RequestGroupModel createRequestGroup(VersionModel version, ApiDataBean apiData) {
        return new RequestGroupModel(version, apiData.getGroup());
    }

    public RequestModel createRequest(RequestGroupModel requestGroup, ApiDataBean apiData) {
        RequestModel request = new RequestModel(requestGroup, apiData);
        request.init(apiData.getType(), apiData.getUrl(), apiData.getTitle(), apiData.getVersion(),
                apiData.getName(), apiData.getGroup(), apiData.getDescription());
        return request;
    }

    public StatusCodeGroupModel createStatusCodeGroup(VersionModel version, ApiDataBean apiData) {
        StatusCodeGroupModel scGroup = new StatusCodeGroupModel(version, apiData);
        scGroup.init(apiData.getTitle(), apiData.getName(), apiData.getVersion());
        return scGroup;
    }

}
