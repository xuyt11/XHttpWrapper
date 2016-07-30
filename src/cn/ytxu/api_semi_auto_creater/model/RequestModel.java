package cn.ytxu.api_semi_auto_creater.model;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/7/20.
 */
public class RequestModel extends BaseModel<SectionModel> {
    private String name;// 请求名称
    private String version;

    public RequestModel(SectionModel higherLevel, Element element, String name, String version) {
        super(higherLevel, element);
        this.name = name;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }
}
