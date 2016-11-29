package cn.ytxu.xhttp_wrapper.config.property.filter;

import cn.ytxu.util.LogUtil;
import cn.ytxu.xhttp_wrapper.model.VersionModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/8/28.
 */
public class FilterWrapper {// 需要输出的版本号列表

    private static FilterWrapper instance;

    private FilterBean filter;

    public static FilterWrapper getInstance() {
        return instance;
    }

    public static void load(FilterBean filter) {
        instance = new FilterWrapper(filter);
        LogUtil.i(FilterWrapper.class, "load filter property success...");
    }

    private FilterWrapper(FilterBean filter) {
        this.filter = filter;
        if (filter.isUseHeaders() && filter.getHeaders().size() <= 0) {
            throw new IllegalArgumentException("u setup use filter headers function, but the headers property don`t setup...");
        }
        if (filter.isUseOutputVersions() && filter.getOutputVersions().size() <= 0) {
            throw new IllegalArgumentException("u setup use filter output versions function, but the output versions property don`t setup...");
        }
    }

    /**
     * 过滤request中header参数
     */
    public boolean hasThisHeaderInFilterHeaders(String headerName) {
        if (!filter.isUseHeaders()) {
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
        if (!filter.isUseOutputVersions()) {
            return versions;
        }

        List<VersionModel> dest = new ArrayList<>();
        for (VersionModel version : versions) {
            try {
                findOutputVersionByVersionModel(version);
            } catch (NotFoundOutputVersionException ignore) {
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
                filterVersionBean = findOutputVersionByVersionModel(version);
            } catch (NotFoundOutputVersionException ignore) {
                continue;
            }
            sections.addAll(getSectionsAfterFilted(version, filterVersionBean));
        }
        return sections;
    }

    private FilterVersionBean findOutputVersionByVersionModel(VersionModel version) {
        final String targetVersionName = version.getName();
        for (FilterVersionBean outputVersion : filter.getOutputVersions()) {
            if (isOutputVersionByVersionName(targetVersionName, outputVersion)) {
                return outputVersion;
            }
        }
        throw new NotFoundOutputVersionException();
    }

    private boolean isOutputVersionByVersionName(String targetVersionName, FilterVersionBean output_version) {
        return output_version.getOutput_version_name().equals(targetVersionName);
    }

    private static class NotFoundOutputVersionException extends RuntimeException {
    }

    private List<RequestGroupModel> getSectionsAfterFilted(VersionModel version, FilterVersionBean outputVersion) {
        if (!outputVersion.isUse_output_sections()) {
            return version.getRequestGroups();
        }

        List<String> sectionNames = outputVersion.getOutput_sections();
        if (sectionNames.size() <= 0) {
            return Collections.EMPTY_LIST;
        }

        List<RequestGroupModel> sections = new ArrayList<>(sectionNames.size());
        for (String sectionName : sectionNames) {
            for (RequestGroupModel sectionModel : version.getRequestGroups()) {
                if (sectionModel.getName().equals(sectionName)) {
                    sections.add(sectionModel);
                }
            }
        }
        return sections;
    }
}
