package cn.ytxu.http_wrapper.model.field;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.request.RequestModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/12/04.
 */
public class FieldGroupModel extends BaseModel<RequestModel> {
    private final String name;
    private List<FieldModel> fields = Collections.EMPTY_LIST;

    public FieldGroupModel(RequestModel higherLevel, String name) {
        super(higherLevel);
        this.name = name;
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
