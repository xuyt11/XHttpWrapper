package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.model.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20.
 * convert ApiData to status code group model , and if is the latest version
 */
public class NonVersionStatusCodeConverter {
    private VersionModel version;
    private Map<String, Integer> orderVersionIndexs;
    private ApiDataBean apiData;


    public NonVersionStatusCodeConverter(VersionModel version, Map<String, Integer> orderVersionIndexs, ApiDataBean apiData) {
        this.version = version;
        this.orderVersionIndexs = orderVersionIndexs;
        this.apiData = apiData;
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
        final Integer currIndex = findVersionIndex(apiData.getVersion());
        final Integer storeIndex = findVersionIndex(scGroup.getVersion());
        return currIndex > storeIndex;
    }

    private Integer findVersionIndex(String version) {
        return orderVersionIndexs.get(version);
    }

    private void setApiData2StatusCodes() {
        StatusCodeGroupModel scGroup = new StatusCodeGroupModel(version, apiData);
        version.addStatusCodeGroup(scGroup);
    }
}
