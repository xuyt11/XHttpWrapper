package cn.ytxu.api_semi_auto_creater.parser.base.entity;

import org.jsoup.nodes.Element;

public class RequestEntity extends BaseEntity<SectionEntity> {
    private String name;
    private String version;

    public RequestEntity(SectionEntity higherLevel, Element element) {
        super(higherLevel, element);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}