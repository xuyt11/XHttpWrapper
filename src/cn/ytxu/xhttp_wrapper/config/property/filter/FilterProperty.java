package cn.ytxu.xhttp_wrapper.config.property.filter;

import cn.ytxu.xhttp_wrapper.model.VersionModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/28.
 */
public class FilterProperty {// 需要输出的版本号列表

    private static FilterProperty instance;

    private FilterBean filter;
    private boolean useHeaderFilter = false;// 是否使用过滤request header的功能

    private FilterProperty(FilterBean filter) {
        this.filter = filter;
    }

    public static FilterProperty getInstance() {
        return instance;
    }

    public static void load(FilterBean filter) {
        instance = new FilterProperty(filter);
        configHeaderFilterFun(filter);
    }

    private static void configHeaderFilterFun(FilterBean filter) {
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
    public List<VersionModel> getVersionsAfterFilted(List<VersionModel> versions) {
        if (!filter.isUse_output_versions()) {
            return versions;
        }

        List<VersionModel> dest = new ArrayList<>();
        for (VersionModel version : versions) {
            try {
                filter.findOutputVersionByVersionModel(version);
            } catch (FilterBean.NotFoundOutputVersionException ignore) {
                continue;
            }
            dest.add(version);
        }
        return dest;
    }

    /**
     * 获取到需要输出的分类
     */
    public List<RequestGroupModel> getSectionsAfterFilted(List<VersionModel> versions) {
        versions = getVersionsAfterFilted(versions);
        List<RequestGroupModel> sections = new ArrayList<>();
        for (VersionModel version : versions) {
            FilterVersionBean filterVersionBean;
            try {
                filterVersionBean = filter.findOutputVersionByVersionModel(version);
            } catch (FilterBean.NotFoundOutputVersionException ignore) {
                continue;
            }
            sections.addAll(filter.getSectionsAfterFilted(version, filterVersionBean));
        }
        return sections;
    }

}
