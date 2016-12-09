package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.mutil_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
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
    private final List<ApiDataBean> apiDatas;
    private final LinkedHashMap<String, VersionModel> orderVersionMap;// 根据配置文件，生成顺序的版本号


    public MutilVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
        this.orderVersionMap = createOrderVersions();
    }

    /**
     * 根据配置文件，生成顺序的版本号
     */
    private LinkedHashMap<String, VersionModel> createOrderVersions() {
        List<String> orderVersions = ConfigWrapper.getBaseConfig().getOrderVersions();
        LinkedHashMap<String, VersionModel> orderVersionMap = new LinkedHashMap<>(orderVersions.size());
        for (String versionCode : orderVersions) {
            orderVersionMap.put(versionCode, new VersionModel(versionCode));
        }
        return orderVersionMap;
    }

    public List<VersionModel> start() {
        foreachParseApiDatas();
        return getVersions();
    }

    private void foreachParseApiDatas() {
        for (ApiDataBean apiData : apiDatas) {
            VersionModel version = orderVersionMap.get(apiData.getVersion());
            if (notInOrderVersions(version)) {
                continue;
            }
            if (ApidocjsHelper.getApiData().isAStatusCodeGroup(apiData)) {
                createStatusCodeGroupModel(version, apiData);
                continue;
            }
            new MutilVersionRequestConverter(version, apiData).start();
        }
    }

    private boolean notInOrderVersions(VersionModel version) {
        return Objects.isNull(version);
    }

    private void createStatusCodeGroupModel(VersionModel version, ApiDataBean apiData) {
        ApidocjsHelper.getApiData().createStatusCodeGroup(version, apiData);
    }

    private List<VersionModel> getVersions() {
        return orderVersionMap.values().stream().collect(Collectors.toList());
    }

}
