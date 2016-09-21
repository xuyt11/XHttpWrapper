package cn.ytxu.xhttp_wrapper.model.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestInputModel extends BaseModel<RequestModel, Bean> {

    private List<FieldGroupModel> fieldGroups = Collections.EMPTY_LIST;

    public RequestInputModel(RequestModel higherLevel, Bean element) {
        super(higherLevel, element);
    }

    public List<FieldGroupModel> getFieldGroups() {
        return fieldGroups;
    }

    public void setFieldGroups(List<FieldGroupModel> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }
}
