package cn.ytxu.apacer.entity;

import java.util.List;

/**
 * 请求响应实体类
 * 2016-04-05
 */
public class ResponseEntity {

    private String responseDesc;// 响应的描述
    private String responseHeader;// 响应头
    private String responseContent;// 响应内容

    private List<OutputParamEntity> outputParams;
    private String statusCode = null;// 该响应的status_code值,可以与Api中statusCode进行比较

    private MethodEntity method;

    public ResponseEntity(String responseDesc, String responseHeader, String responseContent) {
        this.responseDesc = responseDesc;
        this.responseHeader = responseHeader;
        this.responseContent = responseContent;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public String getResponseContent() {
        return responseContent;
    }


    public List<OutputParamEntity> getOutputParams() {
        return outputParams;
    }

    public void setOutputParams(List<OutputParamEntity> outputParams) {
        this.outputParams = outputParams;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public MethodEntity getMethod() {
        return method;
    }

    public static void setMethod(List<ResponseEntity> responses, MethodEntity method) {
        if (responses == null || responses.size() <= 0) {
            return;
        }

        for (ResponseEntity response : responses) {
            response.method = method;
        }
    }
}
