package cn.ytxu.api_semi_auto_creater.model;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/7/20.
 */
public class VersionModel extends BaseModel<DocModel> {

    public VersionModel(DocModel higherLevel, Element element) {
        super(higherLevel, element);
    }


}
