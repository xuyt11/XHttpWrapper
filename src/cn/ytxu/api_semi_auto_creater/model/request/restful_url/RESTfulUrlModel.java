package cn.ytxu.api_semi_auto_creater.model.request.restful_url;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class RESTfulUrlModel extends BaseModel<RequestModel> {
    private String url;// 方法的相对路径，起始位置必须不是/,因为人
    private boolean isRESTfulUrl = false;
    private boolean hasMultiParam;// 是否有多选类型的参数，若有的话，则使用url是需要使用multiUrl
    private String multiUrl;
    private List<RESTfulParamModel> params = Collections.EMPTY_LIST;

    public RESTfulUrlModel(RequestModel higherLevel, String url) {
        super(higherLevel, null);
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public boolean isRESTfulUrl() {
        return isRESTfulUrl;
    }

    public void setRESTfulUrl(boolean isRESTfulUrl) {
        this.isRESTfulUrl = isRESTfulUrl;
    }

    public boolean hasMultiParam() {
        return hasMultiParam;
    }

    public void setHasMultiParam(boolean hasMultiParam) {
        this.hasMultiParam = hasMultiParam;
    }

    public String getMultiUrl() {
        return multiUrl;
    }

    public void setMultiUrl(String multiUrl) {
        this.multiUrl = multiUrl;
    }

    public void setParams(List<RESTfulParamModel> params) {
        this.params = params;
    }

    public List<RESTfulParamModel> getParams() {
        return params;
    }

}
