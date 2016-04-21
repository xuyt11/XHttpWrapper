package cn.ytxu.apacer.entity;

import java.util.List;

/**
 * Created by newchama on 16/3/30.
 */
public class StatusCodeEntity {

    private String name;
    private String value;
    private String desc;

    public StatusCodeEntity(String name, String desc, String value) {
        this.name = name;
        this.desc = desc;
        this.value = value;
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

    /** 获取目标状态码 */
    public static StatusCodeEntity getTarget(List<StatusCodeEntity> statusCodes, String statusCode) {
        if (null == statusCode) {
            return null;
        }

        for (StatusCodeEntity statusCodeEntity : statusCodes) {
            if (statusCodeEntity.getValue().equals(statusCode)) {
                return statusCodeEntity;
            }
        }

        return null;
    }


}
