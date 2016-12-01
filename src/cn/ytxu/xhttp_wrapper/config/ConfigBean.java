package cn.ytxu.xhttp_wrapper.config;

import cn.ytxu.xhttp_wrapper.config.property.api_data_file.ApiDataFileBean;
import cn.ytxu.xhttp_wrapper.config.property.response.ResponseBean;
import cn.ytxu.xhttp_wrapper.config.property.base_config.BaseConfigBean;
import cn.ytxu.xhttp_wrapper.config.property.element_type.FieldTypeEnumBean;
import cn.ytxu.xhttp_wrapper.config.property.filter.FilterBean;
import cn.ytxu.xhttp_wrapper.config.property.request.RequestBean;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeBean;

import java.util.List;

/**
 * Created by ytxu on 2016/8/31.<br>
 * 配置中数据的实体类
 */
public class ConfigBean {
    private List<ApiDataFileBean> api_data_file;
    private BaseConfigBean base_config = BaseConfigBean.DEFAULT;
    private FilterBean filter = FilterBean.DEFAULT;
    private RequestBean request;
    private ResponseBean response;
    private StatusCodeBean status_code;
    private FieldTypeEnumBean field_type_enum;

    public List<ApiDataFileBean> getApiDataFile() {
        return api_data_file;
    }

    public BaseConfigBean getConfig() {
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

    public FieldTypeEnumBean getFieldTypeEnum() {
        return field_type_enum;
    }


}
