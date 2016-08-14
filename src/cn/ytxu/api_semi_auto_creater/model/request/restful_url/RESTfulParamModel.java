package cn.ytxu.api_semi_auto_creater.model.request.restful_url;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;

/**
 * Created by ytxu on 2016/8/14.
 */
public class RESTfulParamModel extends BaseModel<RESTfulUrlModel> {

    private String param;
    private int start, end;// range 在转换请求url时，替换的范围

    public RESTfulParamModel(RESTfulUrlModel higherLevel, String restfulParam, int start, int end) {
        super(higherLevel, null);
        this.param = restfulParam;
        this.start = start;
        this.end = end;
    }

    public String getParam() {
        return param;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }


    //*************** reflect method area ***************
    public String RESTful_field_name() {
        return param;
    }

}
