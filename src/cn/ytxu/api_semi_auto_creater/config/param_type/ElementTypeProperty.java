package cn.ytxu.api_semi_auto_creater.config.param_type;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by ytxu on 2016/8/28.
 * 请求参数与响应体中输出参数类型的枚举
 * statement-format:key=value
 * key:类型名称；value：类型的输出值
 * value-format:element_type[,element_request_type]
 * element_type:请求与实体类中参数的类型; element_request_type:请求中可选参数的类型
 */
public class ElementTypeProperty {

    private static List<ElementTypeProperty> properties = new ArrayList<>(ElementType.values().length);

    private ElementType elementType;//
    private String element_type;// 请求与实体类中参数的类型
    private String element_request_type;// 请求中可选参数的类型

    private ElementTypeProperty(ElementType elementType) {
        this(elementType, null, null);
    }

    private ElementTypeProperty(ElementType elementType, String element_type) {
        this(elementType, element_type, element_type);
    }

    private ElementTypeProperty(ElementType elementType, String element_type, String element_request_type) {
        this.elementType = elementType;
        this.element_type = element_type;
        this.element_request_type = element_request_type;
    }

    public String getElementType() {
        return element_type;
    }

    public String getElementRequestType() {
        return element_request_type;
    }

    public static void createProperties(Properties pps) {
        for (ElementType type : ElementType.values()) {
            String propertyKey = type.getPropertyKey();
            String value = pps.getProperty(propertyKey, null);
            ElementTypeProperty property = getParamTypeProperty(type, value);
            properties.add(property);
        }
    }

    /**
     * value-format:element_type[,element_request_type]
     */
    private static ElementTypeProperty getParamTypeProperty(ElementType type, String value) {
        if (Objects.isNull(value)) {
            return new ElementTypeProperty(type);
        }
        String[] types = value.trim().split(",");
        if (types.length == 0) {
            return new ElementTypeProperty(type);
        }
        if (types.length == 1) {
            return new ElementTypeProperty(type, value.trim());
        }
        return new ElementTypeProperty(type, types[0].trim(), types[1].trim());
    }

    public static ElementTypeProperty getByElementType(ElementType elementType) {
        for (ElementTypeProperty property : properties) {
            if (property.elementType == elementType) {
                return property;
            }
        }
        throw new IllegalArgumentException("it can not happend, but log element type name is " + elementType.name());
    }

}
