package cn.ytxu.api_semi_auto_creater.model.response;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.parser.response.output.OutputParamType;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/8/17.
 */
public class OutputParamModel extends BaseModel<ResponseModel> {

    private final OutputParamModel parent;
    private final OutputParamType type;

    private String fieldName;
    private Object fieldValue;
    private List<OutputParamModel> outputs = Collections.EMPTY_LIST;

    public OutputParamModel(ResponseModel higherLevel, OutputParamModel parent, OutputParamType type) {
        super(higherLevel, null);
        this.parent = parent;
        this.type = type;
    }

    public void setNameAndValue(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public void setOutputs(List<OutputParamModel> outputs) {
        this.outputs = outputs;
    }

    /**
     * 是否为响应下面的根参数，还是根参数下面的参数
     */
    public boolean isResponseRootParam() {
        return Objects.isNull(parent);
    }

    public Object getValue() {
        return fieldValue;
    }

    public OutputParamType getType() {
        return type;
    }
}
