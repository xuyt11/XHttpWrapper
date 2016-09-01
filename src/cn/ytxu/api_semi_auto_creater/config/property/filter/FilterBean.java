package cn.ytxu.api_semi_auto_creater.config.property.filter;

import java.util.Collections;
import java.util.List;

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