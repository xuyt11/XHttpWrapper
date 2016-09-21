package cn.ytxu.xhttp_wrapper.model.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestInputModel extends BaseModel<RequestModel, Bean> {

    public RequestInputModel(RequestModel higherLevel, Bean bean) {
        super(higherLevel, bean);
    }


}
