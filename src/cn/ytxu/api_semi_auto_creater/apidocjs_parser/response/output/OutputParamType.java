package cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by ytxu on 2016/8/17.
 */
public enum OutputParamType {
    // tip: 顺序是不能改动的，因为第一个是判空的类型，最后一个是确保为未知类型
    NULL(null) {
        @Override
        boolean isThisType(Object obj) {
            return obj == null;
        }

        @Override
        public void replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            int index = outputs.indexOf(target);
            outputs.set(index, model);
        }
    },
    INTEGER(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    NUMBER(Number.class),// FUTURE 未来将会删除掉的类型，这样的类型，不能知道精确类型
    BOOLEAN(Boolean.class),
    STRING(String.class),
    JSON_OBJECT(JSONObject.class) {
        @Override
        public void parseValueAndValuesIfCan(OutputParamParser parser, OutputParamModel output) {
            new ObjectTypeOutputParser(parser, output).start();
            return;
        }

        @Override
        public void replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            addValueIfNeed(target, model);
        }
    },
    //tip: JSONArray中不能包含JSONArray，这种的数据结构，我不解析
    JSON_ARRAY(JSONArray.class) {
        @Override
        public void parseValueAndValuesIfCan(OutputParamParser parser, OutputParamModel output) {
            new ArrayTypeOutputParser(parser, output).start();
        }

        @Override
        public void replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            addValueIfNeed(target, model);
        }
    },
    UNKNOWN(null) {
        @Override
        boolean isThisType(Object obj) {
            return true;
        }
    };

    private Class clazz;

    OutputParamType(Class clazz) {
        this.clazz = clazz;
    }

    public static OutputParamType get(Object obj) {
        for (OutputParamType type : OutputParamType.values()) {
            if (type.isThisType(obj)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    boolean isThisType(Object obj) {
        Class objType = obj.getClass();
        return objType == clazz;
    }

    public OutputParamModel createOutput(ResponseModel response, OutputParamModel parent, String fieldName, Object fieldValue) {
        return new OutputParamModel(response, parent, this, fieldName, fieldValue);
    }

    /**
     * @return output中的subs
     */
    public void parseValueAndValuesIfCan(OutputParamParser parser, OutputParamModel output) {
        return;
    }

    /**
     * tips:
     * if tagettype is NULL, replace it with model;
     * else if targetType is Object or Array, add model`s value to target`s values
     * otherwise, do nothing...
     */
    public void replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
    }

    protected void addValueIfNeed(OutputParamModel target, OutputParamModel model) {
        Object value = model.getValue();
        OutputParamType type = OutputParamType.get(value);
        if (type == NULL) {// model`s type is NULL, so do nothing...
            return;
        }
        if (type != this) {// model`s type is not same for target`s type
            throw new IllegalStateException("the value type is not match" +
                    "\nin request " + model.getHigherLevel().getHigherLevel().getName() +
                    "\n and the output name is " + model.getName() + ", its previous type is " + this.name() + ", but curr type is " + type.name());
        }
        target.addValue(value);
        return;
    }
}
