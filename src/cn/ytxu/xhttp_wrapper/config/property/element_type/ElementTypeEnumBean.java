package cn.ytxu.xhttp_wrapper.config.property.element_type;

/**
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
public class ElementTypeEnumBean {
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

    public EtBean getMap_et() {
        return map_et;
    }

    public void setMap_et(EtBean map_et) {
        this.map_et = map_et;
    }

    public EtBean getObject_et() {
        return object_et;
    }

    public void setObject_et(EtBean object_et) {
        this.object_et = object_et;
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