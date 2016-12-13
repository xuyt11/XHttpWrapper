package cn.ytxu.http_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.http_wrapper.config.ConfigWrapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytxu on 2016/12/2.
 */
public class NonVersionHelper {

    /**
     * key: version,
     * value: version order index
     */
    private final Map<String, Integer> orderVersionIndexs;

    NonVersionHelper() {
        this.orderVersionIndexs = createOrderVersionIndexs();
    }

    private Map<String, Integer> createOrderVersionIndexs() {
        List<String> orderVersions = ConfigWrapper.getBaseConfig().getOrderVersions();
        Map<String, Integer> orderVersionIndexs = new LinkedHashMap<>(orderVersions.size());
        for (String orderVersion : orderVersions) {
            orderVersionIndexs.put(orderVersion, orderVersions.indexOf(orderVersion));
        }
        return orderVersionIndexs;
    }

    public boolean firstVersionIsBiggerThanTheSecondVersion(String firstVersion, String secondVersion) {
        final int firstIndex = findVersionIndex(firstVersion);
        final int secondIndex = findVersionIndex(secondVersion);
        return firstIndex > secondIndex;
    }

    private int findVersionIndex(String version) {
        return orderVersionIndexs.get(version);
    }

    public boolean isNotNeedParsedVersion(String versionName) {
        return !orderVersionIndexs.containsKey(versionName);
    }
}
