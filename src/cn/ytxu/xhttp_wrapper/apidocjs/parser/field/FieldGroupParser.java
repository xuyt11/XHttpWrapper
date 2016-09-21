package cn.ytxu.xhttp_wrapper.apidocjs.parser.field;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/21.
 */
public class FieldGroupParser<T extends BaseModel> {

    private T higherLevel;
    private Map.Entry<String, List<FieldBean>> element;

    public FieldGroupParser(T higherLevel, Map.Entry<String, List<FieldBean>> element) {
        this.higherLevel = higherLevel;
        this.element = element;
    }

    public FieldGroupModel start() {
        FieldGroupModel fieldGroup = new FieldGroupModel(higherLevel, element);
        List<FieldModel> fields = getFields(fieldGroup);
        fieldGroup.setFields(fields);
        return fieldGroup;
    }

    private List<FieldModel> getFields(FieldGroupModel fieldGroup) {
        List<FieldBean> fieldBeens = element.getValue();
        List<FieldModel> fields = new ArrayList<>(fieldBeens.size());
        fieldBeens.forEach(fieldBean -> {
            FieldModel field = new FieldParser(fieldGroup, fieldBean).start();
            fields.add(field);
        });
        return fields;
    }

}
