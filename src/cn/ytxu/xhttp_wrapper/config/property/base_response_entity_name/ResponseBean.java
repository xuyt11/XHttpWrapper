package cn.ytxu.xhttp_wrapper.config.property.base_response_entity_name;

import cn.ytxu.util.FileUtil;

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

    public static class BaseResponseParamBean {
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
}