package cn.ytxu.xhttp_wrapper.apidocjs.parser.field;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldModel;
import cn.ytxu.xhttp_wrapper.model.request.header.RequestHeaderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/21.
 */
public class FieldGroupParser<T extends BaseModel> {

    private T higherLevel;
    private Bean bean;

    public FieldGroupParser(T higherLevel, Bean bean) {
        this.higherLevel = higherLevel;
        this.bean = bean;
    }

    public List<FieldGroupModel<RequestHeaderModel>> start() {
        Set<Map.Entry<String, List<FieldBean>>> entrySet = bean.getFields().entrySet();
        List<FieldGroupModel<RequestHeaderModel>> fieldGroups = new ArrayList<>(entrySet.size());
        entrySet.forEach(fieldBeanMapEntry -> {
            FieldGroupModel fieldGroup = getFieldGroup(fieldBeanMapEntry);
            fieldGroups.add(fieldGroup);
        });
        return fieldGroups;
    }

    private FieldGroupModel getFieldGroup(Map.Entry<String, List<FieldBean>> element) {
        FieldGroupModel fieldGroup = new FieldGroupModel(higherLevel, element);
        List<FieldModel> fields = getFields(element, fieldGroup);
        fieldGroup.setFields(fields);
        return fieldGroup;
    }

    private List<FieldModel> getFields(Map.Entry<String, List<FieldBean>> element, FieldGroupModel fieldGroup) {
        List<FieldBean> fieldBeens = element.getValue();
        List<FieldModel> fields = new ArrayList<>(fieldBeens.size());
        fieldBeens.forEach(fieldBean -> {
            FieldModel field = new FieldParser(fieldGroup, fieldBean).start();
            fields.add(field);
        });
        return fields;
    }

}
