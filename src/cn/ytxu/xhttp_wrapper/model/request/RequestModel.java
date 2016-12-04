package cn.ytxu.xhttp_wrapper.model.request;

import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.util.FileUtil;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldModel;
import cn.ytxu.xhttp_wrapper.model.request.header.HeaderGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.input.InputGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.restful_url.RESTfulParamModel;
import cn.ytxu.xhttp_wrapper.model.request.restful_url.RESTfulUrlModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseContainerModel;

import java.util.*;

/**
 * Created by ytxu on 2016/7/20.
 */
public class RequestModel extends BaseModel<RequestGroupModel, Void> {
    /**
     * 请求方法类型: request method string
     * 可以有多个，以空格分隔
     * format：(post|post patch|...)
     */
    private String methodType;
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
    /**
     * request desc
     * 接口描述，支持html语法，且在两侧会有<p></p>标签
     * format：<p>xuyt_desc<font color='red'>red</font></p>
     */
    private String description;

    private RESTfulUrlModel restfulUrl;// url
    private List<HeaderGroupModel> headerGroups = Collections.EMPTY_LIST;
    private List<InputGroupModel> inputGroups = Collections.EMPTY_LIST;
    private ResponseContainerModel successContainer, errorContainer;
    private List<ResponseModel> responses = Collections.EMPTY_LIST;// 响应列表

    public RequestModel(RequestGroupModel higherLevel) {
        super(higherLevel);
    }

    public void init(String type, String url, String title, String version, String name, String group, String description) {
        this.methodType = type;
        this.url = url;
        this.title = title;
        this.version = version;
        this.name = name;
        this.group = group;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descrption) {
        this.description = descrption;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public RESTfulUrlModel getRestfulUrl() {
        return restfulUrl;
    }

    public void setRestfulUrl(RESTfulUrlModel restfulUrl) {
        this.restfulUrl = restfulUrl;
    }

    public List<HeaderGroupModel> getHeaderGroups() {
        return headerGroups;
    }

    public void addHeaderGroup(HeaderGroupModel headerGroup) {
        if (headerGroups == Collections.EMPTY_LIST) {
            headerGroups = new ArrayList<>();
        }
        headerGroups.add(headerGroup);
    }

    public List<InputGroupModel> getInputGroups() {
        return inputGroups;
    }

    public void addInputGroup(InputGroupModel inputGroup) {
        if (inputGroups == Collections.EMPTY_LIST) {
            inputGroups = new ArrayList<>();
        }
        inputGroups.add(inputGroup);
    }

    public List<ResponseModel> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseModel> responses) {
        this.responses = responses;
    }

    public ResponseContainerModel getSuccessContainer() {
        return successContainer;
    }

    public ResponseContainerModel getErrorContainer() {
        return errorContainer;
    }

    public void setSuccessResponseContainer(ResponseContainerModel successContainer) {
        this.successContainer = successContainer;
    }

    public void setErrorResponseContainer(ResponseContainerModel errorContainer) {
        this.errorContainer = errorContainer;
    }

    //*************** reflect method area ***************
    public String request_desc() {
        return description;
    }

    public String request_name() {
        return name;
    }

    public String request_class_name() {
        String className = FileUtil.getClassFileName(name);
        return className;
    }

    public String request_version() {
        return version;
    }

    public String request_url() {
        return restfulUrl.getUrl();
    }

    public String request_METHOD() {
        return methodType.toUpperCase();
    }

    public List<FieldModel> headers() {
        return getFieldModels(headerGroups);
    }

    private <T extends FieldGroupModel> List<FieldModel> getFieldModels(List<T> fieldGroups) {
        List<FieldModel> fields = new ArrayList<>();
        fieldGroups.forEach(fieldGroup -> fields.addAll(fieldGroup.getFields()));
        return fields;
    }

    public List<FieldModel> filtered_headers() {
        List<FieldModel> headers = new LinkedList<>(headers());
        Iterator<FieldModel> iterator = headers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isFilterTag()) {
                iterator.remove();
            }
        }
        return headers;
    }

    public List<FieldModel> inputs() {
        return getFieldModels(inputGroups);
    }

    public boolean request_url_is_RESTful() {
        return restfulUrl.isRESTfulUrl();
    }

    public List<RESTfulParamModel> RESTful_fields() {
        return restfulUrl.getParams();
    }

    public String request_normal_url() {
        return restfulUrl.request_normal_url();
    }

    public String request_convert_url() {
        return restfulUrl.request_convert_url();
    }

}
