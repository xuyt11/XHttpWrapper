package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponsesModel;

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
        ResponsesModel success = new ResponsesModel(request, successBean);
        new FieldGroupParser(success, successBean, success).start();
        new ResponseParser(success, successBean.getExamples()).start();
        request.setSuccessResponses(success);
    }

    private void parseErrorGroup() {
        ResponsesModel error = new ResponsesModel(request, errorBean);
        new FieldGroupParser(error, errorBean, error).start();
        new ResponseParser(error, errorBean.getExamples()).start();
        request.setErrorResponses(error);
    }
}
