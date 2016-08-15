package cn.ytxu.api_semi_auto_creater.model.response;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/8/15.
 */
public class ResponseModel extends BaseModel<RequestModel> {

    private Element descEle, messageEle;// 响应描述，响应报文
    private String desc;
    private String header;
    private String content;

    public ResponseModel(RequestModel higherLevel, Element descEle, Element messageEle) {
        super(higherLevel, null);
        this.descEle = descEle;
        this.messageEle = messageEle;
    }

    public void setData(String desc, String header, String content) {
        this.desc = desc;
        this.header = header;
        this.content = content;
    }
}
