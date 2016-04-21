package cn.ytxu.test;

/**
 * 响应数据实体类:所有的响应数据都要符合该格式
 * 2016-04-11
 */
public class ResponseEntity {

    private long status_code;
    private String message;
    private Error error;

    public static class Error {

    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                ", error=" + error +
                '}';
    }
}
