package cn.ytxu.http_wrapper.model.request.input;

import cn.ytxu.http_wrapper.model.field.FieldModel;

/**
 * Created by ytxu on 2016/12/4.
 */
public class InputModel extends FieldModel<InputGroupModel> {

    public InputModel(InputGroupModel higherLevel) {
        super(higherLevel);
        higherLevel.addInput(this);
    }


    //*************** reflect method area ***************
    public String input_type() {
        return type();
    }

    public String input_request_param_type() {
        return requestParamType();
    }

    public String input_name() {
        return getName();
    }

    public String input_desc() {
        return getDescription();
    }

    public boolean input_isOptional() {
        return isOptional();
    }

}
