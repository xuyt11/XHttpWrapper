package cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data;

import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/1.
 */
public class ApiDataHelper {

    private static ApiDataHelper instance;

    // 用于替换BaseModel中的element,减少框架内部与外部框架的耦合
    private final Map<RequestModel, ApiDataBean> requestCache = new HashMap<>(1000);
    private final Map<StatusCodeGroupModel, ApiDataBean> statusCodeGroupCache = new HashMap<>(10);
//    private final Map<StatusCodeModel, ApiDataBean> statusCodeCache = new HashMap<>(100);

    public static void reload() {
        if (instance != null) {
            instance.requestCache.clear();
            instance.statusCodeGroupCache.clear();
        }
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


    //********************* create *********************
    public ApiDataBean getApiData(RequestModel request) {
        return requestCache.get(request);
    }

    public ApiDataBean getApiData(StatusCodeGroupModel statusCodeGroup) {
        return requestCache.get(statusCodeGroup);
    }


    //********************* create *********************
    public RequestGroupModel createRequestGroup(VersionModel version, ApiDataBean apiData) {
        return new RequestGroupModel(version, apiData.getGroup());
    }

    public RequestModel createRequest(RequestGroupModel requestGroup, ApiDataBean apiData) {
        RequestModel request = new RequestModel(requestGroup, apiData);
        request.init(apiData.getType(), apiData.getUrl(), apiData.getTitle(), apiData.getVersion(),
                apiData.getName(), apiData.getGroup(), apiData.getDescription());
        requestCache.put(request, apiData);
        return request;
    }

    public StatusCodeGroupModel createStatusCodeGroup(VersionModel version, ApiDataBean apiData) {
        StatusCodeGroupModel scGroup = new StatusCodeGroupModel(version, apiData);
        scGroup.init(apiData.getTitle(), apiData.getName(), apiData.getVersion());
        statusCodeGroupCache.put(scGroup, apiData);
        return scGroup;
    }

}
