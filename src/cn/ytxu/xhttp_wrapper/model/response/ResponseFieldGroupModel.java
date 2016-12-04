package cn.ytxu.xhttp_wrapper.model.response;

import cn.ytxu.xhttp_wrapper.model.BaseModel;

/**
 * Created by Administrator on 2016/9/24.
 */
public class ResponseFieldGroupModel extends BaseModel<ResponseContainerModel, Void> {
    private final String name;

    public ResponseFieldGroupModel(ResponseContainerModel higherLevel, String name) {
        super(higherLevel);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
