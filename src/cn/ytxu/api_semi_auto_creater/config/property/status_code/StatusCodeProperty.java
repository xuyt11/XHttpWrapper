package cn.ytxu.api_semi_auto_creater.config.property.status_code;

/**
 * Created by ytxu on 2016/9/2.
 */
public class StatusCodeProperty {
    // TODO 同时更改statuscode的解析

    private static StatusCodeProperty instance;

    private StatusCodeBean statusCode;

    public static StatusCodeProperty getInstance() {
        return instance;
    }

    public static void load(StatusCodeBean statusCode) {
        instance = new StatusCodeProperty(statusCode);
    }

    private StatusCodeProperty(StatusCodeBean statusCode) {
        this.statusCode = statusCode;
    }
}
