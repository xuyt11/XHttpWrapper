package cn.ytxu.http_wrapper.model.field;

import cn.ytxu.http_wrapper.model.BaseModel;

/**
 * Created by ytxu on 2016/9/23.
 * request : header, parameter<br>
 * title: param`s name<br>
 * content: param format text<br>
 * type: param`s type<br>
 * e.g.<br>
 * 1、type:json; content:{"name":"isAuthed"}<br>
 * 2、type:array; content:1,2,3,5-->以逗号分隔数组，并以string传输<br>
 * <p>
 * response: success, error
 * title: response desc<br>
 * content: response format text<br>
 * type:response text`s type-->format:json,text...<br>
 * e.g.<br>
 * "title": "成功示例"<br>
 * "content": "HTTP 200 OK\nContent-Type: application/json\nVary: Accept\nAllow: GET, PUT, PATCH, HEAD, OPTIONS\n{\n    \"status_code\": 0,\n    \"message\": \"\",\n    \"data\": {\n        \"first_name\": \"test\",\n        \"weibo_url\": \"weibo\",\n        \"weixin_number\": \"wechat\",\n        \"summary\": \"test\",\n        \"member_investhistory\": [\n            {\n                \"invest_date\": \"2015-11-12\",\n                \"project_type\": 5,\n                \"project_stage\": 2,\n                \"project_name\": \"test\"\n            }\n        ]\n    }\n}"<br>
 * "type": "json"
 */
public class ExampleModel<T extends BaseModel> extends BaseModel<T> {
    private String title;
    private String content;
    private String type;// text,json...

    public ExampleModel(T higherLevel) {
        super(higherLevel);
    }

    public void init(String title, String content, String type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

}
