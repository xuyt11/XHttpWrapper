package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.restful_url;

import cn.ytxu.xhttp_wrapper.config.property.request.DateReplaceBean;
import cn.ytxu.xhttp_wrapper.config.property.request.RequestProperty;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.request.restful_url.RESTfulParamModel;
import cn.ytxu.xhttp_wrapper.model.request.restful_url.RESTfulUrlModel;

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

    public RESTfulUrlParser(RequestModel request) {
        model = new RESTfulUrlModel(request, request.getUrl());
    }

    public RESTfulUrlModel start() {
        setIsRESTfulUrl();
        parseMultiUrl();
        parseIdOrDateParam();
        return model;
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
            multiUrl = getMultiUrl(multiUrl, group);
        } while (m.find());
        model.setMultiUrl(multiUrl);
    }

    private String getMultiUrl(String multiUrl, String group) {
        List<String> multis = RequestProperty.getInstance().getMultis();
        for (String multi : multis) {
            if (group.contains(multi)) {
                return multiUrl.replace(group, multi);// 直接替换requestUrl，不需要进行参数的添加等的处理
            }
        }
        throw new NotFoundTargetMultiUrlReplaceContentException(group);
    }

    private static class NotFoundTargetMultiUrlReplaceContentException extends RuntimeException {
        public NotFoundTargetMultiUrlReplaceContentException(String group) {
            super("you must add this multi replace content in .json config file, and this multi pattern group is " + group);
        }
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

        String restfulParam = getRestfulParam(group);
        if (restfulParam.contains("-") || restfulParam.contains(":") || restfulParam.contains(" ")) {
            throw new RuntimeException("the RESTful request url is" + model.getUrl() +
                    ", and the restfulParam is " + restfulParam +
                    ", and ytxu need parse this param, so i throw exception...");
        }
        return new RESTfulParamModel(model, restfulParam, start, end);
    }

    private String getRestfulParam(String group) {
        String restfulParam = group.substring(1, group.length() - 1);
        List<DateReplaceBean> dateReplaces = RequestProperty.getInstance().getDateReplaces();
        for (DateReplaceBean dateReplace : dateReplaces) {
            if (dateReplace.getDate_format().equals(restfulParam)) {
                restfulParam = dateReplace.getDate_request_param();
            }
        }
        return restfulParam;
    }

}
