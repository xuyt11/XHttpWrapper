package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 16/5/3.
 */
public class BaseEntity<HigherLevelEntity extends BaseEntity> {

    private HigherLevelEntity higherLevel;// 上一级对象
    private Element element;// 解析出对象的html element

    public HigherLevelEntity getHigherLevel() {
        return higherLevel;
    }

    public void setHigherLevel(HigherLevelEntity higherLevel) {
        this.higherLevel = higherLevel;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    @Deprecated
    protected static <P extends BaseEntity> void setHigherLevel(List<P> thats, BaseEntity higherLevel) {
        if (thats == null || thats.size() <= 0) {
            return;
        }

        if (higherLevel == null) {
            return;
        }

        for (P that : thats) {
            that.setHigherLevel(higherLevel);
        }

    }


}
