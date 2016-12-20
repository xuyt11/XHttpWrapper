package cn.ytxu.http_wrapper.config.property.param_type;

import cn.ytxu.http_wrapper.common.util.TextUtil;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;

import java.util.Collections;
import java.util.List;

/**
 * 请求参数与响应字段的相关类型设置
 */
public class ParamTypeBean {

    /**
     * 在api文档中写入的类型名称，用于匹配类型，且忽略大小写
     */
    private List<String> match_type_names = Collections.EMPTY_LIST;

    /**
     * 请求参数的类型
     */
    private String request_param_type;

    /**
     * 请求中可选参数的类型，或者是数组、对象、集合等类型参数在请求中的类型
     */
    private String request_optional_param_type;

    /**
     * 响应中的参数类型
     */
    private String response_param_type;

    private ParamTypeEnum paramTypeEnum;


    public List<String> getMatchTypeNames() {
        return match_type_names;
    }

    public void setMatchTypeNames(List<String> match_type_names) {
        this.match_type_names = match_type_names;
    }

    public String getRequestParamType() {
        return request_param_type;
    }

    public void setRequestParamType(String request_param_type) {
        this.request_param_type = request_param_type;
    }

    public String getRequestOptionalParamType() {
        return request_optional_param_type;
    }

    public void setRequestOptionalParamType(String request_optional_param_type) {
        this.request_optional_param_type = request_optional_param_type;
    }

    public String getResponseParamType(OutputParamModel output) {
       return paramTypeEnum.getResponseParamType(response_param_type, output);
    }

    public void setResponseParamType(String response_param_type) {
        this.response_param_type = response_param_type;
    }

    public boolean isInvalid() {
        return TextUtil.isBlank(request_param_type) || TextUtil.isBlank(request_optional_param_type) || TextUtil.isBlank(response_param_type);
    }

    public void setParamTypeEnum(ParamTypeEnum paramTypeEnum) {
        this.paramTypeEnum = paramTypeEnum;
    }

    public ParamTypeEnum getParamTypeEnum() {
        return paramTypeEnum;
    }
}