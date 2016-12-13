package cn.ytxu.http_wrapper.config.property.base_config;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.enums.CompileModel;

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

    public String getCreateFileCharset() {
        return baseConfig.getCreateFileCharset();
    }

    public String getCompileModelName() {
        return baseConfig.getCompileModelName();
    }

    public CompileModel getCompileModelType() {
        return CompileModel.getByName(baseConfig.getCompileModelName());
    }

    public List<String> getOrderVersions() {
        return baseConfig.getOrderVersions();
    }
}
