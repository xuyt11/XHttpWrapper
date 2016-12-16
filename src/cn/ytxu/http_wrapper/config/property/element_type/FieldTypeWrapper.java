package cn.ytxu.http_wrapper.config.property.element_type;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;


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
    }

    public String getElementTypeByOutput(OutputParamModel output) {
        ElementType etEnum = ElementType.getTypeByOutputType(output.getType());
        return etEnum.getElementTypeByOutput(output);
    }


}
