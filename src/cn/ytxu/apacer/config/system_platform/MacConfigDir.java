package cn.ytxu.apacer.config.system_platform;

import cn.ytxu.apacer.config.Config;

/**
 * Created by Administrator on 2016/5/8.
 */
public class MacConfigDir implements Config.ConfigDir {
    private static final String INPUT_FILE_DIR = "/Users/newchama/Desktop/NewChama-Data/";
    private static final String AUTO_CREATE_DIR = "/Users/newchama/Documents/ytxu/newchama_android/NewChama/";
    private static final String RESPONSE_ENTITY_FILE_DIR = AUTO_CREATE_DIR + "newchama.model/src/main/java/com/newchama/api/";
    private static final String REQUEST_FILE_DIR = AUTO_CREATE_DIR + "newchama.common/src/main/java/com/newchama/api/";

    private static MacConfigDir instance;

    private MacConfigDir() {
    }

    public static MacConfigDir getInstance() {
        if (instance == null) {
            instance = new MacConfigDir();
        }
        return instance;
    }

    @Override
    public String getInputDir() {
        return INPUT_FILE_DIR;
    }

    @Override
    public String getAutoCreaterDir() {
        return AUTO_CREATE_DIR;
    }

    @Override
    public String getRequestDir() {
        return REQUEST_FILE_DIR;
    }

    @Override
    public String getResponseEntityDir() {
        return RESPONSE_ENTITY_FILE_DIR;
    }
}
