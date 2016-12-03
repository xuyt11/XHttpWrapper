package cn.ytxu.xhttp_wrapper.apidocjs.parser.field;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/9/21.
 */
public class FieldParser {
    //****************** data type ******************
    private static final String PATTERN_FRONT = "{DataType:";
    private static final String PATTERN_END = "}";
    private static final Pattern DATA_TYPE_PATTERN = Pattern.compile("(\\{DataType:)\\w+(\\})");

    private FieldGroupModel higherLevel;
    private FieldBean element;

    private FieldModel field;

    public FieldParser(FieldGroupModel higherLevel, FieldBean element) {
        this.higherLevel = higherLevel;
        this.element = element;
    }

    public FieldModel start() {
        field = new FieldModel(higherLevel, element);

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
        FieldType fieldType = FieldType.getByFieldTypeStr(field.getType());
        field.setFieldType(fieldType);
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
