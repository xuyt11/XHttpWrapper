package cn.ytxu.api_semi_auto_creater.model;

/**
 * Created by ytxu on 2016/6/16.
 */
public class RESTfulUrlModel extends BaseModel<RequestModel> {
    private String url;// 方法的相对路径，起始位置必须不是/,因为人
    private boolean isRESTfulUrl = false;

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
}
