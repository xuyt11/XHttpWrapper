package cn.ytxu.test;

import cn.ytxu.apacer.config.system_platform.MacConfigDir;
import cn.ytxu.apacer.config.system_platform.OSPlatform;
import cn.ytxu.apacer.config.system_platform.Win10ConfigDir;
import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.util.LogUtil;

/**
 * Created by newchama on 16/4/18.
 */
public class RetainDataTest {

    public static void main(String... args) {
        OSPlatform os = OSPlatform.getCurrentOSPlatform();
        System.out.println(os.toString());
        switch (os) {
            case Windows: {
                test4Win10();
            }
            break;
            case MacOS:
            case MacOSX: {
                test4MacOs();
            }
            break;
            default:
                throw new RuntimeException("this is not suppot platform, current platform is " + os.toString());
        }

    }

    private static void test4Win10() {
        RetainEntity retain = RetainEntity.getRetainEntity("RetainDataTest.txt", "E:\\NewChama\\");
        LogUtil.e("import:" + retain.getImportData().toString());
        LogUtil.e("field:" + retain.getFieldData().toString());
        LogUtil.e("method:" + retain.getMethodData().toString());
        LogUtil.e("other:" + retain.getOtherData().toString());
    }

    private static void test4MacOs() {
        RetainEntity retain = RetainEntity.getRetainEntity("RetainDataTest.txt", "/Users/newchama/Desktop/NewChama-Data/");
        LogUtil.e("import:" + retain.getImportData().toString());
        LogUtil.e("field:" + retain.getFieldData().toString());
        LogUtil.e("method:" + retain.getMethodData().toString());
        LogUtil.e("other:" + retain.getOtherData().toString());
    }


}
