package cn.ytxu.api_semi_auto_creater.config.property.request;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class RESTfulBean {

    /** tip: 多分类选择的参数：根据顺序查找替换
     * 我现阶段只有匹配不同平台的接口的参数 */
    private List<String> multi_replace = Collections.EMPTY_LIST;

    private List<DateReplaceBean> date_replace = Collections.EMPTY_LIST;

    public List<String> getMulti_replace() {
        return multi_replace;
    }

    public void setMulti_replace(List<String> multi_replace) {
        this.multi_replace = multi_replace;
    }

    public List<DateReplaceBean> getDate_replace() {
        return date_replace;
    }

    public void setDate_replace(List<DateReplaceBean> date_replace) {
        this.date_replace = date_replace;
    }
}
