package cn.ytxu.http_wrapper.config.property.status_code;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/9/2.
 * status code的版本号（filted_versions）依赖于base_config中的版本号配置，
 * 若在base_config中过滤掉的配置的版本号，filted_versions中就算有，也不会生效
 */
public class StatusCodeBean {

    private String request_group_name;// status_code在Section(分类)中的名称(在Section中的名称的配置)
    /**
     * 解析状态码field所使用的解析模式的名称；例如：x_custom_model与default_value_model
     */
    private String parse_model;
    private boolean use_version_filter = false;// 是否使用版本过滤
    private List<String> filted_versions = Collections.EMPTY_LIST;// 过滤之后的版本号
    /**
     * response success时，status code的值
     */
    private String ok_number = "0";

    public String getRequestGroupName() {
        return request_group_name;
    }

    public String getParseModel() {
        return parse_model;
    }

    public boolean isUseVersionFilter() {
        return use_version_filter;
    }

    public List<String> getFiltedVersions() {
        return filted_versions;
    }

    public String getOkNumber() {
        return ok_number;
    }

    public void setRequest_group_name(String request_group_name) {
        this.request_group_name = request_group_name;
    }

    public void setParse_model(String parse_model) {
        this.parse_model = parse_model;
    }

    public void setUse_version_filter(boolean use_version_filter) {
        this.use_version_filter = use_version_filter;
    }

    public void setFilted_versions(List<String> filted_versions) {
        this.filted_versions = filted_versions;
    }

    public void setOk_number(String ok_number) {
        this.ok_number = ok_number;
    }
}
