package cn.ytxu.xhttp_wrapper.model.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupContainer;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestInputsModel extends BaseModel<RequestModel, Bean> implements FieldGroupContainer<RequestInputsModel> {

    private List<FieldGroupModel<RequestInputsModel>> fieldGroups = Collections.EMPTY_LIST;
    private List<RequestInputExampleModel> inputExamples = Collections.EMPTY_LIST;

    public RequestInputsModel(RequestModel higherLevel, Bean element) {
        super(higherLevel, element);
    }

    @Override
    public List<FieldGroupModel<RequestInputsModel>> getFieldGroups() {
        return fieldGroups;
    }

    @Override
    public void setFieldGroups(List<FieldGroupModel<RequestInputsModel>> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }

    public List<RequestInputExampleModel> getInputExamples() {
        return inputExamples;
    }

    public void setInputExamples(List<RequestInputExampleModel> inputExamples) {
        this.inputExamples = inputExamples;
    }
}
