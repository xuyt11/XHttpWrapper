package cn.ytxu.xhttp_wrapper.config;

import cn.ytxu.xhttp_wrapper.config.property.apidoc.ApidocOutputDataFileBean;
import cn.ytxu.xhttp_wrapper.config.property.base_response_entity_name.ResponseBean;
import cn.ytxu.xhttp_wrapper.config.property.config.ConfigBean;
import cn.ytxu.xhttp_wrapper.config.property.element_type.FieldTypeEnumBean;
import cn.ytxu.xhttp_wrapper.config.property.filter.FilterBean;
import cn.ytxu.xhttp_wrapper.config.property.request.RequestBean;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeBean;
import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.util.List;

/**
 * Created by ytxu on 2016/8/31.<br>
 */
public class PropertyEntity {
    private ConfigBean config = ConfigBean.DEFAULT;
    private List<ApidocOutputDataFileBean> apidoc_output_data_file;
    private FilterBean filter = FilterBean.DEFAULT;
    private RequestBean request;
    private ResponseBean response;
    private StatusCodeBean status_code;
    private FieldTypeEnumBean field_type_enum;

    public ConfigBean getConfig() {
        return config;
    }

    public List<ApidocOutputDataFileBean> getApidoc_output_data_file() {
        return apidoc_output_data_file;
    }

    public void setApidoc_output_data_file(List<ApidocOutputDataFileBean> apidoc_output_data_file) {
        this.apidoc_output_data_file = apidoc_output_data_file;
    }

    public FilterBean getFilter() {
        return filter;
    }

    public void setFilter(FilterBean filter) {
        this.filter = filter;
    }

    public RequestBean getRequest() {
        return request;
    }

    public void setRequest(RequestBean request) {
        this.request = request;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public StatusCodeBean getStatus_code() {
        return status_code;
    }

    public void setStatus_code(StatusCodeBean status_code) {
        this.status_code = status_code;
    }

    public FieldTypeEnumBean getField_type_enum() {
        return field_type_enum;
    }

    public void setField_type_enum(FieldTypeEnumBean field_type_enum) {
        this.field_type_enum = field_type_enum;
    }


    public static void main(String... args) {
        InputStream in = PropertyEntity.class.getClassLoader().getResourceAsStream("NewChama-android.json");
        try {
            PropertyEntity object = JSON.parseObject(in, PropertyEntity.class);
            System.out.println("object:" + object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
