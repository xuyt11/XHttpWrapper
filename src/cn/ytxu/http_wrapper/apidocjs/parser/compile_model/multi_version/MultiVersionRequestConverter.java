package cn.ytxu.http_wrapper.apidocjs.parser.compile_model.multi_version;

import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.model.request.RequestGroupModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MultiVersionRequestConverter {
    private VersionModel version;
    private ApiDataBean apiData;

    public MultiVersionRequestConverter(VersionModel version, ApiDataBean apiData) {
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
        return ApidocjsHelper.getApiData().createRequestGroup(version, apiData);
    }

    private void createRequest(RequestGroupModel requestGroup) {
        ApidocjsHelper.getApiData().createRequest(requestGroup, apiData);
    }

}
