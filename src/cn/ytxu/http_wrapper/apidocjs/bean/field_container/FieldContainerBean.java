package cn.ytxu.http_wrapper.apidocjs.bean.field_container;

import cn.ytxu.http_wrapper.apidocjs.bean.field_container.example.ExampleBean;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * request:header,parameter
 * response:success,error
 */
public class FieldContainerBean {
    public static final FieldContainerBean EMPTY = new FieldContainerBean();

    /**
     * 在request与response中的field
     * k:field group name
     * 1，若为response，则为parent name
     * v:fields
     */
    private Map<String, List<FieldBean>> fields = Collections.EMPTY_MAP;
    /**
     * 若为request，则为field value example
     * 若为response，则为该response example
     */
    private List<ExampleBean> examples = Collections.EMPTY_LIST;

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