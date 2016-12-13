package cn.ytxu.http_wrapper.model.response.field;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.response.ResponseContainerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/12/04.
 */
public class ResponseFieldGroupModel extends BaseModel<ResponseContainerModel> implements Comparable<ResponseFieldGroupModel> {
    private final String name;

    private List<ResponseFieldModel> fields = Collections.EMPTY_LIST;

    public ResponseFieldGroupModel(ResponseContainerModel higherLevel, String name) {
        super(higherLevel);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ResponseFieldModel> getFields() {
        return fields;
    }

    public void setFields(List<ResponseFieldModel> fields) {
        this.fields = fields;
    }

    public void addField(ResponseFieldModel field) {
        if (fields == Collections.EMPTY_LIST) {
            fields = new ArrayList<>();
        }
        fields.add(field);
    }

    @Override
    public int compareTo(ResponseFieldGroupModel o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
