package cn.ytxu.api_semi_auto_creater.model;

import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.api_semi_auto_creater.model.request.restful_url.RESTfulParamModel;
import cn.ytxu.api_semi_auto_creater.model.request.restful_url.RESTfulUrlModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.http_wrapper.common.util.FileUtil;
import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class RequestModel extends BaseModel<SectionModel> {
    private String name;// 请求名称
    private String version;// 版本号
    private String descrption;// 请求描述:Account - 初始化装好信息
    private String methodType;// 请求方法类型:get
    private RESTfulUrlModel restfulUrl;// url
    private List<DefinedParamModel> definedParams;// 已定义了的参数：有参数名，参数类型，参数描述等信息
    private List<InputParamModel> headers, inputs;// 请求的头部参数与输入参数
    private List<ResponseModel> responses = Collections.EMPTY_LIST;// 响应列表

    public RequestModel(SectionModel higherLevel, Element element, String name, String version) {
        super(higherLevel, element);
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
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

    public List<DefinedParamModel> getDefinedParams() {
        return definedParams;
    }

    public void setDefinedParams(List<DefinedParamModel> definedParams) {
        this.definedParams = definedParams;
    }

    public List<InputParamModel> getHeaders() {
        return headers;
    }

    public void setHeaders(List<InputParamModel> headers) {
        this.headers = headers;
    }

    public List<InputParamModel> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputParamModel> inputs) {
        this.inputs = inputs;
    }

    public List<ResponseModel> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseModel> responses) {
        this.responses = responses;
    }

    //*************** reflect method area ***************
    public String request_desc() {
        return descrption;
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

    public List<InputParamModel> headers() {
        return headers;
    }

    public List<InputParamModel> filtered_headers() {
        List<InputParamModel> headers = new LinkedList<>(headers());
        Iterator<InputParamModel> iterator = headers.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().isFilterTag()) {
                iterator.remove();
            }
        }
        return headers;
    }

    public List<InputParamModel> inputs() {
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

}
