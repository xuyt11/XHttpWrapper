package cn.ytxu.xhttp_wrapper.model.field;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by ytxu on 2016/9/21.
 */
public class FieldGroupModel<T extends BaseModel> extends BaseModel<T, Map.Entry<String, List<FieldBean>>> {

    private String name;
    private List<FieldModel> fields = Collections.EMPTY_LIST;

    public FieldGroupModel(T higherLevel, Map.Entry<String, List<FieldBean>> element) {
        super(higherLevel, element);
        this.name = element.getKey();
    }

    public String getName() {
        return name;
    }

    public List<FieldModel> getFields() {
        return fields;
    }

    public void setFields(List<FieldModel> fields) {
        this.fields = fields;
    }
}
