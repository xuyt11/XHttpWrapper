package cn.ytxu.xhttp_wrapper.model.request.restful_url;

import cn.ytxu.xhttp_wrapper.model.BaseModel;

import java.text.DecimalFormat;

/**
 * Created by ytxu on 2016/8/14.
 */
public class RESTfulParamModel extends BaseModel<RESTfulUrlModel, Void> {

    private final String param;// 在url或multiUrl中的字符串
    private final String realParam;// 在代码中实际的字符串
    private final int start, end;// param 在url或multiUrl中的范围(range), 在转换请求url时，替换的范围

    public RESTfulParamModel(RESTfulUrlModel higherLevel, String param, String realParam, int start, int end) {
        super(higherLevel);
        this.param = param;
        this.realParam = realParam;
        this.start = start;
        this.end = end;
    }

    public String getParam() {
        return param;
    }

    public String getRealParam() {
        return realParam;
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
