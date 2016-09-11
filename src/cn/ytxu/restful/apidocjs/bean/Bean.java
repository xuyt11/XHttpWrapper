package cn.ytxu.restful.apidocjs.bean;

import java.util.List;
import java.util.Map;

public class Bean {
    private Map<String, FieldBean> fields;
    private List<ExampleBean> examples;

    public Map<String, FieldBean> getFields() {
        return fields;
    }

    public void setFields(Map<String, FieldBean> fields) {
        this.fields = fields;
    }

    public List<ExampleBean> getExamples() {
        return examples;
    }

    public void setExamples(List<ExampleBean> examples) {
        this.examples = examples;
    }
}