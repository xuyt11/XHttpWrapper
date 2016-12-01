package cn.ytxu.xhttp_wrapper.model;

import cn.ytxu.xhttp_wrapper.model.version.helper.VersionHelper;

/**
 * Created by ytxu on 2016/12/2.
 */
public class ModelHelper {

    public static void reload() {
        VersionHelper.reload();
    }

    public static VersionHelper getVersion() {
        return VersionHelper.getInstance();
    }

}
