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
        parseMultiUrl();
    }

    private void setIsRESTfulUrl() {
        Pattern idOrDatePattern = Pattern.compile("[\\{]{1}.{2,}?[\\}]{1}");
        Matcher m = idOrDatePattern.matcher(model.getUrl());
        model.setRESTfulUrl(m.find());
    }

    private void parseMultiUrl() {
        Pattern multiPattern = Pattern.compile("[\\[]{1}.{2,}?[\\]]{1}");
        Matcher m = multiPattern.matcher(model.getUrl());
        boolean hasMultiParam = m.find();
        model.setHasMultiParam(hasMultiParam);

        if (!hasMultiParam) {
            return;
        }

        String multiUrl = model.getUrl();
        do {
            String group = m.group();
            if (group.contains("android")) {// 现阶段只匹配不同平台的接口
                multiUrl = multiUrl.replace(group, "android");// 直接替换requestUrl，不需要进行参数的添加等的处理
            } else {// 直接抛出异常，在控制台中查看requestUrl、group，再去进行代码的添加
                throw new RuntimeException("you need fix it, this requestUrl is " + model.getUrl() + ", and the multi pattern group is " + group);
            }
        } while (m.find());
        model.setMultiUrl(multiUrl);
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
