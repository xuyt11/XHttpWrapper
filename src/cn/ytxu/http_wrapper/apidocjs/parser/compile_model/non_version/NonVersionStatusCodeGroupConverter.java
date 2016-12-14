package cn.ytxu.http_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 * convert ApiData to status code group model , and if is the latest version
 */
public class NonVersionStatusCodeGroupConverter {
    private final VersionModel version;
    private final ApiDataBean apiData;
    private final NonVersionHelper helper;


    public NonVersionStatusCodeGroupConverter(VersionModel version, ApiDataBean apiData, NonVersionHelper helper) {
        this.version = version;
        this.apiData = apiData;
        this.helper = helper;
    }

    public void start() {
        try {
            StatusCodeGroupModel scGroup = findSameNameSCGroup();
            if (needStoreTheStatusCodeTypeSApiData(scGroup)) {
                setApiData2StatusCodes();
            }
        } catch (NotFoundSameNameStatusCodeGroupException ignore) {
            setApiData2StatusCodes();
        }
    }

    private StatusCodeGroupModel findSameNameSCGroup() {
        List<StatusCodeGroupModel> scGroups = version.getStatusCodeGroups();
        for (StatusCodeGroupModel scGroup : scGroups) {
            if (scGroup.getName().equals(apiData.getName())) {
                return scGroup;
            }
        }
        throw new NotFoundSameNameStatusCodeGroupException();
    }

    private static final class NotFoundSameNameStatusCodeGroupException extends RuntimeException {
    }

    private boolean needStoreTheStatusCodeTypeSApiData(StatusCodeGroupModel scGroup) {
        return helper.firstVersionIsBiggerThanTheSecondVersion(apiData.getVersion(), scGroup.getVersion());
    }

    private void setApiData2StatusCodes() {
        ApidocjsHelper.getApiData().createStatusCodeGroup(version, apiData);
    }
}
