package cn.ytxu.api_semi_auto_creater.config;

import cn.ytxu.api_semi_auto_creater.config.property.apidoc.ApidocFileAddressesBean;
import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.ResponseBean;
import cn.ytxu.api_semi_auto_creater.config.property.config.ConfigBean;
import cn.ytxu.api_semi_auto_creater.config.property.element_type.ElementTypeEnumBean;
import cn.ytxu.api_semi_auto_creater.config.property.filter.FilterBean;
import cn.ytxu.api_semi_auto_creater.config.property.request.RequestBean;
import cn.ytxu.api_semi_auto_creater.config.property.status_code.StatusCodeBean;
import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.util.List;

/**
 * Created by ytxu on 2016/8/31.<br>
 */
public class PropertyEntity {
    private ConfigBean config = ConfigBean.DEFAULT;
    private FilterBean filter = FilterBean.DEFAULT;
    private RequestBean request;
    private ResponseBean response;
    private StatusCodeBean status_code;
    private ElementTypeEnumBean element_type_enum;
    private List<ApidocFileAddressesBean> apidoc_file_addresses;

    public ConfigBean getConfig() {
        return config;
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

    public ElementTypeEnumBean getElement_type_enum() {
        return element_type_enum;
    }

    public void setElement_type_enum(ElementTypeEnumBean element_type_enum) {
        this.element_type_enum = element_type_enum;
    }

    public List<ApidocFileAddressesBean> getApidoc_file_addresses() {
        return apidoc_file_addresses;
    }

    public void setApidoc_file_addresses(List<ApidocFileAddressesBean> apidoc_file_addresses) {
        this.apidoc_file_addresses = apidoc_file_addresses;
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
