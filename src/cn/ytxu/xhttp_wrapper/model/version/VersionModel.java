package cn.ytxu.xhttp_wrapper.model.version;

import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.config.property.response.BaseResponseParamBean;
import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.response.OutputParamModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016-9-18
 */
public class VersionModel extends BaseModel {

    private String name;// 版本名称
    private List<StatusCodeGroupModel> statusCodeGroups = Collections.EMPTY_LIST;
    private List<RequestGroupModel> requestGroups = Collections.EMPTY_LIST;
    private List<OutputParamModel> subsOfErrors;// base response 中所有的error内的字段

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

    public List<RequestModel> getRequests() {
        List<RequestModel> requests = new ArrayList<>();
        requestGroups.forEach(requestGroup -> requests.addAll(requestGroup.getRequests()));
        return requests;
    }

    public void setSubsOfErrors(List<OutputParamModel> subsOfErrors) {
        this.subsOfErrors = subsOfErrors;
    }


    //*************** get list data area ***************
    public static List<ResponseModel> getResponses(List<VersionModel> versions) {
        List<ResponseModel> responses = new ArrayList<>();
        for (RequestModel request : getRequests(versions)) {
            responses.addAll(request.getSuccessContainer().getResponses());
        }
        return responses;
    }

    public static List<RequestModel> getRequests(List<VersionModel> versions) {
        List<RequestModel> requests = new ArrayList<>();
        for (RequestGroupModel section : getSections(versions)) {
            requests.addAll(section.getRequests());
        }
        return requests;
    }

    public static List<RequestGroupModel> getSections(List<VersionModel> versions) {
        return ConfigWrapper.getFilter().getRequestGroupsAfterFilted(versions);
    }

    public static List<VersionModel> getVersions(List<VersionModel> versions) {
        return ConfigWrapper.getFilter().getVersionsAfterFilted(versions);
    }

    //*************** reflect method area ***************
    public String version_code() {
        return name.replace(".", "_");
    }

    public List sections() {
        return requestGroups;
    }

    public List<BaseResponseParamBean> base_response_outputs() {
        return ConfigWrapper.getResponse().getAll();
    }

    public String error_bro_type() {
        return ConfigWrapper.getResponse().getErrorType();
    }

    public List<OutputParamModel> subs_of_errors() {
        return subsOfErrors;
    }

}