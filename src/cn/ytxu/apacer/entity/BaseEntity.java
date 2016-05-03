package cn.ytxu.apacer.entity;

import java.util.List;

/**
 * Created by ytxu on 16/5/3.
 */
public class BaseEntity<T> {

    protected T higherLevel;// 上一级对象


    public T getHigherLevel() {
        return higherLevel;
    }

    protected static <P extends BaseEntity> void setHigherLevel(List<P> thats, BaseEntity higherLevel) {
        if (thats == null || thats.size() <= 0) {
            return;
        }

        if (higherLevel == null) {
            return;
        }

        for (P that : thats) {
            that.higherLevel = higherLevel;
        }

    }


}
