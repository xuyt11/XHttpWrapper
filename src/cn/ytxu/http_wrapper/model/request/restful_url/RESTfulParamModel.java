package cn.ytxu.http_wrapper.model.request.restful_url;

import cn.ytxu.http_wrapper.model.BaseModel;

import java.text.DecimalFormat;

/**
 * Created by ytxu on 2016/8/14.
 */
public class RESTfulParamModel extends BaseModel<RESTfulUrlModel> {

    private final String param;// 在url或multiUrl中的字符串
    private final String realParam;// 在代码中实际的字符串
    private final int paramIndex;// 在url或multiUrl中所有param的index
    private final int start, end;// param 在url或multiUrl中的范围(range), 在转换请求url时，替换的范围

    public RESTfulParamModel(RESTfulUrlModel higherLevel, String param, String realParam, int paramIndex, int start, int end) {
        super(higherLevel);
        this.param = param;
        this.realParam = realParam;
        this.paramIndex = paramIndex;
        this.start = start;
        this.end = end;
        higherLevel.addParam(this);
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
        int indexOfParams = paramIndex;// getHigherLevel().getParams().indexOf(this);
        String formatIndex = new DecimalFormat("00").format(indexOfParams);
        return realParam + formatIndex;
    }

}
