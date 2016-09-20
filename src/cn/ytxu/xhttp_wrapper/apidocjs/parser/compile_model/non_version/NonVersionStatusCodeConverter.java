package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.model.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 * convert ApiData to status code group model , and if is the latest version
 */
public class NonVersionStatusCodeConverter {
    private VersionModel version;
    private ApiDataBean apiData;
    private OrderVersionUtil orderVersionUtil;


    public NonVersionStatusCodeConverter(VersionModel version, ApiDataBean apiData, OrderVersionUtil orderVersionUtil) {
        this.version = version;
        this.apiData = apiData;
        this.orderVersionUtil = orderVersionUtil;
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
        return orderVersionUtil.firstVersionIsBiggerThanTheSecondVersion(apiData.getVersion(), scGroup.getVersion());
    }

    private void setApiData2StatusCodes() {
        StatusCodeGroupModel scGroup = new StatusCodeGroupModel(version, apiData);
        version.addStatusCodeGroup(scGroup);
    }
}
