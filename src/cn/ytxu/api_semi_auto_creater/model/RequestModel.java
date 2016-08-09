package cn.ytxu.api_semi_auto_creater.model;

import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import org.jsoup.nodes.Element;

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

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public void setRestfulUrl(RESTfulUrlModel restfulUrl) {
        this.restfulUrl = restfulUrl;
    }

    public void setDefinedParams(List<DefinedParamModel> definedParams) {
        this.definedParams = definedParams;
    }

    public void setHeaders(List<InputParamModel> headers) {
        this.headers = headers;
    }

    public void setInputParams(List<InputParamModel> inputs) {
        this.inputs = inputs;
    }
}
