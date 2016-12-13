package cn.ytxu.xhttp_wrapper;

import cn.ytxu.xhttp_wrapper.common.enums.ApiDataSourceType;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;
import cn.ytxu.xhttp_wrapper.xhwt_engine.XHWTFileCreater;
import cn.ytxu.util.LogUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/6/16.
 */
public class NewEngine {
    // 配置文件的路径(可以有多个)
    private static final String[] XHWT_CONFIG_PATHS = {"./xhwt/ncm_non_version/NewChama-android.json"};
    //, "./xhwt/NewChama-ios.json"};
//    ./xhwt/newchama/NewChama-android.json

    public static void main(String... args) throws IOException {
        final String[] xhwtConfigPaths = getXhwtConfigPaths(args);

        for (int i = 0; i < xhwtConfigPaths.length; i++) {
            long start = System.currentTimeMillis();

            final String xhwtConfigPath = xhwtConfigPaths[i];
            ConfigWrapper.load(xhwtConfigPath);

            String apiDataSource = ConfigWrapper.getApiDataFile().getApiDataSource();
            List<VersionModel> versions = ApiDataSourceType.get(apiDataSource).createXHWTModelByParseApiData();

            new XHWTFileCreater(versions, xhwtConfigPath).start();

            long end = System.currentTimeMillis();
            LogUtil.w("duration time is " + (end - start));
        }
    }

    /**
     * 若没有输入参数args，则为我自己在intellij idea中使用，所以使用XHWT_CONFIG_PATHS；
     * 否则，为使用其他用户使用jar包；
     * @param args
     * @return
     */
    private static String[] getXhwtConfigPaths(String[] args) {
        if (Objects.isNull(args) || args.length <= 0) {
            return XHWT_CONFIG_PATHS;
        }
        return args;
    }
}
