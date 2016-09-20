package cn.ytxu.xhttp_wrapper.config.property.status_code;

import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.model.status_code.StatusCodeCategoryModel;

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

    public boolean isStatusCodeGroup(String groupName) {
        return getSectionName4StatusCode().equals(groupName);
    }

    public List<StatusCodeCategoryModel> getStatusCodes(DocModel docModel, boolean filter) {
        if (!filter || !isUseVersionFilter()) {
            return getAllStatusCodes(docModel);
        }
        return getFiltedStatusCodes(docModel);
    }

    private List<StatusCodeCategoryModel> getAllStatusCodes(DocModel docModel) {
        List<StatusCodeCategoryModel> statusCodes = new ArrayList<>();
        for (VersionModel versionModel : docModel.getVersions()) {
            List<StatusCodeCategoryModel> vStatusCodes = versionModel.getStatusCodes();
            if (vStatusCodes.size() > 0) {
                statusCodes.addAll(vStatusCodes);
            }
        }
        return statusCodes;
    }

    private boolean isUseVersionFilter() {
        return statusCodeBean.isUse_version_filter();
    }

    private List<StatusCodeCategoryModel> getFiltedStatusCodes(DocModel docModel) {
        List<StatusCodeCategoryModel> filtedStatusCodes = new ArrayList<>();
        for (StatusCodeCategoryModel statusCode : getAllStatusCodes(docModel)) {
            if (isOutputVersion(statusCode)) {
                filtedStatusCodes.add(statusCode);
            }
        }
        return filtedStatusCodes;
    }

    private boolean isOutputVersion(StatusCodeCategoryModel statusCode) {
        String version = statusCode.getName();
        for (String outputVersion : statusCodeBean.getFilted_versions()) {
            if (outputVersion.equals(version)) {
                return true;
            }
        }
        return false;
    }

    public String getOkNumber() {
        return statusCodeBean.getOk_number();
    }

    public String getParseModelName() {
        return statusCodeBean.getParse_model();
    }

}
