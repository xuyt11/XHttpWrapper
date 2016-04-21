package com.newchama.source;


/**
 * 响应数据实体类:所有的响应数据都要符合该格式
 * 2016-04-11
 */
public class ResponseEntity {

    private long status_code;
    private String message;
    private Error error;

    public long getStatus_code() {
        return status_code;
    }

    public void setStatus_code(long status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public static class Error {

    }
}


//
///**
// * 响应数据实体类:所有的响应数据都要符合该格式
// * 2016-04-11
// */
//public class ResponseEntity<T> {
//
//    private long status_code;
//    private String message;
//    private T data;
//    private Error error;
//
//    public long getStatus_code() {
//        return status_code;
//    }
//
//    public void setStatus_code(long status_code) {
//        this.status_code = status_code;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }
//
//    public Error getError() {
//        return error;
//    }
//
//    public void setError(Error error) {
//        this.error = error;
//    }
//
//    public static class Error {
//
//    }
//}
