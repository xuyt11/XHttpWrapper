package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * API文档实体类
 * Created by ytxu on 2016/6/16.
 * 只有一个状态码类: 状态码不分版本;也就是说,状态码是不能更改的,只能是新增;
 */
public class DocumentEntity extends BaseEntity {
    // TODO 设置version entity enum，所以以后每新加一个版本，都要进行添加，查找不到该版本号，直接抛出异常
    private List<String> versions;// API所有的版本号
    private List<StatusCodeEntity> statusCodes;// 所有的状态码
    private List<SectionEntity> sections;

    public DocumentEntity(BaseEntity higherLevel, Element element) {
        super(higherLevel, element);
    }
    // get common response and its error entity base had parse all response content;

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public List<StatusCodeEntity> getStatusCodes() {
        return statusCodes;
    }

    public void setStatusCodes(List<StatusCodeEntity> statusCodes) {
        this.statusCodes = statusCodes;
    }

    public List<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(List<SectionEntity> sections) {
        this.sections = sections;
    }

}
