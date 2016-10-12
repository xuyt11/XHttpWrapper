package cn.ytxu.xhttp_wrapper.config.property.filter;

import cn.ytxu.xhttp_wrapper.model.VersionModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 过滤request中header参数；如：Authorization,userId....<br>
 * use_output_versions:是否使用版本输出过滤；若为false，则下面的output_versions参数就失效<br>
 * output_versions:过滤版本与过滤该版本下的分类
 */
public class FilterBean {
    public static final FilterBean DEFAULT = new FilterBean();

    private List<String> headers = Collections.EMPTY_LIST;
    private boolean use_output_versions = false;
    private List<FilterVersionBean> output_versions = Collections.EMPTY_LIST;

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public boolean isUse_output_versions() {
        return use_output_versions;
    }

    public void setUse_output_versions(boolean use_output_versions) {
        this.use_output_versions = use_output_versions;
    }

    public List<FilterVersionBean> getOutput_versions() {
        return output_versions;
    }

    public void setOutput_versions(List<FilterVersionBean> output_versions) {
        this.output_versions = output_versions;
    }

    public FilterVersionBean findOutputVersionByVersionModel(VersionModel version) {
        final String targetVersionName = version.getName();
        for (FilterVersionBean outputVersion : getOutput_versions()) {
            if (isOutputVersionByVersionName(targetVersionName, outputVersion)) {
                return outputVersion;
            }
        }
        throw new NotFoundOutputVersionException();
    }

    private boolean isOutputVersionByVersionName(String targetVersionName, FilterVersionBean output_version) {
        return output_version.getOutput_version_name().equals(targetVersionName);
    }

    public static class NotFoundOutputVersionException extends RuntimeException {
    }


    public List<RequestGroupModel> getSectionsAfterFilted(VersionModel version, FilterVersionBean outputVersion) {
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