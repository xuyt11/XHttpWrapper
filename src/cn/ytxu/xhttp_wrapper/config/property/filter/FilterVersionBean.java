package cn.ytxu.xhttp_wrapper.config.property.filter;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 16/9/8.
 * 过滤版本与过滤该版本下的分类<br>
 * use_output_request_group:是否对分类进行输出过滤；若为false，则下面的output_request_groups参数就失效<br>
 * 应用于API接口以及实体类<br>
 */
public class FilterVersionBean {
    /**
     * 输出的版本名称
     */
    private String output_version_name = "";
    /**
     * 是否对分类进行输出过滤
     */
    private boolean use_output_request_group = false;
    /**
     * 输出的版本下,需要输出的分类名称的数组
     */
    private List<String> output_request_groups = Collections.EMPTY_LIST;

    public String getOutputVersionName() {
        return output_version_name;
    }

    public boolean isUseOutputRequestGroup() {
        return use_output_request_group;
    }

    public List<String> getOutputRequestGroups() {
        return output_request_groups;
    }

    public void setOutput_version_name(String output_version_name) {
        this.output_version_name = output_version_name;
    }

    public void setUse_output_request_group(boolean use_output_request_group) {
        this.use_output_request_group = use_output_request_group;
    }

    public void setOutput_request_groups(List<String> output_request_groups) {
        this.output_request_groups = output_request_groups;
    }
}
