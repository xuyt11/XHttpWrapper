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
public class ResponseModel extends FieldExampleModel<ResponseGroupModel> {

    public ResponseModel(ResponseGroupModel higherLevel, ExampleBean element) {
        super(higherLevel, element);
    }

}
