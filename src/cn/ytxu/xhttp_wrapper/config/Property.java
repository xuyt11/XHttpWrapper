package cn.ytxu.xhttp_wrapper.config;

import cn.ytxu.xhttp_wrapper.config.property.api_data_file.ApiDataFileWrapper;
import cn.ytxu.xhttp_wrapper.config.property.base_config.BaseConfigWrapper;
import cn.ytxu.xhttp_wrapper.config.property.filter.FilterWrapper;
import cn.ytxu.xhttp_wrapper.config.property.element_type.FieldTypeProperty;
import cn.ytxu.xhttp_wrapper.config.property.response.ResponseWrapper;
import cn.ytxu.xhttp_wrapper.config.property.request.RequestWrapper;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeProperty;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ytxu on 2016/8/14.
 */
public class Property {

    public static void load(String xtempPrefixName) {
        InputStream in = null;
        try {
            String fileName = Suffix.Json.getTempFileName(xtempPrefixName);
            in = Property.class.getClassLoader().getResourceAsStream(fileName);
            PropertyConfig object = JSON.parseObject(in, PropertyConfig.class);
            load(object);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("init properties file failure...");
        } finally {
            close(in);
        }
    }

    private static void load(PropertyConfig object) {
        ApiDataFileWrapper.load(object.getApiDataFile());
        BaseConfigWrapper.load(object.getConfig());
        FilterWrapper.load(object.getFilter());
        RequestWrapper.load(object.getRequest());
        ResponseWrapper.load(object.getResponse());
        FieldTypeProperty.load(object.getField_type_enum());
        StatusCodeProperty.load(object.getStatus_code());
    }

    private static void close(InputStream in) {
        if (in == null) {
            return;
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ApiDataFileWrapper getApiDataFile() {
        return ApiDataFileWrapper.getInstance();
    }

    public static BaseConfigWrapper getBaseConfig() {
        return BaseConfigWrapper.getInstance();
    }

    public static FilterWrapper getFilter() {
        return FilterWrapper.getInstance();
    }

    public static RequestWrapper getRequest() {
        return RequestWrapper.getInstance();
    }

    public static ResponseWrapper getResponse() {
        return ResponseWrapper.getInstance();
    }

    public static FieldTypeProperty getFieldTypeProperty() {
        return FieldTypeProperty.getInstance();
    }

    public static StatusCodeProperty getStatusCodeProperty() {
        return StatusCodeProperty.getInstance();
    }

}
