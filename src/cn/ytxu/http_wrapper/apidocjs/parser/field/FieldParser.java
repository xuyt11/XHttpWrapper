package cn.ytxu.http_wrapper.apidocjs.parser.field;

import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.config.property.param_type.ParamTypeBean;
import cn.ytxu.http_wrapper.model.field.FieldModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/21.
 */
public class FieldParser<T extends FieldModel> {
    //****************** data type ******************
    private static final String PATTERN_FRONT = "{DataType:";
    private static final String PATTERN_END = "}";
    private static final Pattern DATA_TYPE_PATTERN = Pattern.compile("(\\{DataType:)\\w+(\\})");

    private final T field;
    private final FieldBean element;

    public FieldParser(T fieldModel, FieldBean element) {
        this.field = fieldModel;
        this.element = element;
    }

    public FieldModel<T> start() {
        field.setName(element.getField());
        field.setGroup(element.getGroup());
        field.setType(element.getType());
        field.setOptional(element.isOptional());
        field.setDefaultValue(element.getDefaultValue());
        field.setDescription(element.getDescription());
        field.setSize(element.getSize());

        setFieldType();
        setDataType();
        return field;
    }

    private void setFieldType() {
        ParamTypeBean paramTypeBean = ConfigWrapper.getParamType().getParamTypeBean(field.getType());
        field.setParamTypeBean(paramTypeBean);
    }

    private void setDataType() {
        Matcher m = DATA_TYPE_PATTERN.matcher(field.getDescription());
        if (!m.find()) {
            return;
        }

        String group = m.group();
        int methodNameStart = PATTERN_FRONT.length();
        int methodNameEnd = group.length() - PATTERN_END.length();
        String dataType = group.substring(methodNameStart, methodNameEnd);
        field.setDataType(dataType);
    }


}
