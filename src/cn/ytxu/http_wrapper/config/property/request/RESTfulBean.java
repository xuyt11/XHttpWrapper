package cn.ytxu.http_wrapper.config.property.request;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/9/5.
 * 替换URL上的替代符的配置
 */
public class RESTfulBean {
    /**
     * 在url上替换的字符串，主要是使用在string replace上
     * 例如：若replaceString="%s"，则URL:api/project_id/{project_id}/, 会替换为api/project_id/%s/
     */
    private String replaceString;
    /**
     * tip: 多分类选择的参数：根据顺序查找替换
     * 例如：若multi_replace = android，在URL：api/project/[android|ios|html]/中，
     * 则会替换为：api/project/android/
     * 若有多个替换规则可以匹配到，只会替换第一个匹配；
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
