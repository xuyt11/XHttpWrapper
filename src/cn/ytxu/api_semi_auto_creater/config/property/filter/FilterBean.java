package cn.ytxu.api_semi_auto_creater.config.property.filter;

import java.util.Collections;
import java.util.List;

/**
 * 过滤request中header参数；如：Authorization,userId....<br>
 * <p>
 * use_output_versions:是否使用版本输出过滤；若为false，则下面的output_versions参数就失效
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
}