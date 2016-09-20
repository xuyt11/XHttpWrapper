package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.mutil_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.model.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.RequestModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

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
            requestGroup = findRequestGroupInVersion4TheApiData();
        } catch (NotFoundThisApiDataSRequestGroupInThisVersionException ignore) {
            requestGroup = createRequestGroup();
        }
        createRequest(requestGroup);
    }

    private RequestGroupModel findRequestGroupInVersion4TheApiData() {
        final List<RequestGroupModel> requestGroups = version.getRequestGroups();
        final String apiDataGroupName = apiData.getGroup();
        for (RequestGroupModel requestGroup : requestGroups) {
            if (requestGroup.getName().equals(apiDataGroupName)) {
                return requestGroup;
            }
        }
        throw new NotFoundThisApiDataSRequestGroupInThisVersionException();
    }

    private static final class NotFoundThisApiDataSRequestGroupInThisVersionException extends RuntimeException {
    }

    private RequestGroupModel createRequestGroup() {
        RequestGroupModel requestGroup = new RequestGroupModel(version, apiData);
        version.addRequestGroup(requestGroup);
        return requestGroup;
    }

    private void createRequest(RequestGroupModel requestGroup) {
        RequestModel request = new RequestModel(requestGroup, apiData);
        requestGroup.addRequest(request);
    }

}
