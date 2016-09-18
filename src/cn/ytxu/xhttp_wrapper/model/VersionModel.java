package cn.ytxu.xhttp_wrapper.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016-9-18
 */
public class VersionModel extends BaseModel {
    /**
     * 无版本号模式时的model
     */
    public static final VersionModel NON_VERSION_MODEL = new VersionModel("non_version");

    private String name;// 版本名称
    private List<StatusCodeGroupModel> statusCodeGroups = Collections.EMPTY_LIST;
    private List<RequestGroupModel> requestGroups = Collections.EMPTY_LIST;

    public VersionModel(String name) {
        super(null, null);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addStatusCodeGroup(StatusCodeGroupModel statusCodeGroup) {
        if (statusCodeGroups == Collections.EMPTY_LIST) {
            statusCodeGroups = new ArrayList<>();
        }
        statusCodeGroups.add(statusCodeGroup);
    }

    public List<StatusCodeGroupModel> getStatusCodeGroups() {
        return statusCodeGroups;
    }

    public void addRequestGroup(RequestGroupModel requestGroup) {
        if (requestGroups == Collections.EMPTY_LIST) {
            requestGroups = new ArrayList<>(10);
        }
        requestGroups.add(requestGroup);
    }

    public List<RequestGroupModel> getRequestGroups() {
        return requestGroups;
    }


    //*************** reflect method area ***************
    public String version_code() {
        return name.replace(".", "_");
    }

    public List sections() {
        return requestGroups;
    }

}
