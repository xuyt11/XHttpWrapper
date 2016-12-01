package cn.ytxu.xhttp_wrapper.model.version.helper;

import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

import java.util.LinkedHashMap;

/**
 * Created by ytxu on 2016/12/1.
 */
public class VersionHelper {

    private final NonVersionHelper nonHelper = new NonVersionHelper();
    private final MutilVersionHelper mutilHelper = new MutilVersionHelper();

    private static VersionHelper instance;

    public static void reload() {
        instance = new VersionHelper();
    }

    private VersionHelper() {
    }

    public static VersionHelper getInstance() {
        return instance;
    }


    //********************** non version **********************

    public VersionModel getNonVersionModel() {
        return nonHelper.getNonVersionModel();
    }

    public Integer findVersionIndex(String version) {
        return nonHelper.findVersionIndex(version);
    }

    public boolean isNotNeedParsedVersion(String versionName) {
        return nonHelper.isNotNeedParsedVersion(versionName);
    }

    public boolean firstVersionIsBiggerThanTheSecondVersion(String firstVersion, String secondVersion) {
        return nonHelper.firstVersionIsBiggerThanTheSecondVersion(firstVersion, secondVersion);
    }


    //********************** mutil version **********************

    public LinkedHashMap<String, VersionModel> getOrderVersionMap() {
        return mutilHelper.getOrderVersionMap();
    }
}
