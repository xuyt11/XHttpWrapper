package cn.ytxu.xhttp_wrapper;

import cn.ytxu.xhttp_wrapper.common.enums.ApiDataSourceType;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;
import cn.ytxu.xhttp_wrapper.xhwt_engine.XHWTFileCreater;
import cn.ytxu.util.LogUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class NewEngine {
    // 配置文件的路径(可以有多个)
    private static final String[] XHWT_CONFIG_PATHS = {"./xhwt/newchama/NewChama-android.json"};//, "./xhwt/NewChama-ios.json"};

    public static void main(String... args) throws IOException {
        for (int i = 0; i < XHWT_CONFIG_PATHS.length; i++) {
            long start = System.currentTimeMillis();

            final String xhwtConfigPath = XHWT_CONFIG_PATHS[i];
            ConfigWrapper.load(xhwtConfigPath);

            String apiDataSource = ConfigWrapper.getApiDataFile().getApiDataSource();
            List<VersionModel> versions = ApiDataSourceType.get(apiDataSource).createXHWTModelByParseApiData();

            new XHWTFileCreater(versions, xhwtConfigPath).start();

            long end = System.currentTimeMillis();
            LogUtil.w("duration time is " + (end - start));
        }
    }
}
