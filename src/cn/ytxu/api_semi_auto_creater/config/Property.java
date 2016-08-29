package cn.ytxu.api_semi_auto_creater.config;

import cn.ytxu.api_semi_auto_creater.config.property.FilterProperty;
import cn.ytxu.api_semi_auto_creater.config.property.element_type.ElementTypeProperty;
import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.BaseResponseEntityNameProperty;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by ytxu on 2016/8/14.
 */
public class Property {

    public static void load(String xtempPrefixName) {
        InputStream in = null;
        try {
            Properties pps = new Properties();
            String fileName = Suffix.Properties.getTempFileName(xtempPrefixName);
            in = Property.class.getClassLoader().getResourceAsStream(fileName);
            pps.load(in);
            load(pps);
            pps.clear();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("init properties file failure...");
        } finally {
            close(in);
        }
    }

    private static void load(Properties pps) {
        FilterProperty.load(pps);
        BaseResponseEntityNameProperty.load(pps);
        ElementTypeProperty.load(pps);
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
