package cn.ytxu.api_semi_auto_creater.config.property.status_code;

import cn.ytxu.api_semi_auto_creater.model.base.StatusCodeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/9/2.
 */
public class StatusCodeProperty {
    private static StatusCodeProperty instance;

    private StatusCodeBean statusCodeBean;

    public static StatusCodeProperty getInstance() {
        return instance;
    }

    public static void load(StatusCodeBean statusCode) {
        instance = new StatusCodeProperty(statusCode);
    }

    private StatusCodeProperty(StatusCodeBean statusCode) {
        this.statusCodeBean = statusCode;
    }

    public String getSectionName4StatusCode() {
        return statusCodeBean.getSection_name();
    }

    public List<StatusCodeModel> getStatusCodes(List<StatusCodeModel> statusCodes) {
        if (!isUseVersionFilter()) {
            return statusCodes;
        }
        return getFiltedStatusCodes(statusCodes);
    }

    private boolean isUseVersionFilter() {
        return statusCodeBean.isUse_version_filter();
    }

    private List<StatusCodeModel> getFiltedStatusCodes(List<StatusCodeModel> statusCodes) {
        List<StatusCodeModel> filtedStatusCodes = new ArrayList<>();
        for (StatusCodeModel statusCode : statusCodes) {
            if (isOutputVersion(statusCode)) {
                filtedStatusCodes.add(statusCode);
            }
        }
        return filtedStatusCodes;
    }

    private boolean isOutputVersion(StatusCodeModel statusCode) {
        String version = statusCode.getHigherLevel().getName();
        for (String outputVersion : statusCodeBean.getFilted_versions()) {
            if (outputVersion.equals(version)) {
                return true;
            }
        }
        return false;
    }

}
