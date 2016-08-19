package cn.ytxu.api_semi_auto_creater.model.response;

import cn.ytxu.api_semi_auto_creater.model.BaseModel;
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

    private String fieldName;
    private Object fieldValue;
    private List<Object> values = Collections.EMPTY_LIST;// 只有object与array，才会有的
    private List<OutputParamModel> subs = Collections.EMPTY_LIST;

    public OutputParamModel(ResponseModel higherLevel, OutputParamModel parent, OutputParamType type) {
        super(higherLevel, null);
        this.parent = parent;
        this.type = type;
    }

    public void setNameAndValue(String fieldName, Object fieldValue) {
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

    public List<OutputParamModel> addOutputsAfterFilter(List<OutputParamModel> models) {
        if (subs.size() == 0) {
            subs = models;
            return models;
        }

        List<OutputParamModel> filtedOutputs = new ArrayList<>();
        for (OutputParamModel model : models) {
            try {
                OutputParamModel target = findSameNameItemFromOutputsByModel(model);
                boolean needAdd = target.getType().replaceOutputOrAddValue(subs, target, model);
                if (needAdd) {
                    filtedOutputs.add(model);
                }
            } catch (NotFoundSameNameItemException ignore) {
                subs.add(model);
                filtedOutputs.add(model);
            }
        }
        return filtedOutputs;
    }

    private OutputParamModel findSameNameItemFromOutputsByModel(OutputParamModel model) throws NotFoundSameNameItemException {
        for (OutputParamModel output : subs) {
            if (output.getName().equals(model.getName())) {
                return output;
            }
        }
        throw new NotFoundSameNameItemException("");
    }

    public void addValue(Object value) {
        if (values == Collections.EMPTY_LIST) {
            values = new ArrayList<>();
        }
        values.add(value);
    }


    private static class NotFoundSameNameItemException extends Exception {
        public NotFoundSameNameItemException(String message) {
            super(message);
        }
    }
}
