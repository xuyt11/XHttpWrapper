package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.api_semi_auto_creater.creater.Creater;
import cn.ytxu.api_semi_auto_creater.parser.Parser;
import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.util.LogUtil;

/**
 * Created by ytxu on 2016/6/16.
 */
public class NewEngine {
    // 配置文件与xtemp模板文件的前缀名称(可以有多个目标版本)
    private static final String[] XTEMP_PREFIX_NAMES = {"NewChama-android"};//, "NewChama-ios"};

    public static void main(String... args) {
        for (int i = 0; i < XTEMP_PREFIX_NAMES.length; i++) {
            long start = System.currentTimeMillis();
            final String xTempPrefixName = XTEMP_PREFIX_NAMES[i];
            Property.load(xTempPrefixName);
            DocModel docModel = new Parser().start();
            new Creater(docModel, xTempPrefixName).start();

            long end = System.currentTimeMillis();
            LogUtil.w("duration time is " + (end - start));
        }
    }
}
