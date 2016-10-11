package cn.ytxu.xhttp_wrapper.model.response;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ExampleBean;
import cn.ytxu.xhttp_wrapper.model.field.FieldExampleModel;

/**
 * Created by ytxu on 2016/9/23.
 * response: success, error
 * title: response desc<br>
 * content: response format text<br>
 * type:response text`s type-->format:json,text...<br>
 * e.g.<br>
 * "title": "成功示例"<br>
 * "content": "HTTP 200 OK\nContent-Type: application/json\nVary: Accept\nAllow: GET, PUT, PATCH, HEAD, OPTIONS\n{\n    \"status_code\": 0,\n    \"message\": \"\",\n    \"data\": {\n        \"first_name\": \"test\",\n        \"weibo_url\": \"weibo\",\n        \"weixin_number\": \"wechat\",\n        \"summary\": \"test\",\n        \"member_investhistory\": [\n            {\n                \"invest_date\": \"2015-11-12\",\n                \"project_type\": 5,\n                \"project_stage\": 2,\n                \"project_name\": \"test\"\n            }\n        ]\n    }\n}"<br>
 * "type": "json"
 */
public class ResponseModel extends FieldExampleModel<ResponseContainerModel> {
    private String header;// 响应报文中的头部
    private String body;// 响应报文中的响应体

    // body中的字段数据
    private String statusCode = "";// 防止出现空指针异常

    public ResponseModel(ResponseContainerModel higherLevel, ExampleBean element) {
        super(higherLevel, element);
    }

    public void setBodyAndBody(String header, String body) {
        this.header = header;
        this.body = body;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
