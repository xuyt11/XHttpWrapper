package cn.ytxu.xhttp_wrapper.model.response;

import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.field.ResponseFieldGroupModel;

import java.util.Collections;
import java.util.List;

public class ResponseContainerModel extends BaseModel<RequestModel, Void> {

    private List<ResponseFieldGroupModel> successFieldGroups = Collections.EMPTY_LIST;
    private List<ResponseFieldGroupModel> errorFieldGroups = Collections.EMPTY_LIST;

    private List<ResponseModel> responses = Collections.EMPTY_LIST;

    public ResponseContainerModel(RequestModel higherLevel) {
        super(higherLevel);
    }

    public List<ResponseFieldGroupModel> getSuccessFieldGroups() {
        return successFieldGroups;
    }

    public List<ResponseFieldGroupModel> getErrorFieldGroups() {
        return errorFieldGroups;
    }

    public void setSuccessFieldGroups(List<ResponseFieldGroupModel> successFieldGroups) {
        this.successFieldGroups = successFieldGroups;
    }

    public void setErrorFieldGroups(List<ResponseFieldGroupModel> errorFieldGroups) {
        this.errorFieldGroups = errorFieldGroups;
    }

    public List<ResponseModel> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseModel> responses) {
        this.responses = responses;
    }
}