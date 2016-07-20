package cn.ytxu.api_semi_auto_creater.model;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 */
public class DocModel extends BaseModel {
    private List<VersionModel> versions;
//    private List<?> statusCodes;// 所有的状态码

    public DocModel(BaseModel higherLevel, Element element) {
        super(higherLevel, element);
    }


}
