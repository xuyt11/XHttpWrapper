package cn.ytxu.xhttp_wrapper.model.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/24.
 */
public class RequestInputGroupModel extends FieldGroupModel<RequestModel> {

    public RequestInputGroupModel(RequestModel higherLevel, Map.Entry<String, List<FieldBean>> element) {
        super(higherLevel, element);
    }

}
