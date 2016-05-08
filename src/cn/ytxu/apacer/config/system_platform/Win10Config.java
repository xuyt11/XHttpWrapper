package cn.ytxu.apacer.config.system_platform;

import cn.ytxu.apacer.config.Config;

/**
 * Created by Administrator on 2016/5/8.
 */
public class Win10Config extends Config {

    private static Win10Config  instance;

    private Win10Config() {
        super();
    }

    public static Win10Config getInstance() {
        if (instance == null) {
            instance = new Win10Config();
        }
        return instance;
    }

}
