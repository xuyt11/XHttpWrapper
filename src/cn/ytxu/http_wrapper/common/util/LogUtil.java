package cn.ytxu.http_wrapper.common.util;

public class LogUtil {

    private static final int printAll = -1;
    private static final int i = 0;
    private static final int w = 1;
    private static final int e = 2;

    private static final int level = printAll;

	public static void i(String tag, String message) {
		System.out.println(tag + ":" + message);
	}

	public static void i(Class<?> cls, String message) {
		System.out.println(cls.getName() + ":" + message);
	}

	public static void i(int index, String message) {
		System.out.println(index + ":" + message);
	}

	public static void i(String message) {
        if (level < i) {
            print(message);
        }
	}

	/** 警告:查看提示信息 */
	public static void w(String message) {
        if (level < w) {
            print("warn:" + message);
        }
	}

    /** 错误与异常:查看提示信息 */
    public static void e(String message) {
        if (level < e) {
            print("error:" + message);
        }
    }

    public static void ee(Class<?> cls, String... msgs) {
        if (level < e) {
            StringBuffer msgBuffer = new StringBuffer(cls.getSimpleName());
            msgBuffer.append(" : ");
            for (String msg : msgs) {
                msgBuffer.append(msg);
            }
            e(msgBuffer.toString());
        }
    }

    public static void ee(Class<?> cls, String[] titles, String... msgs) {
        if (level < e) {
            StringBuffer msgBuffer = new StringBuffer(cls.getSimpleName());
            msgBuffer.append(" : ");
            for (String title : titles) {
                msgBuffer.append(title);
            }
            for (String msg : msgs) {
                msgBuffer.append(msg);
            }
            e(msgBuffer.toString());
        }
    }

    private static void print(String message) {
        System.out.println(message);
    }

}
