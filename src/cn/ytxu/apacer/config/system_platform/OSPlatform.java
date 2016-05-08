package cn.ytxu.apacer.config.system_platform;

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

    private final String description;

    OSPlatform(String desc) {
        this.description = desc;
    }

    public String toString() {
        return description;
    }

    public boolean isThisOS() {
        return CurrentOSName.indexOf(description.toLowerCase()) >= 0;
    }

    public boolean isWin10() {
        return Windows.isThisOS() && CurrentOSName.indexOf("10") > 0;
    }

    public static String getCurrentOSName() {
        return CurrentOSName;
    }

    /**
     * 获取当前操作系统
     */
    public static OSPlatform getCurrentOSPlatform() {
        OSPlatform platform;
        if (Linux.isThisOS()) {
            platform = OSPlatform.Linux;
        } else if (MacOS.isThisOS()) {
            platform = OSPlatform.MacOS;
        } else if (MacOSX.isThisOS()) {
            platform = OSPlatform.MacOSX;
        } else if (Windows.isThisOS()) {
            platform = OSPlatform.Windows;
        } else if (AIX.isThisOS()) {
            platform = OSPlatform.AIX;
        } else if (Digital_Unix.isThisOS()) {
            platform = OSPlatform.Digital_Unix;
        } else if (FreeBSD.isThisOS()) {
            platform = OSPlatform.FreeBSD;
        } else if (HP_UX.isThisOS()) {
            platform = OSPlatform.HP_UX;
        } else if (Irix.isThisOS()) {
            platform = OSPlatform.Irix;
        } else if (MPEiX.isThisOS()) {
            platform = OSPlatform.MPEiX;
        } else if (NetWare_411.isThisOS()) {
            platform = OSPlatform.NetWare_411;
        } else if (OpenVMS.isThisOS()) {
            platform = OSPlatform.OpenVMS;
        } else if (OS2.isThisOS()) {
            platform = OSPlatform.OS2;
        } else if (OS390.isThisOS()) {
            platform = OSPlatform.OS390;
        } else if (OSF1.isThisOS()) {
            platform = OSPlatform.OSF1;
        } else if (Solaris.isThisOS()) {
            platform = OSPlatform.Solaris;
        } else if (SunOS.isThisOS()) {
            platform = OSPlatform.SunOS;
        } else {
            platform = OSPlatform.Others;
        }
        return platform;
    }

}
