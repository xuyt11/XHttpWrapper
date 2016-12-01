package cn.ytxu.xhttp_wrapper.config.property.base_config;

import cn.ytxu.util.LogUtil;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BaseConfigWrapper {

    private static BaseConfigWrapper instance;

    private BaseConfigBean baseConfig;

    public static BaseConfigWrapper getInstance() {
        return instance;
    }

    public static void load(BaseConfigBean baseConfig) {
        LogUtil.i(BaseConfigWrapper.class, "load base config property start...");
        instance = new BaseConfigWrapper(baseConfig);
        LogUtil.i(BaseConfigWrapper.class, "load base config property success...");
    }

    private BaseConfigWrapper(BaseConfigBean baseConfig) {
        this.baseConfig = baseConfig;
        List<String> orderVersions = baseConfig.getOrderVersions();
        if (Objects.isNull(orderVersions) || orderVersions.size() < 1) {
            throw new RuntimeException("u don`t set order versions");
        }
    }

    public String getApiDataFileCharset() {
        return baseConfig.getApiDataFileCharset();
    }

    public String getCompileModelName() {
        return baseConfig.getCompileModelName();
    }

    public CompileModelType getCompileModelType() {
        return baseConfig.getCompileModelType();
    }

    public List<String> getOrderVersions() {
        return baseConfig.getOrderVersions();
    }
}
