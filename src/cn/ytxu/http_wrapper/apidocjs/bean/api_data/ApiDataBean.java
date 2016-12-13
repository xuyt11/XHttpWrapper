package cn.ytxu.http_wrapper.apidocjs.bean.api_data;

import cn.ytxu.http_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.http_wrapper.apidocjs.bean.other.PermissionBean;
import cn.ytxu.http_wrapper.apidocjs.bean.other.SampleRequestBean;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/9/11.
 * apidocjs中生成的api_data.json中内容的json格式
 */
public class ApiDataBean {
    /**
     * request method string
     * 可以有多个，以空格分隔
     * format：post patch
     */
    private String type;
    /**
     * request url:absolute url
     * 相对路径
     * format：/api/account/verify/
     */
    private String url;
    /**
     * request title
     * 接口名称 zh
     * format：提交验证信息(投资人)
     */
    private String title;
    /**
     * request version
     * 接口版本；
     * format：1.4.0
     */
    private String version;
    /**
     * request name
     * 接口名称 en
     * format：account_buyer_verify
     */
    private String name;
    /**
     * request group
     * 分组名称；
     * format：Account
     */
    private String group;

    private List<PermissionBean> permission = Collections.EMPTY_LIST;
    /**
     * request input param data
     */
    private FieldContainerBean parameter = FieldContainerBean.EMPTY;
    /**
     * response success data
     */
    private FieldContainerBean success = FieldContainerBean.EMPTY;
    /**
     * response error data
     */
    private FieldContainerBean error = FieldContainerBean.EMPTY;
    /**
     * request header data
     */
    private FieldContainerBean header = FieldContainerBean.EMPTY;
    private List<SampleRequestBean> sampleRequest = Collections.EMPTY_LIST;
    /**
     * request desc
     * 接口描述，支持html语法，且在两侧会有<p></p>标签
     * format：<p>xuyt_desc<font color='red'>red</font></p>
     */
    private String description;
    /**
     * 该接口所在的相对路径+所在文件
     * format：content/xuyt.py
     */
    private String filename;
    /**
     * 内容与group一致
     */
    private String groupTitle;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<PermissionBean> getPermission() {
        return permission;
    }

    public void setPermission(List<PermissionBean> permission) {
        this.permission = permission;
    }

    public FieldContainerBean getParameter() {
        return parameter;
    }

    public void setParameter(FieldContainerBean parameter) {
        this.parameter = parameter;
    }

    public FieldContainerBean getSuccess() {
        return success;
    }

    public void setSuccess(FieldContainerBean success) {
        this.success = success;
    }

    public FieldContainerBean getError() {
        return error;
    }

    public void setError(FieldContainerBean error) {
        this.error = error;
    }

    public FieldContainerBean getHeader() {
        return header;
    }

    public void setHeader(FieldContainerBean header) {
        this.header = header;
    }

    public List<SampleRequestBean> getSampleRequest() {
        return sampleRequest;
    }

    public void setSampleRequest(List<SampleRequestBean> sampleRequest) {
        this.sampleRequest = sampleRequest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }
}
