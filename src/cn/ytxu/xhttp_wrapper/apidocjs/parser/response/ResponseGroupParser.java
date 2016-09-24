package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseContainerModel;

/**
 * Created by Administrator on 2016/9/23.
 * parse success and error param
 */
public class ResponseGroupParser {
    private RequestModel request;
    private Bean successBean, errorBean;

    public ResponseGroupParser(RequestModel request) {
        this.request = request;
        this.successBean = request.getElement().getSuccess();
        this.errorBean = request.getElement().getError();
    }

    public void start() {
        parseSuccessGroup();
        parseErrorGroup();
    }

    private void parseSuccessGroup() {
        ResponseContainerModel successContainer = new ResponseContainerModel(request, successBean);
        new FieldGroupParser(successContainer, successBean, successContainer).start();
        new ResponseParser(successContainer, successBean.getExamples()).start();
        request.setSuccessResponseContainer(successContainer);
    }

    private void parseErrorGroup() {
        ResponseContainerModel errorContainer = new ResponseContainerModel(request, errorBean);
        new FieldGroupParser(errorContainer, errorBean, errorContainer).start();
        new ResponseParser(errorContainer, errorBean.getExamples()).start();
        request.setErrorResponseContainer(errorContainer);
    }
}
