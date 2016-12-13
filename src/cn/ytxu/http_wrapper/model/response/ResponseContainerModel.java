package cn.ytxu.http_wrapper.model.response;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.response.field.ResponseFieldGroupModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResponseContainerModel extends BaseModel<RequestModel> {

    private List<ResponseFieldGroupModel> successFieldGroups = Collections.EMPTY_LIST;
    private List<ResponseFieldGroupModel> errorFieldGroups = Collections.EMPTY_LIST;

    private List<ResponseModel> successResponses = Collections.EMPTY_LIST;
    private List<ResponseModel> errorResponses = Collections.EMPTY_LIST;

    public ResponseContainerModel(RequestModel higherLevel) {
        super(higherLevel);
        higherLevel.setResponseContainer(this);
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

    public List<ResponseModel> getSuccessResponses() {
        return successResponses;
    }

    public void setSuccessResponses(List<ResponseModel> successResponses) {
        this.successResponses = successResponses;
    }

    public List<ResponseModel> getErrorResponses() {
        return errorResponses;
    }

    public void setErrorResponses(List<ResponseModel> errorResponses) {
        this.errorResponses = errorResponses;
    }

    public List<ResponseFieldGroupModel> getFieldGroups() {
        List<ResponseFieldGroupModel> fieldGroups = new ArrayList<>(successFieldGroups);
        fieldGroups.addAll(errorFieldGroups);
        return fieldGroups;
    }
}