package cn.ytxu.xhttp_wrapper.model.status_code;

import cn.ytxu.xhttp_wrapper.model.BaseModel;

/**
 * Created by ytxu on 2016/8/30
 */
public class StatusCodeModel extends BaseModel<StatusCodeGroupModel, Void> {
    private final String group;
    private final String name;
    private final String number;
    private final String desc;

    public StatusCodeModel(StatusCodeGroupModel higherLevel, String group, String name, String number, String desc) {
        super(higherLevel);
        this.group = group;
        this.name = name;
        this.number = number;
        this.desc = desc;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getDesc() {
        return desc;
    }


    //*************** reflect method area ***************
    public String status_code_name() {
        return name;
    }

    public String status_code_number() {
        return number;
    }

    public String status_code_desc() {
        return desc;
    }
}
