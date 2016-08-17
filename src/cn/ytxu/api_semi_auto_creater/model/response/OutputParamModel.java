package cn.ytxu.api_semi_auto_creater.model.response;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.parser.response.output.OutputParamType;

import java.util.Objects;

/**
 * Created by ytxu on 2016/8/17.
 */
public class OutputParamModel extends BaseModel<ResponseModel> {

    private final OutputParamModel parent;
    private final OutputParamType type;

    private String fieldName;
    private Object fieldValue;

    public OutputParamModel(ResponseModel higherLevel, OutputParamType type) {
        super(higherLevel, null);
        this.parent = null;
        this.type = type;
    }

    public OutputParamModel(OutputParamModel parent, OutputParamType type) {
        super(null, null);
        this.parent = parent;
        this.type = type;
    }

    public void setNameAndValue(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * 是否为响应下面的根参数，还是根参数下面的参数
     */
    public boolean isResponseRootParam() {
        return Objects.nonNull(getHigherLevel());
    }

}
