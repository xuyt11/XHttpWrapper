package cn.ytxu.xhttp_wrapper.config;

import cn.ytxu.xhttp_wrapper.config.property.apidoc.ApidocProperty;
import cn.ytxu.xhttp_wrapper.config.property.config.ConfigProperty;
import cn.ytxu.xhttp_wrapper.config.property.filter.FilterProperty;
import cn.ytxu.xhttp_wrapper.config.property.element_type.ElementTypeProperty;
import cn.ytxu.xhttp_wrapper.config.property.base_response_entity_name.BaseResponseEntityNameProperty;
import cn.ytxu.xhttp_wrapper.config.property.request.RequestProperty;
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
            PropertyEntity object = JSON.parseObject(in, PropertyEntity.class);
            load(object);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("init properties file failure...");
        } finally {
            close(in);
        }
    }

    private static void load(PropertyEntity object) {
        ConfigProperty.load(object.getConfig());
        FilterProperty.load(object.getFilter());
        RequestProperty.load(object.getRequest());
        BaseResponseEntityNameProperty.load(object.getResponse());
        ElementTypeProperty.load(object.getElement_type_enum());
        ApidocProperty.load(object.getApidoc_output_data_file());
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

    public static FilterProperty getFilterProperty() {
        return FilterProperty.getInstance();
    }

    public static BaseResponseEntityNameProperty getBRENameProperty() {
        return BaseResponseEntityNameProperty.get();
    }

    public static ApidocProperty getApidocProperty() {
        return ApidocProperty.getInstance();
    }

}
