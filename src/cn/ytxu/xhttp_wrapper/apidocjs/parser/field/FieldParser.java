package cn.ytxu.xhttp_wrapper.apidocjs.parser.field;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldType;

/**
 * Created by Administrator on 2016/9/21.
 */
public class FieldParser {
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
        return field;
    }

    private void setFieldType() {
        FieldType fieldType = FieldType.getByFieldTypeStr(field.getType());
        field.setFieldType(fieldType);
    }

}
