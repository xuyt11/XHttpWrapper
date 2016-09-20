package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.non_version;

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
    private Map<String, Integer> orderVersionIndexs;

    public NonVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        orderVersionIndexs = getOrderVersionIndexs();
        VersionModel version = VersionModel.NON_VERSION_MODEL;
        foreachStoreLatestVersionSApiData(version);
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

    private void foreachStoreLatestVersionSApiData(VersionModel version) {
        for (ApiDataBean apiData : apiDatas) {
            if (isNotNeedParsedVersion(apiData)) {
                continue;
            }
            if (isAStatusCodeGroup4ApiData(apiData)) {
                new NonVersionStatusCodeConverter(version, orderVersionIndexs, apiData).start();
                continue;
            }
            new NonVersionRequestConverter(version, orderVersionIndexs, apiData).start();
        }
    }

    private boolean isNotNeedParsedVersion(ApiDataBean apiData) {
        final Integer index = findVersionIndex(apiData.getVersion());
        return Objects.isNull(index);
    }

    private Integer findVersionIndex(String version) {
        return orderVersionIndexs.get(version);
    }

    private boolean isAStatusCodeGroup4ApiData(ApiDataBean apiData) {
        return StatusCodeProperty.getInstance().isStatusCodeGroup(apiData.getGroup());
    }


}
