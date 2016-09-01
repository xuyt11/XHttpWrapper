package cn.ytxu.api_semi_auto_creater.config.property.filter;

import java.util.Collections;
import java.util.List;

/**
 * 过滤request中header参数；如：Authorization,userId....<br>
 * <p>
 * use_output_versions:是否使用版本输出过滤；若为false，则下面的output_versions参数就失效
 * 过滤版本：枚举出需要输出的版本号
 * temp:(filter.output_versions=1.3.1)-->只输出‘1.3.1’版本的API接口以及实体类
 * temp:(filter.output_versions=1.3.1,1.5.0)-->输出‘1.3.1’以及‘1.5.0’版本的API接口以及实体类<br>
 */
public class FilterBean {
    public static final FilterBean DEFAULT = new FilterBean();

    private List<String> headers = Collections.EMPTY_LIST;
    private boolean use_output_versions = false;
    private List<String> output_versions = Collections.EMPTY_LIST;

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

    public List<String> getOutput_versions() {
        return output_versions;
    }

    public void setOutput_versions(List<String> output_versions) {
        this.output_versions = output_versions;
    }
}