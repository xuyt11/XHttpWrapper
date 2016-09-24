package cn.ytxu.xhttp_wrapper.model.field;

import cn.ytxu.xhttp_wrapper.model.BaseModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 * 字段分组的容器
 */
public interface FieldGroupContainer<FieldGroup extends FieldGroupModel> {

    List<FieldGroup> getFieldGroups();

    void setFieldGroups(List<FieldGroup> fieldGroups);
}
