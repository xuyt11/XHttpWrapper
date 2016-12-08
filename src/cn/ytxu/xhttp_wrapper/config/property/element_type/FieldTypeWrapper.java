package cn.ytxu.xhttp_wrapper.config.property.element_type;

import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.util.LogUtil;
import cn.ytxu.xhttp_wrapper.model.response.OutputParamModel;

import java.util.Objects;

/**
 * Created by ytxu on 2016/8/28.
 * 请求参数与响应体中输出参数类型的枚举
 * statement-format:key=value
 * key:类型名称；value：类型的输出值
 * value-format:field_type[,field_optional_type]
 * field_type:请求与实体类中参数的类型; field_optional_type:请求中可选参数的类型，或者是数组类型参数在请求中的类型
 */
public class FieldTypeWrapper {

    private static FieldTypeWrapper instance;
    private FieldTypeEnumBean fieldTypeEnum;

    public static FieldTypeWrapper getInstance() {
        return instance;
    }

    public static void load(FieldTypeEnumBean fieldTypeEnum) {
        LogUtil.i(FieldTypeWrapper.class, "load field type enum property start...");
        instance = new FieldTypeWrapper(fieldTypeEnum);
        LogUtil.i(FieldTypeWrapper.class, "load field type enum property success...");
    }

    private FieldTypeWrapper(FieldTypeEnumBean fieldTypeEnum) {
        this.fieldTypeEnum = fieldTypeEnum;

        checkPrimaryProperty(fieldTypeEnum.getInteger_et(), "u must setup integer field type property...");
        checkPrimaryProperty(fieldTypeEnum.getFloat_et(), "u must setup float field type property...");
        checkPrimaryProperty(fieldTypeEnum.getBoolean_et(), "u must setup boolean field type property...");
        checkPrimaryProperty(fieldTypeEnum.getString_et(), "u must setup string field type property...");
        checkPrimaryProperty(fieldTypeEnum.getObject_et(), "u must setup object field type property...");
        checkPrimaryProperty(fieldTypeEnum.getArray_et(), "u must setup array field type property...");
        checkPrimaryProperty(fieldTypeEnum.getNull_et(), "u must setup null field type property...");

        checkMinorProperty(fieldTypeEnum.getLong_et(), "u can setup long field type property...");
        checkMinorProperty(fieldTypeEnum.getDouble_et(), "u can setup double field type property...");
        checkMinorProperty(fieldTypeEnum.getDate_et(), "u can setup date field type property...");
        checkMinorProperty(fieldTypeEnum.getFile_et(), "u can setup file field type property...");
        checkMinorProperty(fieldTypeEnum.getMap_et(), "u can setup map field type property...");

        checkCanBeDeletedProperty(fieldTypeEnum.getNumber_et(), "u can`t setup number field type property...");
    }

    private void checkPrimaryProperty(FieldTypeEnumBean.EtBean etBean, String logMessage) {
        if (Objects.isNull(etBean) || etBean.isInvalid()) {
            throw new IllegalArgumentException(logMessage);
        }
    }

    private void checkMinorProperty(FieldTypeEnumBean.EtBean etBean, String logMessage) {
        if (Objects.isNull(etBean) || etBean.isInvalid()) {
            LogUtil.i(FieldTypeWrapper.class, logMessage);
        }
    }

    private void checkCanBeDeletedProperty(FieldTypeEnumBean.EtBean etBean, String logMessage) {
        if (!Objects.isNull(etBean) && !etBean.isInvalid()) {
            LogUtil.i(FieldTypeWrapper.class, logMessage);
        }
    }

    public String getElementType(InputParamModel input) {
        ElementType etEnum = ElementType.getTypeByInput(input);
        return etEnum.getElementTypeByInput();
    }

    public String getElementRequestType(InputParamModel input) {
        ElementType etEnum = ElementType.getTypeByInput(input);
        return etEnum.getElementRequestTypeByInput();
    }

    public String getElementTypeByOutput(OutputParamModel output) {
        ElementType etEnum = ElementType.getTypeByOutputType(output.getType());
        return etEnum.getElementTypeByOutput(output);
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
