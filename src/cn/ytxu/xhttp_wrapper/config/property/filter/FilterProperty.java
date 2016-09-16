package cn.ytxu.xhttp_wrapper.config.property.filter;

import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;

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
    public List<VersionModel> getVersionsAfterFilted(DocModel docModel) {
        if (!filter.isUse_output_versions()) {
            return docModel.getVersions();
        }

        List<VersionModel> versions = new ArrayList<>();
        for (VersionModel version : docModel.getVersions()) {
            try {
                filter.findOutputVersionByVersionModel(version);
            } catch (FilterBean.NotFoundOutputVersionException ignore) {
                continue;
            }
            versions.add(version);
        }
        return versions;
    }

    /**
     * 获取到需要输出的分类
     */
    public List<SectionModel> getSectionsAfterFilted(DocModel docModel) {
        if (!filter.isUse_output_versions()) {
            return docModel.getSections(false);
        }

        List<SectionModel> sections = new ArrayList<>();
        for (VersionModel version : docModel.getVersions()) {
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
