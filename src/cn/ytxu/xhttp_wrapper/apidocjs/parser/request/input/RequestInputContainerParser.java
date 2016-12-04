package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestInputContainerParser {
    private RequestModel request;

    public RequestInputContainerParser(RequestModel request) {
        this.request = request;
    }

    public RequestInputContainerModel start() {
        FieldContainerBean input = ApidocjsHelper.getApiData().getApiData(request).getParameter();
        RequestInputContainerModel inputContainer = new RequestInputContainerModel(request, input);

        new FieldGroupParser(inputContainer, input, inputContainer).start();

        request.setInputContainer(inputContainer);
        return inputContainer;
    }

}
