package cn.ytxu.api_semi_auto_creater.model.base;

import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.BaseResponseEntityNameProperty;
import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.ResponseBean;
import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import org.jsoup.nodes.Element;

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
