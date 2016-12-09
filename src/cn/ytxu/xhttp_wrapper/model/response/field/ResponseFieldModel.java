package cn.ytxu.xhttp_wrapper.model.response.field;

import cn.ytxu.xhttp_wrapper.model.field.FieldModel;

/**
 * Created by ytxu on 2016/12/4.
 */
public class ResponseFieldModel extends FieldModel<ResponseFieldGroupModel> {

    public ResponseFieldModel(ResponseFieldGroupModel higherLevel) {
        super(higherLevel);
        higherLevel.addField(this);
    }
}
