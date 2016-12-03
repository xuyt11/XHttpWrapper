package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.mutil_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MutilVersionRequestConverter {
    private VersionModel version;
    private ApiDataBean apiData;

    public MutilVersionRequestConverter(VersionModel version, ApiDataBean apiData) {
        this.version = version;
        this.apiData = apiData;
    }

    public void start() {
        RequestGroupModel requestGroup;
        try {
            requestGroup = findRequestGroup4TheApiData();
        } catch (NotFoundThisApiDataSRequestGroupInThisVersionException ignore) {
            requestGroup = createAndAddRequestGroup();
        }
        createRequest(requestGroup);
    }

    private RequestGroupModel findRequestGroup4TheApiData() {
        final List<RequestGroupModel> requestGroups = version.getRequestGroups();
        final String apiDataGroupName = apiData.getGroup();
        for (RequestGroupModel requestGroup : requestGroups) {
            if (findRequestGroup(apiDataGroupName, requestGroup)) {
                return requestGroup;
            }
        }
        throw new NotFoundThisApiDataSRequestGroupInThisVersionException();
    }

    private boolean findRequestGroup(String apiDataGroupName, RequestGroupModel requestGroup) {
        return requestGroup.getName().equals(apiDataGroupName);
    }

    private static final class NotFoundThisApiDataSRequestGroupInThisVersionException extends RuntimeException {
    }

    private RequestGroupModel createAndAddRequestGroup() {
        RequestGroupModel requestGroup = new RequestGroupModel(version, apiData.getGroup());
        version.addRequestGroup(requestGroup);
        return requestGroup;
    }

    private void createRequest(RequestGroupModel requestGroup) {
        RequestModel request = new RequestModel(requestGroup, apiData);
        requestGroup.addRequest(request);
    }

}
