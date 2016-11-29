package cn.ytxu.xhttp_wrapper.config.property.api_data_file;

import cn.ytxu.util.LogUtil;
import cn.ytxu.util.OSPlatform;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/9/2.
 */
public class ApiDataFileWrapper {

    private List<ApiDataFileBean> apiDataFiles;

    private static ApiDataFileWrapper instance;

    public static ApiDataFileWrapper getInstance() {
        return instance;
    }

    public static void load(List<ApiDataFileBean> apiDataFiles) {
        LogUtil.i(ApiDataFileWrapper.class, "load api data file property start...");
        instance = new ApiDataFileWrapper(apiDataFiles);
        LogUtil.i(ApiDataFileWrapper.class, "load api data file property success...");
    }

    private ApiDataFileWrapper(List<ApiDataFileBean> apiDataFiles) {
        this.apiDataFiles = apiDataFiles;
        if (Objects.isNull(apiDataFiles) || apiDataFiles.size() < 1) {
            throw new RuntimeException("u don`t set api_data_file path");
        }
    }

    public String getApiDataFilePath() {
        final String osName = OSPlatform.getCurrentOSPlatform().getOsName();
        for (ApiDataFileBean apiDataFile : apiDataFiles) {
            if (osName.equalsIgnoreCase(apiDataFile.getOSName())) {
                return apiDataFile.getAddress();
            }
        }
        throw new IllegalArgumentException("not found the match api_data_file path," +
                " and the current os name is " + osName);
    }
}
