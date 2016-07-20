package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/6/16.
 */
public class InputParamModel extends BaseModel<RequestModel> {

    public InputParamModel(RequestModel higherLevel, Element element) {
        super(higherLevel, element);
    }

}
