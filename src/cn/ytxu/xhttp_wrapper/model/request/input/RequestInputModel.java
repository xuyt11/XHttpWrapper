package cn.ytxu.xhttp_wrapper.model.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/24.
 */
public class RequestInputModel extends FieldGroupModel<RequestInputGroupModel> {

    public RequestInputModel(RequestInputGroupModel higherLevel, Map.Entry<String, List<FieldBean>> element) {
        super(higherLevel, element);
    }

}
