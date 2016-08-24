package cn.ytxu.api_semi_auto_creater.model.response;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import org.jsoup.nodes.Element;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/8/15.
 */
public class ResponseModel extends BaseModel<RequestModel> {

    private Element descEle, messageEle;// 响应描述，响应报文
    private String desc;// 响应描述文本
    private String header;// 响应报文中的头部
    private String body;// 响应报文中的响应体

    // body中的字段数据
    private String statusCode = "";// 防止出现空指针异常
    private List<OutputParamModel> outputs = Collections.EMPTY_LIST;

    public ResponseModel(RequestModel higherLevel, Element descEle, Element messageEle) {
        super(higherLevel, null);
        this.descEle = descEle;
        this.messageEle = messageEle;
    }

    public void setData(String desc, String header, String body) {
        this.desc = desc;
        this.header = header;
        this.body = body;
    }

    public String getDesc() {
        return desc;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setOutputs(List<OutputParamModel> outputs) {
        this.outputs = outputs;
    }

    public List<OutputParamModel> getOutputs() {
        return outputs;
    }


}
