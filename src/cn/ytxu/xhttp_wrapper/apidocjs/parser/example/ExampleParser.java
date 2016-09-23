package cn.ytxu.xhttp_wrapper.apidocjs.parser.example;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ExampleBean;
import cn.ytxu.xhttp_wrapper.model.response.ResponseGroupModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public class ExampleParser {
    private ResponseGroupModel responseGroup;
    private List<ExampleBean> examples;

    public ExampleParser(ResponseGroupModel responseGroup, List<ExampleBean> examples) {
        this.responseGroup = responseGroup;
        this.examples = examples;
    }

    public void start() {
    }
}
