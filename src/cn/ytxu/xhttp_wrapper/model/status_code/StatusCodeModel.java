package cn.ytxu.xhttp_wrapper.model.status_code;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;

/**
 * Created by ytxu on 2016/8/30
 */
public class StatusCodeModel extends BaseModel<StatusCodeGroupModel, FieldBean> {
    private final String group;
    private final String name;
    private final String value;
    private final String desc;

    public StatusCodeModel(StatusCodeGroupModel higherLevel, FieldBean element, String group, String name, String value, String desc) {
        super(higherLevel, element);
        this.group = group;
        this.name = name;
        this.value = value;
        this.desc = desc;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }


    //*************** reflect method area ***************
    public String status_code_name() {
        return name;
    }

    public String status_code_number() {
        return value;
    }

    public String status_code_desc() {
        return desc;
    }
}
