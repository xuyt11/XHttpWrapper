package cn.ytxu.http_wrapper.model;

/**
 * Created by ytxu on 16/5/3.
 */
public class BaseModel<HigherLevelModel extends BaseModel> {

    private HigherLevelModel higherLevel;// 上一级对象

    public BaseModel(HigherLevelModel higherLevel) {
        super();
        this.higherLevel = higherLevel;
    }

    public HigherLevelModel getHigherLevel() {
        return higherLevel;
    }

}
