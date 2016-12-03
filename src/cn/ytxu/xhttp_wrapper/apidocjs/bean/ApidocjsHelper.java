package cn.ytxu.xhttp_wrapper.apidocjs.bean;

import cn.ytxu.util.FileUtil;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataHelper;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

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

    public static List<ApiDataBean> getApiDatasFromFile() throws IOException {
        // 1 get api_data.json path
        String apiDataPath = ConfigWrapper.getApiDataFile().getApiDataFilePath();
        // 2 get json data from file
        String apiDataJsonStr = FileUtil.getContent(apiDataPath);
        // 3 get java object array by json data
        return JSON.parseArray(apiDataJsonStr, ApiDataBean.class);
    }
}
