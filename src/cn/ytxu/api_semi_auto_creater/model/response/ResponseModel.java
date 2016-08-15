package cn.ytxu.api_semi_auto_creater.model.response;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/8/15.
 */
public class ResponseModel extends BaseModel<RequestModel> {

    private Element descEle, messageEle;// 响应描述，响应报文
    private String responseDesc;
    private String responseHeader;
    private String responseContent;

    public ResponseModel(RequestModel higherLevel, Element descEle, Element messageEle) {
        super(higherLevel, null);
        this.descEle = descEle;
        this.messageEle = messageEle;
    }

    public void setData(String responseDesc, String responseHeader, String responseContent) {
        this.responseDesc = responseDesc;
        this.responseHeader = responseHeader;
        this.responseContent = responseContent;
    }
}
