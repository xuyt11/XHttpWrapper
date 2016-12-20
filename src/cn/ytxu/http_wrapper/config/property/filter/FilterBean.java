package cn.ytxu.http_wrapper.config.property.filter;

import java.util.Collections;
import java.util.List;

/**
 * 过滤request中header参数；如：Authorization,userId....<br>
 * use_output_versions:是否使用版本输出过滤；若为false，则下面的output_versions参数就失效<br>
 * output_versions:过滤版本与过滤该版本下的分类
 */
public class FilterBean {
    public static final FilterBean DEFAULT = new FilterBean();

    private boolean use_headers = false;// 是否使用过滤request header的功能
    private List<String> headers = Collections.EMPTY_LIST;
    private boolean use_output_versions = false;// 是否使用版本输出过滤；若为false，则下面的output_versions参数就失效
    /**
     * 可以添加多个过滤规则，但是，每一个版本号只能在同一个规则中，否则只会使用第一个匹配的版本号规则进行过滤
     */
    private List<FilterVersionBean> output_versions = Collections.EMPTY_LIST;


    public boolean isUseHeaders() {
        return use_headers;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public boolean isUseOutputVersions() {
        return use_output_versions;
    }

    public List<FilterVersionBean> getOutputVersions() {
        return output_versions;
    }

    public void setUse_headers(boolean use_headers) {
        this.use_headers = use_headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setUse_output_versions(boolean use_output_versions) {
        this.use_output_versions = use_output_versions;
    }

    public void setOutput_versions(List<FilterVersionBean> output_versions) {
        this.output_versions = output_versions;
    }
}