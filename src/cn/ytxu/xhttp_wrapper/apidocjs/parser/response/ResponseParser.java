package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.example.ExampleBean;
import cn.ytxu.xhttp_wrapper.common.enums.ResponseContentType;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseContainerModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ResponseParser {
    private ResponseContainerModel responseGroup;
    private List<ExampleBean> examples;

    public ResponseParser(ResponseContainerModel responseGroup, List<ExampleBean> examples) {
        this.responseGroup = responseGroup;
        this.examples = examples;
    }

    public void start() {
        if (examples.size() <= 0) {
            return;
        }

        List<ResponseModel> responses = convertExampleBean2ResponseModel();
        parseResponse(responses);
        responseGroup.setResponses(responses);
    }

    private List<ResponseModel> convertExampleBean2ResponseModel() {
        List<ResponseModel> responses = new ArrayList<>(examples.size());
        examples.forEach(example -> {
            ResponseModel exampleModel = new ResponseModel(responseGroup, example);
            responses.add(exampleModel);
        });
        return responses;
    }

    private void parseResponse(List<ResponseModel> responseExamples) {
        responseExamples.forEach(responseExample -> {
            ResponseContentType type = ResponseContentType.getByTypeName(responseExample.getType());
            type.parse(responseExample);
        });
    }
}
