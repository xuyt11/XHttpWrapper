package cn.ytxu.api_semi_auto_creater.model.response;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.parser.response.output.OutputParamType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/8/17.
 */
public class OutputParamModel extends BaseModel<ResponseModel> {

    private final OutputParamModel parent;
    private final OutputParamType type;
    private OutputParamType subType;// 只有array才有，如：List<Integer>,List<Long>,List<String>...
    private DefinedParamModel defined;
    private boolean dontRequireGenerationResponseEntityFileTag = false;// 是否需要生成响应实体文件标记，默认为需要

    private final String fieldName;
    private final Object fieldValue;
    private List<Object> values = Collections.EMPTY_LIST;// 只有object与array，才会有的
    private List<OutputParamModel> subs = Collections.EMPTY_LIST;

    public OutputParamModel(ResponseModel higherLevel, OutputParamModel parent, OutputParamType type,
                            String fieldName, Object fieldValue) {
        super(higherLevel, null);
        this.parent = parent;
        this.type = type;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public void setSubs(List<OutputParamModel> subs) {
        this.subs = subs;
    }

    public List<OutputParamModel> getSubs() {
        return subs;
    }

    /**
     * 是否为响应下面的根参数，还是根参数下面的参数
     */
    public boolean isResponseRootParam() {
        return Objects.isNull(parent);
    }


    public String getName() {
        return fieldName;
    }

    public void setDefined(DefinedParamModel defined) {
        this.defined = defined;
    }

    public DefinedParamModel getDefined() {
        return defined;
    }

    public Object getValue() {
        return fieldValue;
    }

    public List<Object> getValues() {
        return values;
    }

    public OutputParamType getType() {
        return type;
    }

    public void setSubType(OutputParamType subType) {
        this.subType = subType;
    }

    public OutputParamType getSubType() {
        return subType;
    }

    public void addValue(Object value) {
        if (values == Collections.EMPTY_LIST) {
            values = new ArrayList<>();
        }
        values.add(value);
    }

    public void setDontRequireGenerationResponseEntityFileTag() {
        dontRequireGenerationResponseEntityFileTag = true;
    }

    public boolean isDontRequireGenerationResponseEntityFileTag() {
        return dontRequireGenerationResponseEntityFileTag;
    }
}
