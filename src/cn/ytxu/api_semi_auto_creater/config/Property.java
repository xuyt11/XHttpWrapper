package cn.ytxu.api_semi_auto_creater.config;

import cn.ytxu.api_semi_auto_creater.config.property.FilterRequestHeaderProperty;
import cn.ytxu.api_semi_auto_creater.config.property.element_type.ElementTypeProperty;
import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.BaseResponseEntityNameProperty;
import cn.ytxu.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by ytxu on 2016/8/14.
 */
public class Property {

    public static void load() {
        InputStream in = null;
        try {
            Properties pps = new Properties();
            in = Property.class.getClassLoader().getResourceAsStream("NewChama-android.properties");
            pps.load(in);
            FilterRequestHeaderProperty.load(pps);
            BaseResponseEntityNameProperty.createByParseProperties(pps);
            ElementTypeProperty.createProperties(pps);
            pps.clear();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("init properties file failure...");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static FilterRequestHeaderProperty getFilterRequestHeaderProperty() {
        return FilterRequestHeaderProperty.getInstance();
    }

    public static BaseResponseEntityNameProperty getBreNameProperty() {
        return BaseResponseEntityNameProperty.get();
    }


    public static void main(String... args) {
//        String filterHeadersStr = null;
//        String[] filterHeaders = filterHeadersStr.split(",");
//        Arrays.asList(filterHeaders);

        load();

    }

}
