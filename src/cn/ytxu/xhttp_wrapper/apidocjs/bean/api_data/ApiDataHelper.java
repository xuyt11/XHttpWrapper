package cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data;

import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;

/**
 * Created by Administrator on 2016/12/1.
 */
public class ApiDataHelper {

    private static ApiDataHelper instance;

    public static void reload() {
        instance = new ApiDataHelper();
    }

    private ApiDataHelper() {
    }

    public static ApiDataHelper getInstance() {
        return instance;
    }

    public boolean isAStatusCodeGroup(ApiDataBean apiData) {
        return ConfigWrapper.getStatusCode().isStatusCodeGroup(apiData.getGroup());
    }
}
