package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.mutil_version;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

/**
 * Created by Administrator on 2016/9/20.
 */
public class MutilVersionStatusCodeGroupConverter {
    private VersionModel version;
    private ApiDataBean apiData;

    public MutilVersionStatusCodeGroupConverter(VersionModel version, ApiDataBean apiData) {
        this.version = version;
        this.apiData = apiData;
    }

    public void start() {
        StatusCodeGroupModel scGroup = new StatusCodeGroupModel(version, apiData);
        version.addStatusCodeGroup(scGroup);
    }
}
