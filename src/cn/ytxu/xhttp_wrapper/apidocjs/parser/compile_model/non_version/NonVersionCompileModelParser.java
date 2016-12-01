package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.model.ModelHelper;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/9/17.
 * request group-->request
 * and must remove old version request, just keep the latest version
 */
public class NonVersionCompileModelParser {
    private List<ApiDataBean> apiDatas;

    public NonVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        VersionModel version = ModelHelper.getVersion().getNonVersionModel();
        foreachStoreLatestVersionSApiData(version);
        return Collections.singletonList(version);
    }

    private void foreachStoreLatestVersionSApiData(VersionModel version) {
        for (ApiDataBean apiData : apiDatas) {
            if (ModelHelper.getVersion().isNotNeedParsedVersion(apiData.getVersion())) {
                continue;
            }
            if (ApidocjsHelper.getApiData().isAStatusCodeGroup(apiData)) {
                new NonVersionStatusCodeGroupConverter(version, apiData).start();
                continue;
            }
            new NonVersionRequestConverter(version, apiData).start();
        }
    }

}
