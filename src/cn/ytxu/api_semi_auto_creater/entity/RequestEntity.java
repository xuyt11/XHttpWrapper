package cn.ytxu.api_semi_auto_creater.entity;

import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.api_semi_auto_creater.model.RESTfulUrlModel;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 * 方法描述的实体类
 */
public class RequestEntity extends BaseEntity<SectionEntity> {
    private String descrption;// 方法的中文名称（描述）
    private String versionCode;// 该方法的版本号
    private String methodName;// 该方法的方法名称：驼峰法命名
    private String methodType;// 请求类型:post、get、patch...

    private RESTfulUrlModel restfulUrl;// url RESTful风格的解析对象

    private List<DefinedParamModel> definedParams;// 已定义了的参数

    private List<InputParamModel> headers;// 请求头字段
    private List<InputParamModel> inputParams;// 输入字段

    private List<ResponseEntity> responses;// 请求响应列表

    public RequestEntity(SectionEntity higherLevel, Element element) {
        super(higherLevel, element);
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
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

    public List<InputParamModel> getInputParams() {
        return inputParams;
    }

    public void setInputParams(List<InputParamModel> inputParams) {
        this.inputParams = inputParams;
    }

    public List<ResponseEntity> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseEntity> responses) {
        this.responses = responses;
    }
}
