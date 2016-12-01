package cn.ytxu.xhttp_wrapper.model.version.helper;

import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.version.VersionModel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytxu on 2016/12/2.
 */
public class NonVersionHelper {
    /**
     * 无版本号模式时的model
     */
    private final VersionModel NON_VERSION_MODEL = new VersionModel("non_version");
    private final Map<String, Integer> orderVersionIndexs = createOrderVersionIndexs();

    NonVersionHelper() {
    }

    public VersionModel getNonVersionModel() {
        return NON_VERSION_MODEL;
    }

    private Map<String, Integer> createOrderVersionIndexs() {
        List<String> orderVersions = ConfigWrapper.getBaseConfig().getOrderVersions();
        Map<String, Integer> orderVersionIndexs = new LinkedHashMap<>(orderVersions.size());
        for (String orderVersion : orderVersions) {
            orderVersionIndexs.put(orderVersion, orderVersions.indexOf(orderVersion));
        }
        return orderVersionIndexs;
    }

    Integer findVersionIndex(String version) {
        return orderVersionIndexs.get(version);
    }

    boolean firstVersionIsBiggerThanTheSecondVersion(String firstVersion, String secondVersion) {
        final Integer firstIndex = findVersionIndex(firstVersion);
        final Integer secondIndex = findVersionIndex(secondVersion);
        return firstIndex > secondIndex;
    }

    public boolean isNotNeedParsedVersion(String versionName) {
        return orderVersionIndexs.containsKey(versionName);
    }
}
