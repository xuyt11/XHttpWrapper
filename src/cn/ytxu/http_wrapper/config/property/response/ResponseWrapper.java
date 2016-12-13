package cn.ytxu.http_wrapper.config.property.response;

import cn.ytxu.http_wrapper.common.util.LogUtil;

import java.util.List;

/**
 * 基础response必须的字段的字段名称
 */
public class ResponseWrapper {

    private static ResponseWrapper instance;

    private ResponseBean response;

    public static ResponseWrapper getInstance() {
        return instance;
    }

    public static void load(ResponseBean response) {
        LogUtil.i(ResponseWrapper.class, "load response property start...");
        instance = new ResponseWrapper(response);
        LogUtil.i(ResponseWrapper.class, "load response property success...");
    }

    private ResponseWrapper(ResponseBean response) {
        this.response = response;
    }

    public String getStatusCode() {
        return response.getStatusCode();
    }

    public String getMessage() {
        return response.getMessage();
    }

    public String getError() {
        return response.getError();
    }

    public String getErrorType() {
        return response.getErrorType();
    }

    public String getData() {
        return response.getData();
    }

    public List<BaseResponseParamBean> getAll() {
        return response.getAll();
    }
}