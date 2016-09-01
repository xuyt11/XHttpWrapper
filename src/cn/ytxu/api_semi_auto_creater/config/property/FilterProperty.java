package cn.ytxu.api_semi_auto_creater.config.property;

import cn.ytxu.api_semi_auto_creater.config.PropertyEntity;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/28.
 */
public class FilterProperty {// 需要输出的版本号列表

    private static FilterProperty instance;

    private PropertyEntity.FilterBean filter;
    private boolean useHeaderFilter = false;// 是否使用过滤request header的功能

    private FilterProperty(PropertyEntity.FilterBean filter) {
        this.filter = filter;
    }

    public static FilterProperty getInstance() {
        return instance;
    }

    public static void load(PropertyEntity.FilterBean filter) {
        instance = new FilterProperty(filter);
        configHeaderFilterFun(filter);
    }

    private static void configHeaderFilterFun(PropertyEntity.FilterBean filter) {
        instance.useHeaderFilter = filter.getHeaders().size() > 0;
    }

    /**
     * 过滤request中header参数
     */
    public boolean hasThisHeaderInFilterHeaders(String headerName) {
        if (!useHeaderFilter) {
            return false;
        }
        for (String filterHeader : filter.getHeaders()) {
            if (filterHeader.equals(headerName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 过滤api中版本的key，获取到需要输出的版本号
     */
    public List<VersionModel> getVersionsAfterFilter(DocModel docModel) {
        if (!filter.isUse_output_versions()) {
            return docModel.getVersions();
        }

        List<VersionModel> versions = new ArrayList<>();
        for (VersionModel version : docModel.getVersions()) {
            if (isOutputVersion(version)) {
                versions.add(version);
            }
        }
        return versions;
    }

    private boolean isOutputVersion(VersionModel version) {
        for (String outputVersion : filter.getOutput_versions()) {
            if (outputVersion.equals(version.getName())) {
                return true;
            }
        }
        return false;
    }
}
