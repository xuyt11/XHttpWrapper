package cn.ytxu.xhttp_wrapper.apidocjs.parser.compile_model.non_version;

import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/20.
 * 获取版本的顺序，为后面的解析提供版本顺序，因为non_version模式需要过滤掉老版本request
 */
public class OrderVersionUtil {
    private Map<String, Integer> orderVersionIndexs;

    public OrderVersionUtil() {
        generateOrderVersionIndexs();
    }

    private void generateOrderVersionIndexs() {
        List<String> orderVersions = ConfigWrapper.getBaseConfig().getOrderVersions();
        orderVersionIndexs = new LinkedHashMap<>(orderVersions.size());
        for (String orderVersion : orderVersions) {
            orderVersionIndexs.put(orderVersion, orderVersions.indexOf(orderVersion));
        }
    }

    public Integer findVersionIndex(String version) {
        return orderVersionIndexs.get(version);
    }

    public boolean firstVersionIsBiggerThanTheSecondVersion(String firstVersion, String secondVersion) {
        final Integer firstIndex = findVersionIndex(firstVersion);
        final Integer secondIndex = findVersionIndex(secondVersion);
        return firstIndex > secondIndex;
    }

}
