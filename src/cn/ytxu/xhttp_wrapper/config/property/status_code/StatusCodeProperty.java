package cn.ytxu.xhttp_wrapper.config.property.status_code;

import cn.ytxu.xhttp_wrapper.model.VersionModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;

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

    public List<StatusCodeGroupModel> getStatusCodeGroups(List<VersionModel> versions) {
        if (!isUseVersionFilter()) {
            return getAllStatusCodeGroups(versions);
        }
        return getFiltedStatusCodeGroups(versions);
    }

    private List<StatusCodeGroupModel> getAllStatusCodeGroups(List<VersionModel> versions) {
        List<StatusCodeGroupModel> statusCodeGroups = new ArrayList<>();
        for (VersionModel versionModel : versions) {
            List<StatusCodeGroupModel> vStatusCodeGroups = versionModel.getStatusCodeGroups();
            if (vStatusCodeGroups.size() > 0) {
                statusCodeGroups.addAll(vStatusCodeGroups);
            }
        }
        return statusCodeGroups;
    }

    private boolean isUseVersionFilter() {
        return statusCodeBean.isUse_version_filter();
    }

    private List<StatusCodeGroupModel> getFiltedStatusCodeGroups(List<VersionModel> versions) {
        List<StatusCodeGroupModel> filtedStatusCodeGroups = new ArrayList<>();
        for (StatusCodeGroupModel statusCodeGroup : getAllStatusCodeGroups(versions)) {
            if (isOutputVersion(statusCodeGroup)) {
                filtedStatusCodeGroups.add(statusCodeGroup);
            }
        }
        return filtedStatusCodeGroups;
    }

    private boolean isOutputVersion(StatusCodeGroupModel statusCode) {
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
