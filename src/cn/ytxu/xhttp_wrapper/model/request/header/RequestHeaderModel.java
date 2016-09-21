package cn.ytxu.xhttp_wrapper.model.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestHeaderModel extends BaseModel<RequestModel, Bean> {

    public RequestHeaderModel(RequestModel higherLevel, Bean bean) {
        super(higherLevel, bean);
    }


}
