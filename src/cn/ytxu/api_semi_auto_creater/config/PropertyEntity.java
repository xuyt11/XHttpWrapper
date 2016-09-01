package cn.ytxu.api_semi_auto_creater.config;

import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.ResponseBean;
import cn.ytxu.api_semi_auto_creater.config.property.element_type.ElementTypeEnumBean;
import cn.ytxu.api_semi_auto_creater.config.property.filter.FilterBean;
import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.util.List;

/**
 * Created by Administrator on 2016/8/31.<br>
 * 过滤request中header参数；如：Authorization,userId....<br>
 * <p>
 * use_output_versions:是否使用版本输出过滤；若为false，则下面的output_versions参数就失效
 * 过滤版本：枚举出需要输出的版本号
 * temp:(filter.output_versions=1.3.1)-->只输出‘1.3.1’版本的API接口以及实体类
 * temp:(filter.output_versions=1.3.1,1.5.0)-->输出‘1.3.1’以及‘1.5.0’版本的API接口以及实体类<br>
 * <p>
 * 基础response必须的字段的字段名称；format(key:value-->value:base response entity name)<br>
 * <p>
 * status code 在Section中的名称的配置<br>
 * <p>
 * apidoc.html文件地址的配置，包括多操作系统的配置<br>
 * <p>
 * 请求参数与响应体中输出参数类型的枚举
 * statement-format:key=value
 * key:类型名称；value：类型的输出值
 * value-format:element_type[,element_request_type]
 * element_type:请求与实体类中参数的类型; element_request_type:请求中可选参数的类型，或者是数组类型参数在请求中的类型<br>
 * <p>
 * 只有请求方法中有file类型<br>
 * <p>
 * ${object}:子类型的替换字符串<br>
 */
public class PropertyEntity {
    // TODO 替换掉apidoc 与所有的properties中的解析，同时更改statuscode的解析
    private FilterBean filter = FilterBean.DEFAULT;
    private ResponseBean response;
    private String status_code;
    private ElementTypeEnumBean element_type_enum;
    private List<ApidocFileAddressesBean> apidoc_file_addresses;

    public FilterBean getFilter() {
        return filter;
    }

    public void setFilter(FilterBean filter) {
        this.filter = filter;
    }

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
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



    public static class ApidocFileAddressesBean {
        private String OSName;
        private String address;

        public String getOSName() {
            return OSName;
        }

        public void setOSName(String OSName) {
            this.OSName = OSName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
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
