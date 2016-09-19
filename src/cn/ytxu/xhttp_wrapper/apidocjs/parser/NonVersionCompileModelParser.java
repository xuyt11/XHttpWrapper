package cn.ytxu.xhttp_wrapper.apidocjs.parser;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeProperty;
import cn.ytxu.xhttp_wrapper.model.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.RequestModel;
import cn.ytxu.xhttp_wrapper.model.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.*;


/**
 * Created by Administrator on 2016/9/17.
 * 3.2 else is non_version, section-->request, and must remove old version request, just keep the latest version
 */
public class NonVersionCompileModelParser {
    private List<ApiDataBean> apiDatas;
    private Map<String, Integer> orderVersionIndexs;

    public NonVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        orderVersionIndexs = getOrderVersionIndexs();
        VersionModel version = VersionModel.NON_VERSION_MODEL;
        foreachStoreLatestVersionSApiData(version);
        return Collections.singletonList(version);
    }

    /**
     * 获取版本的顺序，为后面的解析提供版本顺序，因为non_version模式需要过滤掉老版本request
     */
    private Map<String, Integer> getOrderVersionIndexs() {
        List<String> orderVersions = Property.getConfigProperty().getOrderVersions();
        Map<String, Integer> orderVersionIndexs = new LinkedHashMap<>(orderVersions.size());
        for (String orderVersion : orderVersions) {
            orderVersionIndexs.put(orderVersion, orderVersions.indexOf(orderVersion));
        }
        return orderVersionIndexs;
    }

    private void foreachStoreLatestVersionSApiData(VersionModel version) {
        for (ApiDataBean apiData : apiDatas) {
            if (isNotNeedParsedVersion(apiData)) {
                continue;
            }
            if (isAStatusCodeGroup4ApiData(apiData)) {
                setApiData2StatusCodesIfIsLatestVersion(version, apiData);
                continue;
            }
            setApiData2RequestGroupIfIsLatestVersion(version, apiData);
        }
    }

    private boolean isNotNeedParsedVersion(ApiDataBean apiData) {
        final Integer index = findVersionIndex(apiData.getVersion());
        return Objects.isNull(index);
    }

    private Integer findVersionIndex(String version) {
        return orderVersionIndexs.get(version);
    }

    private boolean isAStatusCodeGroup4ApiData(ApiDataBean apiData) {
        return StatusCodeProperty.getInstance().isStatusCodeGroup(apiData.getGroup());
    }

    private void setApiData2StatusCodesIfIsLatestVersion(VersionModel version, ApiDataBean scApiData) {
        try {
            StatusCodeGroupModel scGroup = findSameNameSCGroup(version, scApiData);
            if (needStoreTheApiData(scGroup, scApiData)) {
                setApiData2StatusCodes(version, scApiData);
            }
        } catch (NotFoundSameNameStatusCodeGroupException ignore) {
            setApiData2StatusCodes(version, scApiData);
        }
    }

    private StatusCodeGroupModel findSameNameSCGroup(VersionModel version, ApiDataBean scApiData) {
        List<StatusCodeGroupModel> scGroups = version.getStatusCodeGroups();
        for (StatusCodeGroupModel scGroup : scGroups) {
            if (scGroup.getName().equals(scApiData.getName())) {
                return scGroup;
            }
        }
        throw new NotFoundSameNameStatusCodeGroupException();
    }

    private static final class NotFoundSameNameStatusCodeGroupException extends RuntimeException {
    }

    private boolean needStoreTheApiData(StatusCodeGroupModel scGroup, ApiDataBean scApiData) {
        final Integer currIndex = findVersionIndex(scApiData.getVersion());
        final Integer storeIndex = findVersionIndex(scGroup.getVersion());
        return currIndex > storeIndex;
    }

    private void setApiData2StatusCodes(VersionModel version, ApiDataBean apiData) {
        StatusCodeGroupModel scGroup = new StatusCodeGroupModel(version, apiData);
        version.addStatusCodeGroup(scGroup);
    }

    private void setApiData2RequestGroupIfIsLatestVersion(VersionModel version, ApiDataBean apiData) {
        RequestGroupModel requestGroup;
        try {
            requestGroup = findSameNameRequestGroup(version, apiData);
        } catch (NotFoundSameNameRequestGroupException ignore) {
            requestGroup = createAndAddRequestGroup(version, apiData);
        }

        try {
            RequestModel request = findSameNameRequest(requestGroup, apiData);
            if (needReplaceWithApiData(request, apiData)) {
                replaceRequestWithApiDataInRequestGroup(requestGroup, request, apiData);
            }
        } catch (NotFoundSameNameRequestException ignore) {
            createAndAddRequest(requestGroup, apiData);
        }
    }
    private RequestGroupModel findSameNameRequestGroup(VersionModel version, ApiDataBean apiData) {
        List<RequestGroupModel> requestGroups = version.getRequestGroups();
        final String requestGroupName = apiData.getGroup();
        for (RequestGroupModel requestGroup : requestGroups) {
            if (requestGroup.getName().equals(requestGroupName)) {
                return requestGroup;
            }
        }
        throw new NotFoundSameNameRequestGroupException();
    }

    private static final class NotFoundSameNameRequestGroupException extends RuntimeException {
    }

    private RequestModel findSameNameRequest(RequestGroupModel requestGroup, ApiDataBean apiData) {
        List<RequestModel> requests = requestGroup.getRequests();
        final String requestName = apiData.getName();
        for (RequestModel request : requests) {
            if (request.getName().equals(requestName)) {
                return request;
            }
        }
        throw new NotFoundSameNameRequestException();
    }

    private static final class NotFoundSameNameRequestException extends RuntimeException {
    }

    private RequestGroupModel createAndAddRequestGroup(VersionModel version, ApiDataBean apiData) {
        RequestGroupModel requestGroup = new RequestGroupModel(version, apiData);
        version.addRequestGroup(requestGroup);
        return requestGroup;
    }

    private void createAndAddRequest(RequestGroupModel requestGroup, ApiDataBean apiData) {
        RequestModel request = new RequestModel(requestGroup, apiData);
        requestGroup.addRequest(request);
    }

    private boolean needReplaceWithApiData(RequestModel request, ApiDataBean apiData) {
        final Integer currIndex = findVersionIndex(apiData.getVersion());
        final Integer storeIndex = findVersionIndex(request.getVersion());
        return currIndex > storeIndex;
    }

    private void replaceRequestWithApiDataInRequestGroup(RequestGroupModel requestGroup, RequestModel request, ApiDataBean apiData) {
        List<RequestModel> requests = requestGroup.getRequests();

        int replaceIndex = requests.indexOf(request);

        RequestModel replaceRequest = new RequestModel(requestGroup, apiData);
        requests.set(replaceIndex, replaceRequest);
    }


}
