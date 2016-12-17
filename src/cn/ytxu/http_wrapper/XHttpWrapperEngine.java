package cn.ytxu.http_wrapper;

import cn.ytxu.http_wrapper.common.enums.ApiDataSourceType;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.template_engine.XHWTFileCreater;
import cn.ytxu.http_wrapper.common.util.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/6/16.
 */
public class XHttpWrapperEngine {
    // 配置文件的路径(可以有多个)
//    private static final String[] XHWT_CONFIG_PATHS = {"xhwt/asynchttp/multi_version/x-http-wrapper.json"};
    private static final String[] XHWT_CONFIG_PATHS = {"xhwt/asynchttp/non_version/x-http-wrapper.json"};
//    private static final String[] XHWT_CONFIG_PATHS = {"xhwt/volley/non_version/x-http-wrapper.json"};
    //, "xhwt/NewChama-ios.json"};

    public static void main(String... args) throws IOException {
        final String[] xhwtConfigPaths = getXhwtConfigPaths(args);

        for (int i = 0; i < xhwtConfigPaths.length; i++) {
            long start = System.currentTimeMillis();

            run(xhwtConfigPaths[i]);

            long end = System.currentTimeMillis();
            LogUtil.w("duration time is " + (end - start));
        }
    }

    /**
     * 若没有输入参数args，则为我自己在intellij idea中使用，所以使用XHWT_CONFIG_PATHS；
     * 否则，为使用其他用户使用jar包；
     */
    private static String[] getXhwtConfigPaths(String[] args) {
        if (Objects.nonNull(args) && args.length > 0) {
            return args;
        }

        String[] paths = new String[XHWT_CONFIG_PATHS.length];
        for (int i = 0; i < XHWT_CONFIG_PATHS.length; i++) {
            String xhwtConfigPath = XHWT_CONFIG_PATHS[i];
            File configFile = new File(xhwtConfigPath);
            paths[i] = configFile.getAbsolutePath();
        }
        return paths;
    }

    private static void run(final String xhwtConfigPath) throws IOException {
        ConfigWrapper.load(xhwtConfigPath);

        String apiDataSource = ConfigWrapper.getApiDataFile().getApiDataSource();
        List<VersionModel> versions = ApiDataSourceType.get(apiDataSource).createXHWTModelByParseApiData();

        new XHWTFileCreater(versions).start();
    }
}
