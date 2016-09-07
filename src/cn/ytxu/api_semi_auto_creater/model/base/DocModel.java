package cn.ytxu.api_semi_auto_creater.model.base;

import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.BaseResponseEntityNameProperty;
import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.ResponseBean;
import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class DocModel extends BaseModel {
    private List<VersionModel> versions;
    private List<OutputParamModel> subsOfErrors;// base response 中所有的error内的字段

    public DocModel(Element element) {
        super(null, element);
    }

    public List<VersionModel> getVersions() {
        return versions;
    }

    public void setVersions(List<VersionModel> versions) {
        this.versions = versions;
    }

    public List<OutputParamModel> getSubsOfErrors() {
        return subsOfErrors;
    }

    public void setSubsOfErrors(List<OutputParamModel> subsOfErrors) {
        this.subsOfErrors = subsOfErrors;
    }


    //*************** get list data area ***************
    public List<ResponseModel> getResponses(boolean filter) {
        List<ResponseModel> responses = new ArrayList<>();
        for (RequestModel request : getRequests(filter)) {
            responses.addAll(request.getResponses());
        }
        return responses;
    }

    public List<RequestModel> getRequests(boolean filter) {
        List<RequestModel> requests = new ArrayList<>();
        for (SectionModel section : getSections(filter)) {
            requests.addAll(section.getRequests());
        }
        return requests;
    }

    public List<SectionModel> getSections(boolean filter) {
        List<SectionModel> sections = new ArrayList<>();
        for (VersionModel version : getVersionsAfterFilter(filter)) {
            sections.addAll(version.getSections());
        }
        return sections;
    }

    public List<VersionModel> getVersionsAfterFilter(boolean filter) {
        if (filter) {
            return Property.getFilterProperty().getVersionsAfterFilter(this);
        } else {
            return getVersions();
        }
    }


    //*************** reflect method area ***************
    public List<ResponseBean.BaseResponseParamBean> base_response_outputs() {
        return BaseResponseEntityNameProperty.get().getAll();
    }

    public String error_bro_type() {
        return BaseResponseEntityNameProperty.get().getErrorType();
    }

    public List<OutputParamModel> subs_of_errors() {
        return subsOfErrors;
    }

}
