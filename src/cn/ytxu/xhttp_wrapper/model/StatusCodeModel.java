package cn.ytxu.xhttp_wrapper.model;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;

/**
 * Created by ytxu on 2016/8/30.<br>
 * 状态码的desc格式：<br>
 * Field            Description<br>
 * OK               (0, '')<br>
 * UNAUTHORIZED     (1, '登录状态已过期，请重新登入')<br>
 * SERVER_ERROR     (5, '服务器错误') # 5XX 服务器错误<br>
 *
 * Field        format:statusCodeName<br>
 * Description  format1：statusCodeNumber, statusCodeDesc<br>
 *     statusCodeDesc String-->statusCodeDesc<br>
 * Description  format2：(statusCodeNumber, statusCodeDesc)<br>
 *     statusCodeDesc String-->statusCodeDesc)<br>
 * Description  format3：(statusCodeNumber, statusCodeDesc)xxx<br>
 *     statusCodeDesc String-->statusCodeDesc)xxx<br>
 */
public class StatusCodeModel extends BaseModel<StatusCodeGroupModel, FieldBean> {
    private final String name;
    private final String value;
    private final String desc;

    public StatusCodeModel(StatusCodeGroupModel higherLevel, FieldBean element, String name, String value, String desc) {
        super(higherLevel, element);
        this.name = name;
        this.value = value;
        this.desc = desc;
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
