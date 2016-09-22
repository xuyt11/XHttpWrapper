package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.request.input.RequestInputModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestInputParser {
    private RequestModel request;

    public RequestInputParser(RequestModel request) {
        this.request = request;
    }

    public RequestInputModel start() {
        Bean input = request.getElement().getParameter();
        RequestInputModel requestInput = new RequestInputModel(request, input);

        List<FieldGroupModel<RequestInputModel>> fieldGroups = new FieldGroupParser(requestInput, input).start();
        requestInput.setFieldGroups(fieldGroups);

        return requestInput;
    }

}
