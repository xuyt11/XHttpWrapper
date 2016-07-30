package cn.ytxu.apacer.config;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ytxu on 16/5/25.
 * 将对应的API,不再自动生成
 */
public class FilterApiVersionCodeConfig {

    private static final String VERSION_CODE_131 = "1.3.1";
    private static final String VERSION_CODE_140 = "1.4.0";
    private static final String VERSION_CODE_150 = "1.5.0";
    private static final String VERSION_CODE_200 = "2.0.0";

    public List<String> getFilterVersionCodes() {
        return Arrays.asList(VERSION_CODE_131, VERSION_CODE_140, VERSION_CODE_150);
    }


}
