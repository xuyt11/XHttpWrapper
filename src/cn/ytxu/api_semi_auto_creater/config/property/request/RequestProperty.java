package cn.ytxu.api_semi_auto_creater.config.property.request;

import java.util.List;

/**
 * Created by ytxu on 2016/9/5.
 */
public class RequestProperty {

    private static RequestProperty instance;

    private RequestBean requestBean;

    public static RequestProperty getInstance() {
        return instance;
    }

    public static void load(RequestBean request) {
        instance = new RequestProperty(request);
    }

    private RequestProperty(RequestBean request) {
        this.requestBean = request;
    }

    public List<String> getMultis() {
        return requestBean.getRESTful().getMulti_replace();
    }

    public List<DateReplaceBean> getDateReplaces() {
        return requestBean.getRESTful().getDate_replace();
    }
}
