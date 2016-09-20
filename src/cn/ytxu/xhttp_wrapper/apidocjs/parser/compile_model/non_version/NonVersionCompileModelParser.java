package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeProperty;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/17.
 * 3.2 else is non_version, section-->request, and must remove old version request, just keep the latest version
 */
public class NonVersionCompileModelParser {
    private List<ApiDataBean> apiDatas;
    private OrderVersionUtil orderVersionUtil;

    public NonVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        orderVersionUtil = new OrderVersionUtil();
        VersionModel version = VersionModel.NON_VERSION_MODEL;
        foreachStoreLatestVersionSApiData(version);
        return Collections.singletonList(version);
    }

    private void foreachStoreLatestVersionSApiData(VersionModel version) {
        for (ApiDataBean apiData : apiDatas) {
            if (isNotNeedParsedVersion(apiData)) {
                continue;
            }
            if (isAStatusCodeGroup4ApiData(apiData)) {
                new NonVersionStatusCodeGroupConverter(version, apiData, orderVersionUtil).start();
                continue;
            }
            new NonVersionRequestConverter(version, apiData, orderVersionUtil).start();
        }
    }

    private boolean isNotNeedParsedVersion(ApiDataBean apiData) {
        final Integer index = orderVersionUtil.findVersionIndex(apiData.getVersion());
        return Objects.isNull(index);
    }

    private boolean isAStatusCodeGroup4ApiData(ApiDataBean apiData) {
        return StatusCodeProperty.getInstance().isStatusCodeGroup(apiData.getGroup());
    }

}
