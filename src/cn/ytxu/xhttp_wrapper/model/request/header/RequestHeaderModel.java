package cn.ytxu.xhttp_wrapper.model.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestHeaderModel extends BaseModel<RequestModel, Bean> {

    private List<FieldGroupModel<RequestHeaderModel>> fieldGroups = Collections.EMPTY_LIST;

    public RequestHeaderModel(RequestModel higherLevel, Bean element) {
        super(higherLevel, element);
    }

    public List<FieldGroupModel<RequestHeaderModel>> getFieldGroups() {
        return fieldGroups;
    }

    public void setFieldGroups(List<FieldGroupModel<RequestHeaderModel>> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }
}
