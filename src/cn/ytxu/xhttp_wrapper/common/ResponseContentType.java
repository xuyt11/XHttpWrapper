package cn.ytxu.xhttp_wrapper.common;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.json.JsonResponseMessageParser;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;

/**
 * Created by ytxu on 2016/10/11.
 * 响应体的类型，用于对响应体的解析
 */
public enum ResponseContentType {
    json {
        @Override
        public void parse(ResponseModel response) {
            new JsonResponseMessageParser(response).start();
        }
    },
    text {
        @Override
        public void parse(ResponseModel response) {
            throw new IllegalArgumentException("u must setup text`s response content type parser...");
        }
    },
    xml {
        @Override
        public void parse(ResponseModel response) {
            throw new IllegalArgumentException("u must setup xml`s response content type parser...");
        }
    };

    public abstract void parse(ResponseModel response);

    public static ResponseContentType getByTypeName(String typeName) {
        for (ResponseContentType responseContentType : ResponseContentType.values()) {
            if (responseContentType.name().equals(typeName)) {
                return responseContentType;
            }
        }
        throw new UnknownResponseContentTypeException();
    }

    private static final class UnknownResponseContentTypeException extends RuntimeException {
    }
}
