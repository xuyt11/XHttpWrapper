package cn.ytxu.apacer.entity;

/**
 * Created by ytxu on 16/5/3.
 */
public class BaseEntity<T> {

    protected T higherLevel;// 上一级对象

    public T getHigherLevel() {
        return higherLevel;
    }

    public void setHigherLevel(T higherLevel) {
        this.higherLevel = higherLevel;
    }
}
