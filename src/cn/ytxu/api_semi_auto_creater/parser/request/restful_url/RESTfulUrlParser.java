package cn.ytxu.api_semi_auto_creater.parser.request.restful_url;

import cn.ytxu.api_semi_auto_creater.model.request.restful_url.RESTfulParamModel;
import cn.ytxu.api_semi_auto_creater.model.request.restful_url.RESTfulUrlModel;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ytxu on 2016/8/14.
 */
public class RESTfulUrlParser {
    private static final Pattern ID_OR_DATE_PATTERN = Pattern.compile("[\\{]{1}.{2,}?[\\}]{1}");
    private static final Pattern MULTI_PATTERN = Pattern.compile("[\\[]{1}.{2,}?[\\]]{1}");

    private RESTfulUrlModel model;

    public RESTfulUrlParser(RESTfulUrlModel model) {
        this.model = model;
    }

    public void start() {
        setIsRESTfulUrl();
        parseMultiUrl();
        parseIdOrDateParam();
    }

    private void setIsRESTfulUrl() {
        Matcher m = ID_OR_DATE_PATTERN.matcher(model.getUrl());
        model.setRESTfulUrl(m.find());
    }

    private void parseMultiUrl() {
        Matcher m = MULTI_PATTERN.matcher(model.getUrl());
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

    private void parseIdOrDateParam() {
        if (!model.isRESTfulUrl()) {// no heed parse
            return;
        }

        String url = model.hasMultiParam() ? model.getMultiUrl() : model.getUrl();
        Matcher m = ID_OR_DATE_PATTERN.matcher(url);
        List<RESTfulParamModel> params = new ArrayList<>();
        while (m.find()) {
            RESTfulParamModel paramModel = getResTfulParamModel(m);
            params.add(paramModel);
        }
        model.setParams(params);
    }

    private RESTfulParamModel getResTfulParamModel(Matcher m) {
        int start = m.start();
        int end = m.end();
        String group = m.group();

        String restfulParam = group.substring(1, group.length() - 1);
        if (restfulParam.contains("-") || restfulParam.contains(":") || restfulParam.contains(" ")) {
            if ("YYYY-MM-DD".equals(restfulParam)) {
                restfulParam = "restfulDateParam" + "/** " + restfulParam + " */ ";
            } else {
                throw new RuntimeException("the RESTful request url is" + model.getUrl() +
                        ", and the restfulParam is " + restfulParam +
                        ", and ytxu need parse this param, so i throw exception...");
            }
        }
        return new RESTfulParamModel(model, restfulParam, start, end);
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
