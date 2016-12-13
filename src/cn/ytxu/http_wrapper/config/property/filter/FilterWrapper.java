package cn.ytxu.http_wrapper.config.property.filter;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.model.request.RequestGroupModel;

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
        LogUtil.i(FilterWrapper.class, "load filter property start...");
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
    public List<RequestGroupModel> getRequestGroupsAfterFilted(List<VersionModel> versions) {
        versions = getVersionsAfterFilted(versions);
        List<RequestGroupModel> requestGroups = new ArrayList<>();
        for (VersionModel version : versions) {
            FilterVersionBean filterVersionBean;
            try {
                filterVersionBean = findOutputVersionByVersionModel(version);
                requestGroups.addAll(getRequestGroupsAfterFilted(version, filterVersionBean));
            } catch (NotNeedFilterOutputVersionException ignore) {
                requestGroups.addAll(version.getRequestGroups());
                continue;
            } catch (NotFoundOutputVersionException ignore) {
                continue;
            }
        }
        return requestGroups;
    }

    private FilterVersionBean findOutputVersionByVersionModel(VersionModel version) {
        if (!filter.isUseOutputVersions()) {
            throw new NotNeedFilterOutputVersionException();
        }

        final String targetVersionName = version.getName();
        for (FilterVersionBean outputVersion : filter.getOutputVersions()) {
            if (isOutputVersionByVersionName(targetVersionName, outputVersion)) {
                return outputVersion;
            }
        }
        throw new NotFoundOutputVersionException();
    }

    private static class NotNeedFilterOutputVersionException extends RuntimeException {
    }

    private boolean isOutputVersionByVersionName(String targetVersionName, FilterVersionBean output_version) {
        return output_version.getOutputVersionName().equals(targetVersionName);
    }

    private static class NotFoundOutputVersionException extends RuntimeException {
    }

    private List<RequestGroupModel> getRequestGroupsAfterFilted(VersionModel version, FilterVersionBean outputVersion) {
        if (!outputVersion.isUseOutputRequestGroup()) {
            return version.getRequestGroups();
        }

        List<String> requestGroupNames = outputVersion.getOutputRequestGroups();
        if (requestGroupNames.size() <= 0) {
            return Collections.EMPTY_LIST;
        }

        List<RequestGroupModel> requestGroups = new ArrayList<>(requestGroupNames.size());
        for (String requestGroupName : requestGroupNames) {
            for (RequestGroupModel requestGroupModel : version.getRequestGroups()) {
                if (requestGroupModel.getName().equals(requestGroupName)) {
                    requestGroups.add(requestGroupModel);
                }
            }
        }
        return requestGroups;
    }
}
