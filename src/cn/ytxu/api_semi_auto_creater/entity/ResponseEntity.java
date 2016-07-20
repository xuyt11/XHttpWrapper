package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/6/16.
 */
public class ResponseEntity extends BaseEntity<RequestEntity> {

    private OutputParamEntity output;

    private Element responseDescEle;// 该请求响应的描述
    private Element responseEle;// 请求响应报文的数据:响应头,响应体

    public ResponseEntity(RequestEntity higherLevel, Element responseDescEle, Element responseEle) {
        super(higherLevel, null);
        this.responseDescEle = responseDescEle;
        this.responseEle = responseEle;
    }




}
