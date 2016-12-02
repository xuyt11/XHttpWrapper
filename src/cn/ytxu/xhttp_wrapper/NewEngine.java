package cn.ytxu.xhttp_wrapper;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.ApidocjsDataParser;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;
import cn.ytxu.xhttp_wrapper.xtemp.creater.Creater;
import cn.ytxu.util.LogUtil;

import java.io.IOException;
import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class NewEngine {
    // 配置文件与xtemp模板文件的前缀名称(可以有多个目标版本)
    private static final String[] XTEMP_PREFIX_NAMES = {"NewChama-android"};//, "NewChama-ios"};

    public static void main(String... args) throws IOException {
        for (int i = 0; i < XTEMP_PREFIX_NAMES.length; i++) {
            long start = System.currentTimeMillis();

            final String xTempPrefixName = XTEMP_PREFIX_NAMES[i];
            ConfigWrapper.load(xTempPrefixName);
            List<VersionModel> versions = new ApidocjsDataParser().start();
            new Creater(versions, xTempPrefixName).start();

            long end = System.currentTimeMillis();
            LogUtil.w("duration time is " + (end - start));
        }
    }
}
