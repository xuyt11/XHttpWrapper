package cn.ytxu.api_semi_auto_creater.model.status_code;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;

/**
 * Created by ytxu on 2016/8/30.
 */
public class StatusCodeModel extends BaseModel<StatusCodeCategoryModel> {
    private final String name;
    private final String value;
    private final String desc;

    public StatusCodeModel(StatusCodeCategoryModel higherLevel, String name, String value, String desc) {
        super(higherLevel, null);
        this.name = name;
        this.value = value;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
