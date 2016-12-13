package cn.ytxu.http_wrapper.config.property.status_code;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.common.enums.StatusCodeParseModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeGroupModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/9/2.
 */
public class StatusCodeWrapper {
    private static StatusCodeWrapper instance;

    private StatusCodeBean statusCodeBean;

    public static StatusCodeWrapper getInstance() {
        return instance;
    }

    public static void load(StatusCodeBean statusCode) {
        LogUtil.i(StatusCodeWrapper.class, "load status code property start...");
        instance = new StatusCodeWrapper(statusCode);
        LogUtil.i(StatusCodeWrapper.class, "load status code property success...");
    }

    private StatusCodeWrapper(StatusCodeBean statusCode) {
        this.statusCodeBean = statusCode;

        if (Objects.isNull(statusCode.getRequestGroupName())) {
            throw new IllegalArgumentException("u must setup request group property...");
        }
        StatusCodeParseModel.getByEnumName(statusCode.getParseModel());
        if (statusCode.isUseVersionFilter() && statusCode.getFiltedVersions().size() <= 0) {
            throw new IllegalArgumentException("u setup use filter versions function, but the filted_versions property don`t setup...");
        }
        try {
            Integer.getInteger(statusCode.getOkNumber());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("u setup ok_number property error...", e);
        }
    }

    public String getRequestGroupName4StatusCode() {
        return statusCodeBean.getRequestGroupName();
    }

    public boolean isStatusCodeGroup(String groupName) {
        return getRequestGroupName4StatusCode().equals(groupName);
    }

    public StatusCodeParseModel getParseModel() {
        return StatusCodeParseModel.getByEnumName(getParseModelName());
    }

    public String getParseModelName() {
        return statusCodeBean.getParseModel();
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
        return statusCodeBean.isUseVersionFilter();
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
        for (String outputVersion : statusCodeBean.getFiltedVersions()) {
            if (outputVersion.equals(version)) {
                return true;
            }
        }
        return false;
    }

    public String getOkNumber() {
        return statusCodeBean.getOkNumber();
    }

}
