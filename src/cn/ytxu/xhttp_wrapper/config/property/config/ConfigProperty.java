package cn.ytxu.xhttp_wrapper.config.property.config;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class ConfigProperty {

    private static ConfigProperty instance;

    private ConfigBean config;


    public static ConfigProperty getInstance() {
        return instance;
    }

    public static void load(ConfigBean config) {
        instance = new ConfigProperty();
        instance.config = config;
    }

    private ConfigProperty() {
    }

    public String getAutoGenerateFileCharset() {
        return config.getAuto_generate_file_charset();
    }

    public String getCompileModelName() {
        return config.getCompile_model();
    }

    public List<String> getOrderVersions() {
        return config.getOrder_versions();
    }
}
