package cn.ytxu.api_semi_auto_creater.entity;

import java.util.List;

/**
 * Created by newchama on 16/3/30.
 */
public class StatusCodeModel extends BaseModel<DocumentModel> {

    private String name;
    private String value;
    private String desc;

    public StatusCodeModel(String name, String desc, String value) {
        super(null, null);// TODO 需要更改
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
    public static StatusCodeModel getTarget(List<StatusCodeModel> statusCodes, String statusCode) {
        if (null == statusCode) {
            return null;
        }

        for (StatusCodeModel statusCodeEntity : statusCodes) {
            if (statusCodeEntity.getValue().equals(statusCode)) {
                return statusCodeEntity;
            }
        }

        return null;
    }


}
