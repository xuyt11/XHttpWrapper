package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.example.ResponseExampleParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseGroupModel;

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
        ResponseGroupModel success = new ResponseGroupModel(request, successBean);
        new FieldGroupParser(success, successBean, success).start();
        new ResponseExampleParser(success, successBean.getExamples()).start();
        request.setSuccessResponseGroup(success);
    }

    private void parseErrorGroup() {
        ResponseGroupModel error = new ResponseGroupModel(request, errorBean);
        new FieldGroupParser(error, errorBean, error).start();
        new ResponseExampleParser(error, errorBean.getExamples()).start();
        request.setErrorResponseGroup(error);
    }
}
