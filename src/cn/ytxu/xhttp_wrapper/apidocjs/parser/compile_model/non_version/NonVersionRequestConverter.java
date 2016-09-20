package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.model.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.RequestModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 * convert ApiData to request model , and if is the latest version
 */
public class NonVersionRequestConverter {
    private VersionModel version;
    private ApiDataBean apiData;
    private OrderVersionUtil orderVersionUtil;


    public NonVersionRequestConverter(VersionModel version, ApiDataBean apiData, OrderVersionUtil orderVersionUtil) {
        this.version = version;
        this.apiData = apiData;
        this.orderVersionUtil = orderVersionUtil;
    }

    public void start() {
        RequestGroupModel requestGroup;
        try {
            requestGroup = findSameNameRequestGroup();
        } catch (NotFoundSameNameRequestGroupException ignore) {
            requestGroup = createAndAddRequestGroup();
        }

        try {
            RequestModel request = findSameNameRequest(requestGroup);
            if (needReplaceWithApiData(request)) {
                replaceRequestWithApiDataInRequestGroup(requestGroup, request);
            }
        } catch (NotFoundSameNameRequestException ignore) {
            createAndAddRequest(requestGroup);
        }
    }

    private RequestGroupModel findSameNameRequestGroup() {
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

    private RequestModel findSameNameRequest(RequestGroupModel requestGroup) {
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

    private RequestGroupModel createAndAddRequestGroup() {
        RequestGroupModel requestGroup = new RequestGroupModel(version, apiData);
        version.addRequestGroup(requestGroup);
        return requestGroup;
    }

    private void createAndAddRequest(RequestGroupModel requestGroup) {
        RequestModel request = new RequestModel(requestGroup, apiData);
        requestGroup.addRequest(request);
    }

    private boolean needReplaceWithApiData(RequestModel request) {
        return orderVersionUtil.firstVersionIsBiggerThanTheSecondVersion(apiData.getVersion(), request.getVersion());
    }

    private void replaceRequestWithApiDataInRequestGroup(RequestGroupModel requestGroup, RequestModel request) {
        List<RequestModel> requests = requestGroup.getRequests();

        int replaceIndex = requests.indexOf(request);

        RequestModel replaceRequest = new RequestModel(requestGroup, apiData);
        requests.set(replaceIndex, replaceRequest);
    }

}
