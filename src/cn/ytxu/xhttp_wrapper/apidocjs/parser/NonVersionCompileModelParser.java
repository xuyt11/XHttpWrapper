package cn.ytxu.xhttp_wrapper.apidocjs.parser;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeProperty;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.*;


/**
 * Created by Administrator on 2016/9/17.
 * 3.2 else is non_version, section-->request, and must remove old version request, just keep the latest version
 */
public class NonVersionCompileModelParser {
    private List<ApiDataBean> apiDatas;

    public NonVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        Map<String, Integer> orderVersionIndexs = getOrderVersionIndexs();
        VersionModel version = VersionModel.NON_VERSION_MODEL;
        foreachStoreLatestVersionSApiData(version, orderVersionIndexs);
        return Collections.singletonList(version);
    }

    /**
     * 获取版本的顺序，为后面的解析提供版本顺序，因为non_version模式需要过滤掉老版本request
     */
    private Map<String, Integer> getOrderVersionIndexs() {
        List<String> orderVersions = Property.getConfigProperty().getOrderVersions();
        Map<String, Integer> orderVersionIndexs = new LinkedHashMap<>(orderVersions.size());
        for (String orderVersion : orderVersions) {
            orderVersionIndexs.put(orderVersion, orderVersions.indexOf(orderVersion));
        }
        return orderVersionIndexs;
    }

    private void foreachStoreLatestVersionSApiData(VersionModel version, Map<String, Integer> orderVersionIndexs) {
        for (ApiDataBean apiData : apiDatas) {
            final Integer index = orderVersionIndexs.get(apiData.getVersion());
            if (isNotNeedParsedVersion(index)) {
                continue;
            }
            if (isAStatusCodeGroup4ApiData(apiData)) {
                setApiData2StatusCodesIfIsLatestVersion(version, apiData);
                continue;
            }
            setApiData2RequestGroupIfIsLatestVersion(version, apiData);
        }
    }

    private boolean isNotNeedParsedVersion(Integer index) {
        return Objects.isNull(index);
    }

    private boolean isAStatusCodeGroup4ApiData(ApiDataBean apiData) {
        return StatusCodeProperty.getInstance().isStatusCodeGroup(apiData.getGroup());
    }

    private void setApiData2StatusCodesIfIsLatestVersion(VersionModel version, ApiDataBean apiData) {


    }


    private void setApiData2RequestGroupIfIsLatestVersion(VersionModel version, ApiDataBean apiData) {
    }

}
