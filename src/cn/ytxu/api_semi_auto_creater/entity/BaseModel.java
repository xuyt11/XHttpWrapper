package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 16/5/3.
 */
public class BaseModel<HigherLevelModel extends BaseModel> {

    private HigherLevelModel higherLevel;// 上一级对象
    private Element element;// 解析出对象的html element

    public BaseModel(HigherLevelModel higherLevel, Element element) {
        super();
        this.higherLevel = higherLevel;
        this.element = element;
    }

    public HigherLevelModel getHigherLevel() {
        return higherLevel;
    }

    public Element getElement() {
        return element;
    }

}
