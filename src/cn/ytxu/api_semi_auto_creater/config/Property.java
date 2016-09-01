package cn.ytxu.api_semi_auto_creater.config;

import cn.ytxu.api_semi_auto_creater.config.property.FilterProperty;
import cn.ytxu.api_semi_auto_creater.config.property.element_type.ElementTypeProperty;
import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.BaseResponseEntityNameProperty;
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
        FilterProperty.load(object.getFilter());
        BaseResponseEntityNameProperty.load(object.getResponse());
        ElementTypeProperty.load(object.getElement_type_enum());
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

}
