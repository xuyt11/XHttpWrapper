package cn.ytxu.xhttp_wrapper.model.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupContainer;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.Collections;
import java.util.List;

public class ResponsesModel extends BaseModel<RequestModel, Bean> implements FieldGroupContainer<ResponseFieldGroupModel> {

    private List<ResponseFieldGroupModel> fieldGroups = Collections.EMPTY_LIST;
    private List<ResponseModel> responses = Collections.EMPTY_LIST;

    public ResponsesModel(RequestModel higherLevel, Bean element) {
        super(higherLevel, element);
    }

    @Override
    public List<ResponseFieldGroupModel> getFieldGroups() {
        return fieldGroups;
    }

    @Override
    public void setFieldGroups(List<ResponseFieldGroupModel> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }

    public List<ResponseModel> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseModel> responses) {
        this.responses = responses;
    }
}