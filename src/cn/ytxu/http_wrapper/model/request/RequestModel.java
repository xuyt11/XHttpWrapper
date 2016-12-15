package cn.ytxu.http_wrapper.model.request;

import cn.ytxu.http_wrapper.common.util.CamelCaseUtils;
import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.request.header.HeaderGroupModel;
import cn.ytxu.http_wrapper.model.request.header.HeaderModel;
import cn.ytxu.http_wrapper.model.request.input.InputGroupModel;
import cn.ytxu.http_wrapper.model.request.input.InputModel;
import cn.ytxu.http_wrapper.model.request.restful_url.RESTfulParamModel;
import cn.ytxu.http_wrapper.model.request.restful_url.RESTfulUrlModel;
import cn.ytxu.http_wrapper.model.response.ResponseContainerModel;

import java.util.*;

/**
 * Created by ytxu on 2016/7/20.
 */
public class RequestModel extends BaseModel<RequestGroupModel> implements Comparable<RequestModel> {
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

    private ResponseContainerModel responseContainer;

    public RequestModel(RequestGroupModel higherLevel) {
        super(higherLevel);
        higherLevel.addRequest(this);
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

    public void setHeaderGroups(List<HeaderGroupModel> headerGroups) {
        this.headerGroups = headerGroups;
    }

    public List<InputGroupModel> getInputGroups() {
        return inputGroups;
    }

    public void setInputGroups(List<InputGroupModel> inputGroups) {
        this.inputGroups = inputGroups;
    }

    public ResponseContainerModel getResponseContainer() {
        return responseContainer;
    }

    public void setResponseContainer(ResponseContainerModel responseContainer) {
        this.responseContainer = responseContainer;
    }

    @Override
    public int compareTo(RequestModel o) {
        return this.name.compareToIgnoreCase(o.name);
    }


    //*************** reflect method area ***************
    public String request_desc() {
        return description;
    }

    public String request_title() {
        return title;
    }

    public String request_name() {
        return CamelCaseUtils.toCamelCase(name);
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

    public String requestMethodByCapitalizeCamelCase() {//首字母大写的驼峰法命名
        return CamelCaseUtils.toCapitalizeCamelCase(methodType);
    }

    public List<HeaderModel> headers() {
        if (headerGroups.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<HeaderModel> headers = new ArrayList<>(headerGroups.size());
        headerGroups.forEach(headerGroup -> headers.addAll(headerGroup.getHeaders()));
        return headers;
    }

    public List<HeaderModel> filtered_headers() {
        List<HeaderModel> headers = new LinkedList<>(headers());
        Iterator<HeaderModel> iterator = headers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isFilterTag()) {
                iterator.remove();
            }
        }
        return headers;
    }

    public List<InputModel> inputs() {
        if (inputGroups.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<InputModel> inputs = new ArrayList<>(inputGroups.size());
        inputGroups.forEach(inputGroup -> inputs.addAll(inputGroup.getInputs()));
        return inputs;
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


    //*************** request param-->reflect method area ***************

    /**
     * 是否需要生成可选的请求方法(或叫缩略请求方法)；
     * 即：请求参数分组归类；
     */
    public boolean needGenerateOptionalRequestMethod() {
        return ConfigWrapper.getRequest().needGenerateOptionalRequestMethod(inputGroups);
    }

    public String request_param_class_name() {
        String className = FileUtil.getClassFileName(name);
        return className + "RP";
    }

    public List<InputGroupModel> input_groups() {
        return inputGroups;
    }

    public List<InputModel> input_groups_fileds() {
        List<InputModel> inputs = new ArrayList<>();
        for (InputGroupModel inputGroup : inputGroups) {
            inputs.addAll(inputGroup.getInputs());
        }
        return inputs;
    }

}
