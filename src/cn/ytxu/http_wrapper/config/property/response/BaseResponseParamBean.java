package cn.ytxu.http_wrapper.config.property.response;

import cn.ytxu.http_wrapper.common.util.FileUtil;

public class BaseResponseParamBean {
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    //*************** reflect method area ***************
    public String bro_type() {
        return type;
    }

    public String bro_name() {
        return name;
    }

    public String bro_getter() {
        return "get" + FileUtil.getClassFileName(name);
    }

    public String bro_setter() {
        return "set" + FileUtil.getClassFileName(name);
    }

}