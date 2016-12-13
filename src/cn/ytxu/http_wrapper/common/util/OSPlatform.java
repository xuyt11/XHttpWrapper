package cn.ytxu.http_wrapper.common.util;

/**
 * 操作系统平台：
 * 获取System.getProperty("os.name")对应的操作系统
 *
 * @author isea533
 * @author ytxu
 * @modify by ytxu 2016-5-8
 */
public enum OSPlatform {
    Any("any"),
    Linux("Linux"),
    MacOS("Mac OS") {
        public boolean isThisOS() {
            return CurrentOSName.indexOf("mac") >= 0 && CurrentOSName.indexOf("os") > 0 && CurrentOSName.indexOf("x") < 0;
        }
    },
    MacOSX("Mac OS X") {
        public boolean isThisOS() {
            return CurrentOSName.indexOf("mac") >= 0 && CurrentOSName.indexOf("os") > 0 && CurrentOSName.indexOf("x") > 0;
        }
    },
    Windows("Windows"),
    OS2("OS/2"),
    Solaris("Solaris"),
    SunOS("SunOS"),
    MPEiX("MPE/iX"),
    HP_UX("HP-UX"),
    AIX("AIX"),
    OS390("OS/390"),
    FreeBSD("FreeBSD"),
    Irix("Irix"),
    Digital_Unix("Digital Unix") {
        public boolean isThisOS() {
            return CurrentOSName.indexOf("digital") >= 0 && CurrentOSName.indexOf("unix") > 0;
        }
    },
    NetWare_411("NetWare"),
    OSF1("OSF1"),
    OpenVMS("OpenVMS"),
    Others("Others");

    private static final String CurrentOSName = System.getProperty("os.name").toLowerCase();
    private final String osName;

    OSPlatform(String osName) {
        this.osName = osName;
    }

    public String toString() {
        return osName;
    }

    public boolean isThisOS() {
        return CurrentOSName.indexOf(osName.toLowerCase()) >= 0;
    }

    public boolean isWin10() {
        return Windows.isThisOS() && CurrentOSName.indexOf("10") > 0;
    }

    public String getOsName() {
        return osName;
    }

    public static String getCurrentOSName() {
        return CurrentOSName;
    }

    /**
     * 获取当前操作系统
     */
    public static OSPlatform getCurrentOSPlatform() {
        for (OSPlatform osPlatform : OSPlatform.values()) {
            if (osPlatform.isThisOS()) {
                return osPlatform;
            }
        }
        return Others;
    }

}
