package cn.ytxu.xhttp_wrapper.config.property.element_type;

import cn.ytxu.util.TextUtil;

/**
 * 请求参数与响应体中输出参数类型的枚举
 * statement-format:key=value
 * key:类型名称；value：类型的输出值
 * value-format:field_type[,field_optional_type]
 * field_type:请求与实体类中参数的类型;
 * field_optional_type:请求中可选参数的类型，或者是数组类型参数在请求中的类型<br>
 * <p>
 * 只有请求方法中有file类型<br>
 * <p>
 * ${object}:子类型的替换字符串<br>
 */
public class FieldTypeEnumBean {
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
    private EtBean map_et;
    private EtBean object_et;


    public EtBean getNull_et() {
        return null_et;
    }

    public EtBean getDate_et() {
        return date_et;
    }

    public EtBean getFile_et() {
        return file_et;
    }

    public EtBean getInteger_et() {
        return integer_et;
    }

    public EtBean getLong_et() {
        return long_et;
    }

    public EtBean getBoolean_et() {
        return boolean_et;
    }

    public EtBean getFloat_et() {
        return float_et;
    }

    public EtBean getDouble_et() {
        return double_et;
    }

    public EtBean getNumber_et() {
        return number_et;
    }

    public EtBean getString_et() {
        return string_et;
    }

    public EtBean getArray_et() {
        return array_et;
    }

    public EtBean getMap_et() {
        return map_et;
    }

    public EtBean getObject_et() {
        return object_et;
    }

    /**
     * field_type:请求与实体类中参数的类型;
     * field_optional_type:请求中可选参数的类型，或者是数组类型参数在请求中的类型
     */
    public static class EtBean {
        private String field_type;
        private String field_optional_type;

        public String getField_type() {
            return field_type;
        }

        public String getField_optional_type() {
            return field_optional_type;
        }

        public boolean isInvalid() {
            return TextUtil.isBlank(field_type) || TextUtil.isBlank(field_optional_type);
        }
    }
}