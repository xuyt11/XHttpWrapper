package cn.ytxu.api_semi_auto_creater.model;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class SectionModel extends BaseModel<VersionModel> {
    private String name;
    private List<RequestModel> requests;

    public SectionModel(VersionModel higherLevel, Element element, String name) {
        super(higherLevel, element);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<RequestModel> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestModel> requests) {
        this.requests = requests;
    }
}
