package cn.ytxu.http_wrapper.model.status_code;

import cn.ytxu.http_wrapper.model.BaseModel;

/**
 * Created by ytxu on 2016/8/30
 */
public class StatusCodeModel extends BaseModel<StatusCodeGroupModel> implements Comparable<StatusCodeModel> {
    private final String group;
    private final String name;
    private final int number;
    private final String desc;

    public StatusCodeModel(StatusCodeGroupModel higherLevel, String group, String name, int number, String desc) {
        super(higherLevel);
        higherLevel.addStatusCode(this);
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

    public int getNumber() {
        return number;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public int compareTo(StatusCodeModel o) {
        return this.number - o.number;
    }


    //*************** reflect method area ***************
    public String status_code_name() {
        return name;
    }

    public String status_code_number() {
        return number + "";
    }

    public String status_code_desc() {
        return desc;
    }

}
