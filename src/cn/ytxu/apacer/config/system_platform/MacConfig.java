package cn.ytxu.apacer.config.system_platform;

import cn.ytxu.apacer.config.Config;

/**
 * Created by Administrator on 2016/5/8.
 */
public class MacConfig extends Config {

    private static MacConfig  instance;

    private MacConfig() {
        super();
    }

    public static MacConfig getInstance() {
        if (instance == null) {
            instance = new MacConfig();
        }
        return instance;
    }

}
