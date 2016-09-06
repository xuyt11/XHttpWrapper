package cn.ytxu.api_semi_auto_creater.parser.base.entity;

import org.jsoup.nodes.Element;

import java.util.List;

public class DocEntity extends BaseEntity {
    private List<String> versions;// API所有的版本号
    private List<SectionEntity> sections;

    public DocEntity(BaseEntity higherLevel, Element element) {
        super(higherLevel, element);
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public List<SectionEntity> getSections() {
        return sections;
    }

    public void setSections(List<SectionEntity> sections) {
        this.sections = sections;
    }

}