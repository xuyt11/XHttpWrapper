package cn.ytxu.api_semi_auto_creater.config.property.element_type;

import cn.ytxu.api_semi_auto_creater.config.PropertyEntity;
import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;

/**
 * Created by ytxu on 2016/8/28.
 * 请求参数与响应体中输出参数类型的枚举
 * statement-format:key=value
 * key:类型名称；value：类型的输出值
 * value-format:element_type[,element_request_type]
 * element_type:请求与实体类中参数的类型; element_request_type:请求中可选参数的类型，或者是数组类型参数在请求中的类型
 */
public class ElementTypeProperty {

    private static ElementTypeProperty instance;
    private PropertyEntity.ElementTypeEnumBean elementTypeEnum;

    public static ElementTypeProperty getInstance() {
        return instance;
    }

    public static void load(PropertyEntity.ElementTypeEnumBean elementTypeEnum) {
        instance = new ElementTypeProperty(elementTypeEnum);
    }

    private ElementTypeProperty(PropertyEntity.ElementTypeEnumBean elementTypeEnum) {
        this.elementTypeEnum = elementTypeEnum;
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

    public PropertyEntity.ElementTypeEnumBean.EtBean getNullET() {
        return elementTypeEnum.getNull_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getDateET() {
        return elementTypeEnum.getDate_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getFileET() {
        return elementTypeEnum.getFile_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getIntegerET() {
        return elementTypeEnum.getInteger_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getLongET() {
        return elementTypeEnum.getLong_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getBooleanET() {
        return elementTypeEnum.getBoolean_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getFloatET() {
        return elementTypeEnum.getFloat_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getDoubleET() {
        return elementTypeEnum.getDouble_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getNumberET() {
        return elementTypeEnum.getNumber_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getStringET() {
        return elementTypeEnum.getString_et();
    }

    public PropertyEntity.ElementTypeEnumBean.EtBean getArrayET() {
        return elementTypeEnum.getArray_et();
    }


}
