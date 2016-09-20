package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.model.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.RequestModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20.
 * convert ApiData to request model , and if is the latest version
 */
public class NonVersionRequestConverter {
    private VersionModel version;
    private Map<String, Integer> orderVersionIndexs;
    private ApiDataBean apiData;


    public NonVersionRequestConverter(VersionModel version, Map<String, Integer> orderVersionIndexs, ApiDataBean apiData) {
        this.version = version;
        this.orderVersionIndexs = orderVersionIndexs;
        this.apiData = apiData;
    }

    public void start() {
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

    private Integer findVersionIndex(String version) {
        return orderVersionIndexs.get(version);
    }

    private void replaceRequestWithApiDataInRequestGroup(RequestGroupModel requestGroup, RequestModel request, ApiDataBean apiData) {
        List<RequestModel> requests = requestGroup.getRequests();

        int replaceIndex = requests.indexOf(request);

        RequestModel replaceRequest = new RequestModel(requestGroup, apiData);
        requests.set(replaceIndex, replaceRequest);
    }

}
