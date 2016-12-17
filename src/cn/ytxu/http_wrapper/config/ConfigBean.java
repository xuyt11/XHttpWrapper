package cn.ytxu.http_wrapper.config;

import cn.ytxu.http_wrapper.config.property.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.config.property.param_type.ParamTypeBean;
import cn.ytxu.http_wrapper.config.property.response.ResponseBean;
import cn.ytxu.http_wrapper.config.property.base_config.BaseConfigBean;
import cn.ytxu.http_wrapper.config.property.filter.FilterBean;
import cn.ytxu.http_wrapper.config.property.request.RequestBean;
import cn.ytxu.http_wrapper.config.property.status_code.StatusCodeBean;
import cn.ytxu.http_wrapper.config.property.template_file_info.TemplateFileInfoBean;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ytxu on 2016/8/31.<br>
 * 配置中数据的实体类
 */
public class ConfigBean {
    private ApiDataBean api_data;
    private LinkedHashMap<String, TemplateFileInfoBean> template_file_infos;
    private BaseConfigBean base_config = BaseConfigBean.DEFAULT;
    private FilterBean filter = FilterBean.DEFAULT;
    private RequestBean request;
    private ResponseBean response;
    private StatusCodeBean status_code;
    private Map<String, ParamTypeBean> param_types = Collections.EMPTY_MAP;


    public ApiDataBean getApiData() {
        return api_data;
    }

    public LinkedHashMap<String, TemplateFileInfoBean> getTemplateFileInfos() {
        return template_file_infos;
    }

    public BaseConfigBean getBaseConfig() {
        return base_config;
    }

    public FilterBean getFilter() {
        return filter;
    }

    public RequestBean getRequest() {
        return request;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public StatusCodeBean getStatusCode() {
        return status_code;
    }

    public void setApi_data(ApiDataBean api_data) {
        this.api_data = api_data;
    }

    public void setTemplateFileInfos(LinkedHashMap<String, TemplateFileInfoBean> template_file_infos) {
        this.template_file_infos = template_file_infos;
    }

    public void setBase_config(BaseConfigBean base_config) {
        this.base_config = base_config;
    }

    public void setFilter(FilterBean filter) {
        this.filter = filter;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public void setStatus_code(StatusCodeBean status_code) {
        this.status_code = status_code;
    }

    public Map<String, ParamTypeBean> getParamTypes() {
        return param_types;
    }

    public void setParamTypes(Map<String, ParamTypeBean> param_types) {
        this.param_types = param_types;
    }
}
