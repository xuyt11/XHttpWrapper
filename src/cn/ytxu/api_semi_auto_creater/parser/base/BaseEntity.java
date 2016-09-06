package cn.ytxu.api_semi_auto_creater.parser.base;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 16/5/3.
 */
public class BaseEntity<HigherLevelEntity extends BaseEntity> {

    private HigherLevelEntity higherLevel;// 上一级对象
    private Element element;// 解析出对象的html element

    public BaseEntity(HigherLevelEntity higherLevel, Element element) {
        super();
        this.higherLevel = higherLevel;
        this.element = element;
    }

    public HigherLevelEntity getHigherLevel() {
        return higherLevel;
    }

    public Element getElement() {
        return element;
    }

}
