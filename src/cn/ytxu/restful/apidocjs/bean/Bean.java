package cn.ytxu.restful.apidocjs.bean;

import java.util.List;
import java.util.Map;

public class Bean {
    /**
     * k:field group name
     * v:fields
     */
    private Map<String, List<FieldBean>> fields;
    private List<ExampleBean> examples;

    public Map<String, List<FieldBean>> getFields() {
        return fields;
    }

    public void setFields(Map<String, List<FieldBean>> fields) {
        this.fields = fields;
    }

    public List<ExampleBean> getExamples() {
        return examples;
    }

    public void setExamples(List<ExampleBean> examples) {
        this.examples = examples;
    }
}