package cn.ytxu.xhttp_wrapper.apidocjs.parser.example;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ExampleBean;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseGroupModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ResponseExampleParser {
    private ResponseGroupModel responseGroup;
    private List<ExampleBean> examples;

    public ResponseExampleParser(ResponseGroupModel responseGroup, List<ExampleBean> examples) {
        this.responseGroup = responseGroup;
        this.examples = examples;
    }

    public void start() {
        if (examples.size() <= 0) {
            return;
        }

        List<ResponseModel> responseExamples = convertExampleBean2ResponseExampleModel();
        responseGroup.setResponses(responseExamples);
        parseResponse(responseExamples);
    }

    private List<ResponseModel> convertExampleBean2ResponseExampleModel() {
        List<ResponseModel> responseExamples = new ArrayList<>(examples.size());
        examples.forEach(example -> {
            ResponseModel exampleModel = new ResponseModel(responseGroup, example);
            responseExamples.add(exampleModel);
        });
        return responseExamples;
    }

    private void parseResponse(List<ResponseModel> responseExamples) {
        responseExamples.forEach(responseExample -> {
            // TODO base on type to parse content
        });
    }
}
