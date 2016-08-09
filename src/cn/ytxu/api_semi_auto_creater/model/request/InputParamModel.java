package cn.ytxu.api_semi_auto_creater.model.request;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/6/16.
 */
public class InputParamModel extends BaseModel<RequestModel> {

    public InputParamModel(RequestModel higherLevel, Element element) {
        super(higherLevel, element);
    }

}
