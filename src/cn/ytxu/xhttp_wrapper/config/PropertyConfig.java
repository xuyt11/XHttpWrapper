package cn.ytxu.xhttp_wrapper.config;

import cn.ytxu.xhttp_wrapper.config.property.api_data_file.ApiDataFileBean;
import cn.ytxu.xhttp_wrapper.config.property.base_response_entity_name.ResponseBean;
import cn.ytxu.xhttp_wrapper.config.property.base_config.BaseConfigBean;
import cn.ytxu.xhttp_wrapper.config.property.element_type.FieldTypeEnumBean;
import cn.ytxu.xhttp_wrapper.config.property.filter.FilterBean;
import cn.ytxu.xhttp_wrapper.config.property.request.RequestBean;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeBean;
import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.util.List;

/**
 * Created by ytxu on 2016/8/31.<br>
 * 配置中数据的实体类
 */
public class PropertyConfig {
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

    public StatusCodeBean getStatus_code() {
        return status_code;
    }

    public FieldTypeEnumBean getField_type_enum() {
        return field_type_enum;
    }

    public static void main(String... args) {
        InputStream in = PropertyConfig.class.getClassLoader().getResourceAsStream("NewChama-android.json");
        try {
            PropertyConfig object = JSON.parseObject(in, PropertyConfig.class);
            System.out.println("object:" + object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
