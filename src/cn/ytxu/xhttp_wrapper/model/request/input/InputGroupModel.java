package cn.ytxu.xhttp_wrapper.model.request.input;

import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class InputGroupModel extends BaseModel<RequestModel, Void> {
    private final String name;
    private List<InputModel> inputs = Collections.EMPTY_LIST;

    public InputGroupModel(RequestModel higherLevel, String name) {
        super(higherLevel);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<InputModel> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputModel> inputs) {
        this.inputs = inputs;
    }
}
