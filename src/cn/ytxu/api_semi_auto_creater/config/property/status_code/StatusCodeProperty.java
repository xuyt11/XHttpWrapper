package cn.ytxu.api_semi_auto_creater.config.property.status_code;

import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;

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

    public List<SectionModel> getStatusCodes(DocModel docModel) {
        if (!isUseVersionFilter()) {
            return getAllStatusCodes(docModel);
        }
        return getFiltedStatusCodes(docModel);
    }

    private List<SectionModel> getAllStatusCodes(DocModel docModel) {
        List<SectionModel> statusCodes = new ArrayList<>();
        for (VersionModel versionModel : docModel.getVersions()) {
            statusCodes.add(versionModel.getStatusCode());
        }
        return statusCodes;
    }

    private boolean isUseVersionFilter() {
        return statusCodeBean.isUse_version_filter();
    }

    private List<SectionModel> getFiltedStatusCodes(DocModel docModel) {
        List<SectionModel> filtedStatusCodes = new ArrayList<>();
        for (SectionModel statusCode : getAllStatusCodes(docModel)) {
            if (isOutputVersion(statusCode)) {
                filtedStatusCodes.add(statusCode);
            }
        }
        return filtedStatusCodes;
    }

    private boolean isOutputVersion(SectionModel statusCode) {
        String version = statusCode.getHigherLevel().getName();
        for (String outputVersion : statusCodeBean.getFilted_versions()) {
            if (outputVersion.equals(version)) {
                return true;
            }
        }
        return false;
    }

}
