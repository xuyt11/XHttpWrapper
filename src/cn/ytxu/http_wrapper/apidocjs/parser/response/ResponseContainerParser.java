package cn.ytxu.http_wrapper.apidocjs.parser.response;

import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.http_wrapper.apidocjs.parser.field.ExamplesParser;
import cn.ytxu.http_wrapper.common.enums.ResponseContentType;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.response.ResponseContainerModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;
import cn.ytxu.http_wrapper.model.response.field.ResponseFieldGroupModel;

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
        createSuccessResponseModel();
        parseSuccessResponseMessage();
    }

    private void createSuccessResponseModel() {
        new ExamplesParser<>(successBean.getExamples(), new ExamplesParser.Callback<ResponseModel>() {
            @Override
            public ResponseModel createExampleModel() {
                return new ResponseModel(responseContainer);
            }

            @Override
            public void parseExampleModelEnd(ResponseModel exampleModel) {
            }

            @Override
            public void setExampleModels(List<ResponseModel> exampleModel) {
            }

            @Override
            public void parseEnd(List<ResponseModel> exampleModel) {
                responseContainer.setSuccessResponses(exampleModel);
            }
        }).start();
    }

    private void parseSuccessResponseMessage() {
        responseContainer.getSuccessResponses().forEach(responseExample -> {
            ResponseContentType type = ResponseContentType.getByTypeName(responseExample.getType());
            type.parseResponseMessage(responseExample);
        });
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

        createErrorResponseModel();
        parseErrorResponseMessage();
    }

    private void createErrorResponseModel() {
        new ExamplesParser<>(errorBean.getExamples(), new ExamplesParser.Callback<ResponseModel>() {
            @Override
            public ResponseModel createExampleModel() {
                return new ResponseModel(responseContainer);
            }

            @Override
            public void parseExampleModelEnd(ResponseModel exampleModel) {
            }

            @Override
            public void setExampleModels(List<ResponseModel> exampleModel) {
            }

            @Override
            public void parseEnd(List<ResponseModel> exampleModel) {
                responseContainer.setErrorResponses(exampleModel);
            }
        }).start();
    }

    private void parseErrorResponseMessage() {
        responseContainer.getErrorResponses().forEach(responseExample -> {
            ResponseContentType type = ResponseContentType.getByTypeName(responseExample.getType());
            type.parseResponseMessage(responseExample);
        });
    }


}
