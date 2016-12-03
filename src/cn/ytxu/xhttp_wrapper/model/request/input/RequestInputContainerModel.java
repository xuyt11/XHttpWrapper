package cn.ytxu.xhttp_wrapper.model.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupContainer;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestInputContainerModel extends BaseModel<RequestModel, FieldContainerBean> implements FieldGroupContainer<RequestInputGroupModel> {

    private List<RequestInputGroupModel> fieldGroups = Collections.EMPTY_LIST;
    private List<RequestInputExampleModel> inputExamples = Collections.EMPTY_LIST;

    public RequestInputContainerModel(RequestModel higherLevel, FieldContainerBean element) {
        super(higherLevel, element);
    }

    @Override
    public List<RequestInputGroupModel> getFieldGroups() {
        return fieldGroups;
    }

    @Override
    public void setFieldGroups(List<RequestInputGroupModel> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }

    public List<RequestInputExampleModel> getInputExamples() {
        return inputExamples;
    }

    public void setInputExamples(List<RequestInputExampleModel> inputExamples) {
        this.inputExamples = inputExamples;
    }
}
