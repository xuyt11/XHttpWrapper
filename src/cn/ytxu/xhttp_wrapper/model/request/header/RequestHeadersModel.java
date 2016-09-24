package cn.ytxu.xhttp_wrapper.model.request.header;

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
public class RequestHeadersModel extends BaseModel<RequestModel, Bean> implements FieldGroupContainer<RequestHeadersModel> {

    private List<FieldGroupModel<RequestHeadersModel>> fieldGroups = Collections.EMPTY_LIST;
    private List<RequestHeaderExampleModel> headerExamples = Collections.EMPTY_LIST;

    public RequestHeadersModel(RequestModel higherLevel, Bean element) {
        super(higherLevel, element);
    }

    @Override
    public List<FieldGroupModel<RequestHeadersModel>> getFieldGroups() {
        return fieldGroups;
    }

    @Override
    public void setFieldGroups(List<FieldGroupModel<RequestHeadersModel>> fieldGroups) {
        this.fieldGroups = fieldGroups;
    }

    public List<RequestHeaderExampleModel> getHeaderExamples() {
        return headerExamples;
    }

    public void setHeaderExamples(List<RequestHeaderExampleModel> headerExamples) {
        this.headerExamples = headerExamples;
    }
}
