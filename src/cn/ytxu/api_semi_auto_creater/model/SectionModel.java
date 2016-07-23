package cn.ytxu.api_semi_auto_creater.model;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class SectionModel extends BaseModel<VersionModel> {
    private List<RequestModel> requests;

    public SectionModel(VersionModel higherLevel, Element element) {
        super(higherLevel, element);
    }


}
