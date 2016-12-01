package cn.ytxu.xhttp_wrapper.apidocjs.bean;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataHelper;

/**
 * Created by Administrator on 2016/12/2.
 */
public class ApidocjsHelper {

    public static void reload() {
        ApiDataHelper.reload();
    }

    public static ApiDataHelper getApiData() {
        return ApiDataHelper.getInstance();
    }
}
