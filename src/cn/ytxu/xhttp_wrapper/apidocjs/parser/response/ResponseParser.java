package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseGroupModel;

/**
 * Created by Administrator on 2016/9/23.
 * parse success and error param
 */
public class ResponseParser {
    private RequestModel request;
    private Bean successBean, errorBean;

    public ResponseParser(RequestModel request) {
        this.request = request;
        this.successBean = request.getElement().getSuccess();
        this.errorBean = request.getElement().getError();
    }

    public void start() {
        parseSuccessGroup();
        parseErrorGroup();
    }

    private void parseSuccessGroup() {
        ResponseGroupModel success = new ResponseGroupModel(request, successBean);
        new FieldGroupParser(success, successBean, success).start();

        request.setSuccessResponseGroup(success);
    }

    private void parseErrorGroup() {
        ResponseGroupModel error = new ResponseGroupModel(request, errorBean);
        new FieldGroupParser(error, errorBean, error).start();

        request.setErrorResponseGroup(error);
    }
}
