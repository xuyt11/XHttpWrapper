package cn.ytxu.xhttp_wrapper.apidocjs.parser.field;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldModel;

/**
 * Created by Administrator on 2016/9/21.
 */
public class FieldParser {
    private FieldGroupModel higherLevel;
    private FieldBean element;

    public FieldParser(FieldGroupModel higherLevel, FieldBean element) {
        this.higherLevel = higherLevel;
        this.element = element;
    }

    public FieldModel start() {
        FieldModel field = new FieldModel(higherLevel, element);

        field.setGroup(element.getGroup());
        field.setType(element.getType());
        field.setSize(element.getSize());
        field.setOptional(element.isOptional());
        field.setField(element.getField());
        field.setDefaultValue(element.getDefaultValue());
        field.setDescription(element.getDescription());

        return field;
    }
}
