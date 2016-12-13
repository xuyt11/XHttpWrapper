package cn.ytxu.http_wrapper.config.property.response;

import java.util.Arrays;
import java.util.List;

/**
 * 基础response必须的字段的字段名称；
 * format(key:value-->value:base response entity name)
 */
public class ResponseBean {
    private BaseResponseParamBean statusCode;
    private BaseResponseParamBean message;
    private BaseResponseParamBean error;
    private BaseResponseParamBean data;

    public String getStatusCode() {
        return statusCode.getName();
    }

    public void setStatusCode(BaseResponseParamBean statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message.getName();
    }

    public void setMessage(BaseResponseParamBean message) {
        this.message = message;
    }

    public String getError() {
        return error.getName();
    }

    public void setError(BaseResponseParamBean error) {
        this.error = error;
    }

    public String getData() {
        return data.getName();
    }

    public void setData(BaseResponseParamBean data) {
        this.data = data;
    }

    public List<BaseResponseParamBean> getAll() {
        return Arrays.asList(statusCode, message, error, data);
    }

    public String getErrorType() {
        return error.getType();
    }

}