package cn.ytxu.xhttp_wrapper.apidocjs.parser;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeProperty;
import cn.ytxu.xhttp_wrapper.model.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.RequestModel;
import cn.ytxu.xhttp_wrapper.model.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/9/17.
 * 3.1 if is mutil_version, version-->section-->request
 */
public class MutilVersionCompileModelParser {
    private List<ApiDataBean> apiDatas;

    public MutilVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        Map<String, VersionModel> orderVersionMap = getOrderVersionMap();
        foreachParseApiDatas(orderVersionMap);
        return getVersions(orderVersionMap);
    }

    private Map<String, VersionModel> getOrderVersionMap() {
        List<String> orderVersions = Property.getConfigProperty().getOrderVersions();
        Map<String, VersionModel> orderVersionMap = new LinkedHashMap<>(orderVersions.size());
        for (String versionCode : orderVersions) {
            orderVersionMap.put(versionCode, new VersionModel(versionCode));
        }
        return orderVersionMap;
    }

    private void foreachParseApiDatas(Map<String, VersionModel> orderVersionMap) {
        for (ApiDataBean apiData : apiDatas) {
            VersionModel version = orderVersionMap.get(apiData.getVersion());
            if (notInOrderVersions4TheApiData(version)) {
                continue;
            }
            if (isAStatusCodeGroup4ApiData(apiData)) {
                setApiData2StatusCodes(version, apiData);
                continue;
            }
            setApiData2RequestGroup(version, apiData);
        }
    }

    private boolean notInOrderVersions4TheApiData(VersionModel version) {
        return Objects.isNull(version);
    }

    private boolean isAStatusCodeGroup4ApiData(ApiDataBean apiData) {
        return StatusCodeProperty.getInstance().isStatusCodeGroup(apiData.getGroup());
    }

    private void setApiData2StatusCodes(VersionModel version, ApiDataBean apiData) {
        StatusCodeGroupModel scGroup = new StatusCodeGroupModel(version, apiData, apiData.getTitle(), apiData.getName());
        version.addStatusCodeGroup(scGroup);
    }

    private void setApiData2RequestGroup(VersionModel version, ApiDataBean apiData) {
        RequestGroupModel requestGroup;
        try {
            requestGroup = findRequestGroupInVersion4TheApiData(version, apiData);
        } catch (NotFoundThisApiDataSRequestGroupInThisVersionException ignore) {
            requestGroup = new RequestGroupModel(version, apiData.getName());
            version.addRequestGroup(requestGroup);
        }
        requestGroup.addRequest(new RequestModel(requestGroup, apiData));
    }

    private RequestGroupModel findRequestGroupInVersion4TheApiData(VersionModel version, ApiDataBean apiData) {
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

    private List<VersionModel> getVersions(Map<String, VersionModel> orderVersionMap) {
        return orderVersionMap.values().stream().collect(Collectors.toList());
    }
}
