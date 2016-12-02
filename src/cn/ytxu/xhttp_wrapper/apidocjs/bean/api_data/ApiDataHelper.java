package cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data;

import cn.ytxu.util.FileUtil;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

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

    public List<ApiDataBean> getApiDatasFromFile() throws IOException {
        // 1 get api_data.json path
        String apiDataPath = ConfigWrapper.getApiDataFile().getApiDataFilePath();
        // 2 get json data from file
        String apiDataJsonStr = FileUtil.getContent(apiDataPath);
        // 3 get java object array by json data
        return JSON.parseArray(apiDataJsonStr, ApiDataBean.class);
    }

    public boolean isAStatusCodeGroup(ApiDataBean apiData) {
        return ConfigWrapper.getStatusCode().isStatusCodeGroup(apiData.getGroup());
    }
}
