package cn.ytxu.http_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.common.enums.CompileModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/9/17.
 * request group-->request
 * and must remove old version request, just keep the latest version
 */
public class NonVersionCompileModelParser {
    private final List<ApiDataBean> apiDatas;
    /**
     * 无版本号模式时的model
     */
    private final VersionModel NON_VERSION_MODEL = new VersionModel(CompileModel.non_version.name());
    private final NonVersionHelper helper = new NonVersionHelper();

    public NonVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        foreachStoreLatestVersionSApiData();
        return Collections.singletonList(NON_VERSION_MODEL);
    }

    private void foreachStoreLatestVersionSApiData() {
        for (ApiDataBean apiData : apiDatas) {
            if (helper.isNotNeedParsedVersion(apiData.getVersion())) {
                continue;
            }
            if (ApidocjsHelper.getApiData().isAStatusCodeGroup(apiData)) {
                new NonVersionStatusCodeGroupConverter(NON_VERSION_MODEL, apiData, helper).start();
                continue;
            }
            new NonVersionRequestConverter(NON_VERSION_MODEL, apiData, helper).start();
        }
    }

}
