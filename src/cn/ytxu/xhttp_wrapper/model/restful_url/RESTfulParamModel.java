package cn.ytxu.xhttp_wrapper.model.restful_url;

import cn.ytxu.xhttp_wrapper.model.BaseModel;

import java.text.DecimalFormat;

/**
 * Created by ytxu on 2016/8/14.
 */
public class RESTfulParamModel extends BaseModel<RESTfulUrlModel, Void> {

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
        int indexOfParams = getHigherLevel().getParams().indexOf(this);
        String formatIndex = new DecimalFormat("00").format(indexOfParams);
        return param + formatIndex;
    }

}
