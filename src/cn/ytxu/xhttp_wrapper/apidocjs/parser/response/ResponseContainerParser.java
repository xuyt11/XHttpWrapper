package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseContainerModel;
import cn.ytxu.xhttp_wrapper.model.response.field.ResponseFieldGroupModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 * parse success and error
 */
public class ResponseContainerParser {
    private final ResponseContainerModel responseContainer;
    private final FieldContainerBean successBean, errorBean;

    public ResponseContainerParser(RequestModel request) {
        this.responseContainer = new ResponseContainerModel(request);

        ApiDataBean apiData = ApidocjsHelper.getApiData().getApiData(request);
        this.successBean = apiData.getSuccess();
        this.errorBean = apiData.getError();
    }

    public void start() {
        parseSuccess();
        parseError();
    }

    private void parseSuccess() {
        List<ResponseFieldGroupModel> successFieldGroups =
                new ResponseFieldParser(responseContainer, successBean).start();
        responseContainer.setSuccessFieldGroups(successFieldGroups);

        new ResponseParser(responseContainer, successBean.getExamples()).start();
    }

    private void parseError() {
        List<ResponseFieldGroupModel> errorFieldGroups =
                new ResponseFieldParser(responseContainer, errorBean).start();
        responseContainer.setErrorFieldGroups(errorFieldGroups);

        new ResponseParser(responseContainer, errorBean.getExamples()).start();
    }

}
