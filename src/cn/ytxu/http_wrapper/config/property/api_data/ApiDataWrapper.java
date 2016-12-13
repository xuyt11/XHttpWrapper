package cn.ytxu.http_wrapper.config.property.api_data;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.util.OSPlatform;
import cn.ytxu.http_wrapper.common.enums.ApiDataSourceType;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/9/2.
 */
public class ApiDataWrapper {

    private ApiDataBean apiDataBean;

    private static ApiDataWrapper instance;

    public static ApiDataWrapper getInstance() {
        return instance;
    }

    public static void load(ApiDataBean apiDataFileBean) {
        if (Objects.isNull(apiDataFileBean)) {
            throw new RuntimeException("u don`t setup api_data property");
        }
        LogUtil.i(ApiDataWrapper.class, "load api data file property start...");
        instance = new ApiDataWrapper(apiDataFileBean);
        LogUtil.i(ApiDataWrapper.class, "load api data file property success...");
    }

    private ApiDataWrapper(ApiDataBean apiDataFileBean) {
        this.apiDataBean = apiDataFileBean;
        judgeApiDataSource();
        getApiDataFilePath();
    }

    private void judgeApiDataSource() {
        String apiDataSource = apiDataBean.getApiDataSource();
        if (Objects.isNull(apiDataSource)) {
            throw new RuntimeException("u don`t setup api_data_source...");
        }
        ApiDataSourceType.get(apiDataSource);
    }

    public String getApiDataSource() {
        return apiDataBean.getApiDataSource();
    }

    public String getApiDataFilePath() {
        List<ApiDataFilePathInfoBean> pathInfos = apiDataBean.getFilePathInfos();
        if (pathInfos.isEmpty()) {
            throw new RuntimeException("u don`t setup file_path_infos");
        }

        final String osName = OSPlatform.getCurrentOSPlatform().getOsName();
        for (ApiDataFilePathInfoBean pathInfo : pathInfos) {
            if (osName.equalsIgnoreCase(pathInfo.getOSName())) {
                return pathInfo.getAddress();
            }
        }
        throw new IllegalArgumentException("not found the match file_path_info," +
                " and the current os name is " + osName);
    }

    public String getFileCharset() {
        return apiDataBean.getFileCharset();
    }
}
