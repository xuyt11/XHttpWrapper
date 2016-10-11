package cn.ytxu.xhttp_wrapper.model.response;

/**
 * Created by ytxu on 2016/10/11.
 * 响应体的类型，用于对响应体的解析
 */
public enum ResponseContentType {
    json,
    text,
    xml;

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
