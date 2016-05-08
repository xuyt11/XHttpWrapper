package cn.ytxu.apacer.config;

import cn.ytxu.apacer.config.system_platform.MacConfig;
import cn.ytxu.apacer.config.system_platform.OSPlatform;
import cn.ytxu.apacer.config.system_platform.Win10Config;

/**
 * Created by Administrator on 2016/5/8.
 */
public class ConfigFactory {

    public static Config getConfig() {
        OSPlatform os = OSPlatform.getCurrentOSPlatform();
        System.out.println(os.toString());
        switch (os) {
            case Windows:
                return Win10Config.getInstance();

            case MacOS:
            case MacOSX:
                return MacConfig.getInstance();

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
