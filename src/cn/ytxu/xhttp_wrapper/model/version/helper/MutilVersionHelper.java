package cn.ytxu.xhttp_wrapper.model.version.helper;

import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */
public class MutilVersionHelper {
    private final LinkedHashMap<String, VersionModel> orderVersionMap = createOrderVersions();

    MutilVersionHelper() {
    }

    /**
     * 根据配置文件，生成顺序的版本号
     */
    private LinkedHashMap<String, VersionModel> createOrderVersions() {
        List<String> orderVersions = ConfigWrapper.getBaseConfig().getOrderVersions();
        LinkedHashMap<String, VersionModel> orderVersionMap = new LinkedHashMap<>(orderVersions.size());
        for (String versionCode : orderVersions) {
            orderVersionMap.put(versionCode, new VersionModel(versionCode));
        }
        return orderVersionMap;
    }

    LinkedHashMap<String, VersionModel> getOrderVersionMap() {
        return orderVersionMap;
    }
}
