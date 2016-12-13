package cn.ytxu.http_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.model.request.RequestGroupModel;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 * convert ApiData to request model , and if is the latest version
 */
public class NonVersionRequestConverter {
    private final VersionModel version;
    private final ApiDataBean apiData;
    private final NonVersionHelper helper;


    public NonVersionRequestConverter(VersionModel version, ApiDataBean apiData, NonVersionHelper helper) {
        this.version = version;
        this.apiData = apiData;
        this.helper = helper;
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


    //******************** request group ********************
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

    private RequestGroupModel createAndAddRequestGroup() {
        return ApidocjsHelper.getApiData().createRequestGroup(version, apiData);
    }


    //******************** request ********************
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

    private boolean needReplaceWithApiData(RequestModel request) {
        return helper.firstVersionIsBiggerThanTheSecondVersion(apiData.getVersion(), request.getVersion());
    }

    private void replaceRequestWithApiDataInRequestGroup(RequestGroupModel requestGroup, RequestModel request) {
        List<RequestModel> requests = requestGroup.getRequests();

        int replaceIndex = requests.indexOf(request);

        RequestModel replaceRequest = ApidocjsHelper.getApiData().createRequest(requestGroup, apiData);
        requests.set(replaceIndex, replaceRequest);
    }

    private void createAndAddRequest(RequestGroupModel requestGroup) {
        ApidocjsHelper.getApiData().createRequest(requestGroup, apiData);
    }

}
