package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseContainerModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;
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
        parseSuccessField();
        parseSuccessResponse();
        parseErrorField();
        parseErrorResponse();
    }

    private void parseSuccessField() {
        if (successBean.getFields().isEmpty()) {
            return;
        }

        List<ResponseFieldGroupModel> successFieldGroups =
                new ResponseFieldParser(responseContainer, successBean).start();
        responseContainer.setSuccessFieldGroups(successFieldGroups);
    }

    private void parseSuccessResponse() {
        if (successBean.getExamples().isEmpty()) {
            return;
        }

        new ResponseParser(responseContainer, successBean.getExamples(), new ResponseParser.Callback() {
            @Override
            public void setExampleModels(List<ResponseModel> responseModels) {
                responseContainer.setSuccessResponses(responseModels);
            }

            @Override
            public List<ResponseModel> getResponseModels() {
                return responseContainer.getSuccessResponses();
            }
        }).start();
    }

    private void parseErrorField() {
        if (errorBean.getFields().isEmpty()) {
            return;
        }

        List<ResponseFieldGroupModel> errorFieldGroups =
                new ResponseFieldParser(responseContainer, errorBean).start();
        responseContainer.setErrorFieldGroups(errorFieldGroups);
    }

    private void parseErrorResponse() {
        if (errorBean.getExamples().isEmpty()) {
            return;
        }

        new ResponseParser(responseContainer, errorBean.getExamples(), new ResponseParser.Callback() {
            @Override
            public void setExampleModels(List<ResponseModel> responseModels) {
                responseContainer.setErrorResponses(responseModels);
            }

            @Override
            public List<ResponseModel> getResponseModels() {
                return responseContainer.getErrorResponses();
            }
        }).start();
    }

}
