package cn.ytxu.apacer.config.system_platform;

import cn.ytxu.apacer.config.Config;

/**
 * Created by Administrator on 2016/5/8.
 */
public class Win10ConfigDir implements Config.ConfigDir {
    private static final String INPUT_FILE_DIR = "E:\\NewChama\\";
    // test
    private static final String TEST_AUTO_CREATE_DIR = "E:\\NewChama\\autoCreaterDir\\";
    private static final String TEST_RESPONSE_ENTITY_FILE_DIR = TEST_AUTO_CREATE_DIR + "model/";
    private static final String TEST_REQUEST_FILE_DIR = TEST_AUTO_CREATE_DIR + "common/";
    // target
    private static final String TARGET_AUTO_CREATE_DIR = "I:\\NewChamaStudio\\newchama_android\\NewChama\\";
    private static final String TARGET_RESPONSE_ENTITY_FILE_DIR = TARGET_AUTO_CREATE_DIR + "newchama.model/src/main/java/com/newchama/api/";
    private static final String TARGET_REQUEST_FILE_DIR = TARGET_AUTO_CREATE_DIR + "newchama.common/src/main/java/com/newchama/api/";
    // if false, output to test dir, otherwise to target dir
    private static final boolean isTarget = true;

    private enum dir {
        test(TEST_AUTO_CREATE_DIR, TEST_RESPONSE_ENTITY_FILE_DIR, TEST_REQUEST_FILE_DIR),
        target(TARGET_AUTO_CREATE_DIR, TARGET_RESPONSE_ENTITY_FILE_DIR, TARGET_REQUEST_FILE_DIR);

        private final String autoCreateDir;
        private final String requestDir;
        private final String responseEntityDir;

        dir(String autoCreateDir, String requestDir, String responseEntityDir) {
            this.autoCreateDir = autoCreateDir;
            this.requestDir = requestDir;
            this.responseEntityDir = responseEntityDir;
        }

        public String getAutoCreateDir() {
            return autoCreateDir;
        }

        public String getRequestDir() {
            return requestDir;
        }

        public String getResponseEntityDir() {
            return responseEntityDir;
        }
    }

    private static Win10ConfigDir instance;
    private Win10ConfigDir() {}
    public static Win10ConfigDir getInstance() {
        if (instance == null) {
            instance = new Win10ConfigDir();
        }
        return instance;
    }

    @Override
    public String getInputDir() {
        return INPUT_FILE_DIR;
    }

    @Override
    public String getAutoCreaterDir() {
        return isTarget ? dir.target.getAutoCreateDir() : dir.test.getAutoCreateDir();
    }

    @Override
    public String getRequestDir() {
        return isTarget ? dir.target.getRequestDir() : dir.test.getRequestDir();
    }

    @Override
    public String getResponseEntityDir() {
        return isTarget ? dir.target.getResponseEntityDir() : dir.test.getResponseEntityDir();
    }
}
