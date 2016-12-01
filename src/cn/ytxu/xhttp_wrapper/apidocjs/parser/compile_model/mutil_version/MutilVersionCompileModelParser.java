package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.mutil_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.model.ModelHelper;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by ytxu on 2016/9/17.
 * version-->request group-->request
 */
public class MutilVersionCompileModelParser {
    private List<ApiDataBean> apiDatas;

    public MutilVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        LinkedHashMap<String, VersionModel> orderVersionMap = ModelHelper.getVersion().getOrderVersionMap();
        foreachParseApiDatas(orderVersionMap);
        return getVersions(orderVersionMap);
    }

    private void foreachParseApiDatas(LinkedHashMap<String, VersionModel> orderVersionMap) {
        for (ApiDataBean apiData : apiDatas) {
            VersionModel version = orderVersionMap.get(apiData.getVersion());
            if (notInOrderVersions(version)) {
                continue;
            }
            if (ApidocjsHelper.getApiData().isAStatusCodeGroup(apiData)) {
                new MutilVersionStatusCodeGroupConverter(version, apiData).start();
                continue;
            }
            new MutilVersionRequestConverter(version, apiData).start();
        }
    }

    private boolean notInOrderVersions(VersionModel version) {
        return Objects.isNull(version);
    }

    private List<VersionModel> getVersions(LinkedHashMap<String, VersionModel> orderVersionMap) {
        return orderVersionMap.values().stream().collect(Collectors.toList());
    }

}
