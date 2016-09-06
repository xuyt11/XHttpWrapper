package cn.ytxu.api_semi_auto_creater.parser.base.entity;

import org.jsoup.nodes.Element;

import java.util.List;

public class SectionEntity extends BaseEntity<DocEntity> {
    private String name;
    private List<RequestEntity> requests;

    public SectionEntity(DocEntity higherLevel, Element element, String name) {
        super(higherLevel, element);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<RequestEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestEntity> requests) {
        this.requests = requests;
    }

    public static SectionEntity copy(SectionEntity section) {
        SectionEntity newSection = new SectionEntity(section.getHigherLevel(), section.getElement(), section.getName());
        return newSection;
    }
}