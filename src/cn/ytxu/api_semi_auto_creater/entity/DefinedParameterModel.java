package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;


/**
 * Created by ytxu on 2016/6/16.
 * 已定义的参数：有描述信息，类型信息等
 */
public class DefinedParameterModel extends BaseModel<RequestModel> {
    private String paramCategoryName;

    private String name;// 字段名称
    private String type;// 字段的类型
    private boolean isOptional = false;// 是否为可选字段
    private String description;// 字段描述

//    private boolean isList = false;// 是否为数组类型：默认为不是数组类型
//    private List<FieldEntity> subs;// 子字段集合

    public DefinedParameterModel(RequestModel higherLevel, Element element, String paramCategoryName) {
        super(higherLevel, element);
        this.paramCategoryName = paramCategoryName;
    }

    public String getParamCategoryName() {
        return paramCategoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isOptional() {
        return isOptional;
    }

    public void setOptional(boolean optional) {
        isOptional = optional;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
