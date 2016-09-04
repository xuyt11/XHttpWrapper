package cn.ytxu.api_semi_auto_creater.model.base;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.status_code.StatusCodeCategoryModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class VersionModel extends BaseModel<DocModel> {
    /** 无版本号模式时的model */
    public static final VersionModel NON_VERSION_MODEL = new VersionModel(null, null);

    private String name;// 版本名称
    private List<SectionModel> sections;
    private List<StatusCodeCategoryModel> statusCodes = Collections.EMPTY_LIST;

    public VersionModel(DocModel higherLevel, String name) {
        super(higherLevel, null);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<SectionModel> getSections() {
        return sections;
    }

    public void setSections(List<SectionModel> sections) {
        this.sections = sections;
    }

    public void setStatusCodes(List<StatusCodeCategoryModel> statusCodes) {
        this.statusCodes = statusCodes;
    }

    public List<StatusCodeCategoryModel> getStatusCodes() {
        return statusCodes;
    }

    //*************** reflect method area ***************
    public String version_code() {
        return name.replace(".", "_");
    }

    public List sections() {
        return sections;
    }

}
