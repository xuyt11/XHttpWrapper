package cn.ytxu.api_semi_auto_creater.parser.request.restful_url;

import cn.ytxu.api_semi_auto_creater.model.request.restful_url.RESTfulUrlModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/8/14.
 */
public class RESTfulUrlParser {

    private RESTfulUrlModel model;

    public RESTfulUrlParser(RESTfulUrlModel model) {
        this.model = model;
    }

    public void start() {
        setIsRESTfulUrl();
    }

    private void setIsRESTfulUrl() {
        Pattern idOrDatePattern = Pattern.compile("[\\{]{1}.{2,}?[\\}]{1}");
        Matcher m = idOrDatePattern.matcher(model.getUrl());
        model.setRESTfulUrl(m.find());
    }


    private enum FieldType {
        id("id型的：{id}、{feedback_id}、{recommend_id}..."),
        multi("多选型的：[ios|android|web]"),
        date("时间型的：{YYYY-MM-DD}");

        private String tag;

        FieldType(String tag) {
            this.tag = tag;
        }
    }

}
