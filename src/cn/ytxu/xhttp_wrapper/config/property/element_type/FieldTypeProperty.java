package cn.ytxu.xhttp_wrapper.config.property.element_type;

import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;

/**
 * Created by ytxu on 2016/8/28.
 * 请求参数与响应体中输出参数类型的枚举
 * statement-format:key=value
 * key:类型名称；value：类型的输出值
 * value-format:field_type[,field_optional_type]
 * field_type:请求与实体类中参数的类型; field_optional_type:请求中可选参数的类型，或者是数组类型参数在请求中的类型
 */
public class FieldTypeProperty {

    private static FieldTypeProperty instance;
    private FieldTypeEnumBean fieldTypeEnum;

    public static FieldTypeProperty getInstance() {
        return instance;
    }

    public static void load(FieldTypeEnumBean fieldTypeEnum) {
        instance = new FieldTypeProperty(fieldTypeEnum);
    }

    private FieldTypeProperty(FieldTypeEnumBean fieldTypeEnum) {
        this.fieldTypeEnum = fieldTypeEnum;
    }

    public String getElementType(InputParamModel input) {
        ElementType etEnum = ElementType.getTypeByInput(input);
        return etEnum.getElementTypeByInput(this, input);
    }

    public String getElementRequestType(InputParamModel input) {
        ElementType etEnum = ElementType.getTypeByInput(input);
        return etEnum.getElementRequestTypeByInput(this, input);
    }

    public String getElementTypeByOutput(OutputParamModel output) {
        ElementType etEnum = ElementType.getTypeByOutputType(output.getType());
        return etEnum.getElementTypeByOutput(this, output);
    }

    public FieldTypeEnumBean.EtBean getNullET() {
        return fieldTypeEnum.getNull_et();
    }

    public FieldTypeEnumBean.EtBean getDateET() {
        return fieldTypeEnum.getDate_et();
    }

    public FieldTypeEnumBean.EtBean getFileET() {
        return fieldTypeEnum.getFile_et();
    }

    public FieldTypeEnumBean.EtBean getIntegerET() {
        return fieldTypeEnum.getInteger_et();
    }

    public FieldTypeEnumBean.EtBean getLongET() {
        return fieldTypeEnum.getLong_et();
    }

    public FieldTypeEnumBean.EtBean getBooleanET() {
        return fieldTypeEnum.getBoolean_et();
    }

    public FieldTypeEnumBean.EtBean getFloatET() {
        return fieldTypeEnum.getFloat_et();
    }

    public FieldTypeEnumBean.EtBean getDoubleET() {
        return fieldTypeEnum.getDouble_et();
    }

    public FieldTypeEnumBean.EtBean getNumberET() {
        return fieldTypeEnum.getNumber_et();
    }

    public FieldTypeEnumBean.EtBean getStringET() {
        return fieldTypeEnum.getString_et();
    }

    public FieldTypeEnumBean.EtBean getArrayET() {
        return fieldTypeEnum.getArray_et();
    }

    public FieldTypeEnumBean.EtBean getMapET() {
        return fieldTypeEnum.getMap_et();
    }

    public FieldTypeEnumBean.EtBean getObjectET() {
        return fieldTypeEnum.getObject_et();
    }

}
