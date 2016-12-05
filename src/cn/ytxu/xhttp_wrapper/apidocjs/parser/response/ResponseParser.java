package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.example.ExampleBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.ExamplesParser;
import cn.ytxu.xhttp_wrapper.common.enums.ResponseContentType;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseContainerModel;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ResponseParser {
    private final ResponseContainerModel responseContainer;
    private final List<ExampleBean> examples;
    private final Callback callback;

    public ResponseParser(ResponseContainerModel responseContainer, List<ExampleBean> examples,
                          Callback callback) {
        this.responseContainer = responseContainer;
        this.examples = examples;
        this.callback = callback;

        if (Objects.isNull(callback)) {
            throw new RuntimeException("u must setup callback...");
        }
    }

    public void start() {
        if (examples.isEmpty()) {
            return;
        }

        createResponseModel();
        parseResponseMessage();
    }

    private void createResponseModel() {
        new ExamplesParser<>(examples, new ExamplesParser.Callback<ResponseModel>() {
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
                callback.setExampleModels(exampleModel);
            }
        }).start();
    }

    private void parseResponseMessage() {
        callback.getResponseModels().forEach(responseExample -> {
            ResponseContentType type = ResponseContentType.getByTypeName(responseExample.getType());
            type.parseResponseMessage(responseExample);
        });
    }


    public interface Callback {
        void setExampleModels(List<ResponseModel> responseModels);

        List<ResponseModel> getResponseModels();
    }
}
