package cn.ytxu.apacer.config;

import cn.ytxu.apacer.config.system_platform.MacConfigDir;
import cn.ytxu.apacer.config.system_platform.OSPlatform;
import cn.ytxu.apacer.config.system_platform.Win10ConfigDir;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ConfigFactory {

    static void setConfigData() {
        Config.ConfigDir dir = getConfigDir();
        Config.setConfigDirThenCreateOtherConfig(dir);
    }

    private static Config.ConfigDir getConfigDir() {
        OSPlatform os = OSPlatform.getCurrentOSPlatform();
        System.out.println(os.toString());
        switch (os) {
            case Windows:
                return Win10ConfigDir.getInstance();
            case MacOS:
            case MacOSX:
                return MacConfigDir.getInstance();
            default:
                throw new RuntimeException("this is not suppot platform, current platform is " + os.toString());
        }
    }


    public static void main(String... args) {

        OSPlatform os = OSPlatform.getCurrentOSPlatform();
        System.out.println(os.toString());
        switch (os) {
            case Windows:
                System.out.println("is win10 platform :" + OSPlatform.Windows.isWin10());
                break;

            case MacOS:
            case MacOSX:
                break;

            default:
                throw new RuntimeException("this is not suppot platform, current platform is " + os.toString());
        }
    }

}
