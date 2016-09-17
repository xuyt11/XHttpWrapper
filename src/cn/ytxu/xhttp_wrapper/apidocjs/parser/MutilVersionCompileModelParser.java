package cn.ytxu.xhttp_wrapper.apidocjs.parser;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/9/17.
 * 3.1 if is mutil_version, version-->section-->request
 */
public class MutilVersionCompileModelParser {
    private List<ApiDataBean> apiDatas;

    public MutilVersionCompileModelParser(List<ApiDataBean> apiDatas) {
        this.apiDatas = apiDatas;
    }

    public List<VersionModel> start() {
        Map<String, VersionModel> orderVersionMap = getOrderVersionMap();
        // TODO foreach parse api datas
        return getVersions(orderVersionMap);
    }

    private Map<String, VersionModel> getOrderVersionMap() {
        List<String> orderVersions = Property.getConfigProperty().getOrderVersions();
        Map<String, VersionModel> orderVersionMap = new HashMap<>(orderVersions.size());
        for (String orderVersion : orderVersions) {
            orderVersionMap.put(orderVersion, new VersionModel(orderVersion));
        }
        return orderVersionMap;
    }

    private List<VersionModel> getVersions(Map<String, VersionModel> orderVersionMap) {
        return orderVersionMap.values().stream().collect(Collectors.toList());
    }
}
