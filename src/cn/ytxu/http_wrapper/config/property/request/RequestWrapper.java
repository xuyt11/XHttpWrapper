package cn.ytxu.http_wrapper.config.property.request;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.util.TextUtil;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/9/5.
 */
public class RequestWrapper {

    private static RequestWrapper instance;

    private RequestBean requestBean;

    public static RequestWrapper getInstance() {
        return instance;
    }

    public static void load(RequestBean request) {
        LogUtil.i(RequestWrapper.class, "load request property start...");
        instance = new RequestWrapper(request);
        LogUtil.i(RequestWrapper.class, "load request property success...");
    }

    private RequestWrapper(RequestBean request) {
        this.requestBean = request;

        if (Objects.isNull(request.getRESTful())) {
            throw new IllegalArgumentException("u must setup RESTful property...");
        }
        if (TextUtil.isBlank(request.getRESTful().getReplaceString())) {
            throw new IllegalArgumentException("the RESTful-->replaceString property is null, u must setup...");
        }
    }

    public String getReplaceString() {
        return requestBean.getRESTful().getReplaceString();
    }

    public List<String> getMultis() {
        return requestBean.getRESTful().getMultiReplaces();
    }

    public List<DateReplaceBean> getDateReplaces() {
        return requestBean.getRESTful().getDateReplaces();
    }
}
