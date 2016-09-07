package cn.ytxu.util;

import java.util.Collection;

/**
 * Created by ytxu on 16/6/27.
 */
public class ListUtil {

    public static boolean isEmpty(Collection collection) {
        if (collection == null) {
            return true;
        }
        if (collection.size() <= 0) {
            return true;
        }
        return false;
    }

    public static int getSize(Collection eles) {
        if (null == eles) {
            return 0;
        }
        return eles.size();
    }
}
