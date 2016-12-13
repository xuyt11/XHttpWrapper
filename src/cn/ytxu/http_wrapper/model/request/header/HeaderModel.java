package cn.ytxu.http_wrapper.model.request.header;

import cn.ytxu.http_wrapper.model.field.FieldModel;

/**
 * Created by ytxu on 2016/12/4.
 */
public class HeaderModel extends FieldModel<HeaderGroupModel> {

    public HeaderModel(HeaderGroupModel higherLevel) {
        super(higherLevel);
        higherLevel.addHeader(this);
    }


    //*************** reflect method area ***************
    public String header_type() {
        return type();
    }

    public String header_request_param_type() {
        return requestParamType();
    }

    public String header_name() {
        return getName();
    }

    public String header_desc() {
        return getDescription();
    }

}
