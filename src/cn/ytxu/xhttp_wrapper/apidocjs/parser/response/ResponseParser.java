package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ExampleBean;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseGroupModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ResponseParser {
    private ResponseGroupModel responseGroup;
    private List<ExampleBean> examples;

    public ResponseParser(ResponseGroupModel responseGroup, List<ExampleBean> examples) {
        this.responseGroup = responseGroup;
        this.examples = examples;
    }

    public void start() {
        if (examples.size() <= 0) {
            return;
        }

        List<ResponseModel> responses = convertExampleBean2ResponseModel();
        responseGroup.setResponses(responses);
        parseResponse(responses);
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
            // TODO base on type to parse content
        });
    }
}
