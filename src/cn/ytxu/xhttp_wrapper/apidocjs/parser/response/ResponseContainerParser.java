package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseContainerModel;

/**
 * Created by Administrator on 2016/9/23.
 * parse success and error param
 */
public class ResponseContainerParser {
    private RequestModel request;
    private FieldContainerBean successBean, errorBean;

    public ResponseContainerParser(RequestModel request) {
        this.request = request;
        ApiDataBean apiData = ApidocjsHelper.getApiData().getApiData(request);
        this.successBean = apiData.getSuccess();
        this.errorBean = apiData.getError();
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
