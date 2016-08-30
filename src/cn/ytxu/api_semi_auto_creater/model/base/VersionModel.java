package cn.ytxu.api_semi_auto_creater.model.base;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;

import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class VersionModel extends BaseModel<DocModel> {
    private String name;// 版本名称
    private List<SectionModel> sections;
    private List<StatusCodeModel> statusCodes;// TODO

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

    public void setStatusCodes(List<StatusCodeModel> statusCodes) {
        this.statusCodes = statusCodes;
    }

    //*************** reflect method area ***************
    public String version_code() {
        return name.replace(".", "_");
    }

    public List sections() {
        return sections;
    }

}
