package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class OutputParamModel extends BaseModel<ResponseModel> {

    private OutputParamModel parent;// 输出参数的父级，tip：若是为null,则higherLevel一定有值
    private List<OutputParamModel> subs;// 若是对象或数组类型,则有字段

    public OutputParamModel(ResponseModel higherLevel, Element element) {
        super(higherLevel, element);
    }

}
