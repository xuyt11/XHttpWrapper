package cn.ytxu.http_wrapper.config.property.request;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class RESTfulBean {
    /**
     * 在url上替换的字符串，主要是使用在string replace上
     */
    private String replaceString;
    /**
     * tip: 多分类选择的参数：根据顺序查找替换
     * 我现阶段只有匹配不同平台的接口的参数
     */
    private List<String> multi_replace = Collections.EMPTY_LIST;

    private List<DateReplaceBean> date_replace = Collections.EMPTY_LIST;


    public String getReplaceString() {
        return replaceString;
    }

    public List<String> getMultiReplaces() {
        return multi_replace;
    }

    public List<DateReplaceBean> getDateReplaces() {
        return date_replace;
    }

    public void setReplaceString(String replaceString) {
        this.replaceString = replaceString;
    }

    public void setMulti_replace(List<String> multi_replace) {
        this.multi_replace = multi_replace;
    }

    public void setDate_replace(List<DateReplaceBean> date_replace) {
        this.date_replace = date_replace;
    }
}
