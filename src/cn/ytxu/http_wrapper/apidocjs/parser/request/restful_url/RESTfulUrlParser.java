package cn.ytxu.http_wrapper.apidocjs.parser.request.restful_url;

import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.config.property.request.DateReplaceBean;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.request.restful_url.RESTfulParamModel;
import cn.ytxu.http_wrapper.model.request.restful_url.RESTfulUrlModel;

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
            multiUrl = getMultiUrl(multiUrl, group);
        } while (m.find());
        model.setMultiUrl(multiUrl);
    }

    private String getMultiUrl(String multiUrl, String group) {
        List<String> multis = ConfigWrapper.getRequest().getMultis();
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
        int paramIndex = 0;
        while (m.find()) {
            getResTfulParamModel(m, paramIndex);
            paramIndex++;
        }
    }

    private RESTfulParamModel getResTfulParamModel(Matcher m, int paramIndex) {
        int start = m.start();
        int end = m.end();
        String group = m.group();

        String restfulParam = getRestfulParam(group);
        if (restfulParam.contains("-") || restfulParam.contains(":") || restfulParam.contains(" ")) {
            throw new RuntimeException("the RESTful request url is " + model.getUrl() +
                    "\n, and the restfulParam is " + restfulParam +
                    "\n, and ytxu need parse this param, so i throw exception...");
        }
        return new RESTfulParamModel(model, group, restfulParam, paramIndex, start, end);
    }

    private String getRestfulParam(String group) {
        String restfulParam = group.substring(1, group.length() - 1);
        List<DateReplaceBean> dateReplaces = ConfigWrapper.getRequest().getDateReplaces();
        for (DateReplaceBean dateReplace : dateReplaces) {
            if (dateReplace.getDate_format().equals(restfulParam)) {
                restfulParam = dateReplace.getDate_request_param();
                break;
            }
        }
        return restfulParam;
    }

}
