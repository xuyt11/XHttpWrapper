package cn.ytxu.api_semi_auto_creater.config;

import com.alibaba.fastjson.JSON;

import java.io.InputStream;
import java.util.Collections;
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

    public static class FilterBean {
        public static final FilterBean DEFAULT = new FilterBean();
        private List<String> headers = Collections.EMPTY_LIST;
        private boolean use_output_versions = false;
        private List<String> output_versions = Collections.EMPTY_LIST;

        public List<String> getHeaders() {
            return headers;
        }

        public void setHeaders(List<String> headers) {
            this.headers = headers;
        }

        public boolean isUse_output_versions() {
            return use_output_versions;
        }

        public void setUse_output_versions(boolean use_output_versions) {
            this.use_output_versions = use_output_versions;
        }

        public List<String> getOutput_versions() {
            return output_versions;
        }

        public void setOutput_versions(List<String> output_versions) {
            this.output_versions = output_versions;
        }
    }

    public static class ResponseBean {
        private String statusCode;
        private String message;
        private String error;
        private String data;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static class ElementTypeEnumBean {
        private EtBean null_et;
        private EtBean date_et;
        private EtBean file_et;
        private EtBean integer_et;
        private EtBean long_et;
        private EtBean boolean_et;
        private EtBean float_et;
        private EtBean double_et;
        private EtBean number_et;
        private EtBean string_et;
        private EtBean array_et;

        public EtBean getNull_et() {
            return null_et;
        }

        public void setNull_et(EtBean null_et) {
            this.null_et = null_et;
        }

        public EtBean getDate_et() {
            return date_et;
        }

        public void setDate_et(EtBean date_et) {
            this.date_et = date_et;
        }

        public EtBean getFile_et() {
            return file_et;
        }

        public void setFile_et(EtBean file_et) {
            this.file_et = file_et;
        }

        public EtBean getInteger_et() {
            return integer_et;
        }

        public void setInteger_et(EtBean integer_et) {
            this.integer_et = integer_et;
        }

        public EtBean getLong_et() {
            return long_et;
        }

        public void setLong_et(EtBean long_et) {
            this.long_et = long_et;
        }

        public EtBean getBoolean_et() {
            return boolean_et;
        }

        public void setBoolean_et(EtBean boolean_et) {
            this.boolean_et = boolean_et;
        }

        public EtBean getFloat_et() {
            return float_et;
        }

        public void setFloat_et(EtBean float_et) {
            this.float_et = float_et;
        }

        public EtBean getDouble_et() {
            return double_et;
        }

        public void setDouble_et(EtBean double_et) {
            this.double_et = double_et;
        }

        public EtBean getNumber_et() {
            return number_et;
        }

        public void setNumber_et(EtBean number_et) {
            this.number_et = number_et;
        }

        public EtBean getString_et() {
            return string_et;
        }

        public void setString_et(EtBean string_et) {
            this.string_et = string_et;
        }

        public EtBean getArray_et() {
            return array_et;
        }

        public void setArray_et(EtBean array_et) {
            this.array_et = array_et;
        }

        public static class EtBean {
            private String element_type;
            private String element_request_type;

            public String getElement_type() {
                return element_type;
            }

            public void setElement_type(String element_type) {
                this.element_type = element_type;
            }

            public String getElement_request_type() {
                return element_request_type;
            }

            public void setElement_request_type(String element_request_type) {
                this.element_request_type = element_request_type;
            }
        }
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
