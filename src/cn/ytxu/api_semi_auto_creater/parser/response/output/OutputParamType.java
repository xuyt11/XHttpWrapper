package cn.ytxu.api_semi_auto_creater.parser.response.output;

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
        public boolean replaceOutputOrAddValue(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            int index = outputs.indexOf(target);
            outputs.set(index, model);
            return true;
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
            parser.parseValueAndValuesOfObjectType(output);
            JSONObject jObj = (JSONObject) output.getValue();
            Set<Map.Entry<String, Object>> entrys = jObj.entrySet();
            List<OutputParamModel> outputs = parser.getOutputs(entrys, output);
            output.setSubs(outputs);
            // 解析values，生成outputs，再与output中的outputs进行对比过滤，将有效的数据添加到outputs中
            parseValuesThenAdd2OutputsAfterFilter(parser, output);
            return outputs;
        }

        private void parseValuesThenAdd2OutputsAfterFilter(OutputParamParser parser, OutputParamModel output) {
            List<Object> values = output.getValues();
            for (Object val : values) {
                JSONObject vJObj = (JSONObject) val;
                Set<Map.Entry<String, Object>> vEntrys = vJObj.entrySet();
                List<OutputParamModel> models = parser.getOutputs(vEntrys, output);
                output.addOutputsAfterFilter(models);
            }
        }

        @Override
        protected List<OutputParamModel> parseListTypeOutput(OutputParamParser parser, OutputParamModel output) {
            JSONArray jArr = (JSONArray) output.getValue();
            List<OutputParamModel> outputs = new ArrayList<>();
            for (int i = 0, size = jArr.size(); i < size; i++) {
                JSONObject jObj = jArr.getJSONObject(i);
                Set<Map.Entry<String, Object>> entrys = jObj.entrySet();
                List<OutputParamModel> models = parser.getOutputs(entrys, output);
                List<OutputParamModel> filterModels = output.addOutputsAfterFilter(models);
                if (filterModels.size() > 0) {
                    outputs.addAll(filterModels);
                }
            }
            return outputs;
        }

        @Override
        public boolean replaceOutputOrAddValue(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            return addValueIfNeed(target, model);
        }
    },
    JSON_ARRAY(JSONArray.class) {
        @Override
        public void parseValueAndValuesIfCan(OutputParamParser parser, OutputParamModel output) {
            parser.parseValueAndValuesOfArrayType(output);

            JSONArray jArr = (JSONArray) output.getValue();
            int size = jArr.size();
            if (size == 0) {
                output.setSubType(OutputParamType.NULL);
                return Collections.EMPTY_LIST;
            }

            OutputParamType subType = OutputParamType.get(jArr.get(0));
            output.setSubType(subType);
            // 只有是JSONObject类型才能解析，其他的都不需要解析的；
            // tip:并且不能是JSONArray类型，这个类型我不解析；即：JSONArray中不能包含JSONArray，这种的数据结构，我不解析
            return subType.parseListTypeOutput(parser, output);
            // TODO 解析values
        }

        @Override
        protected List<OutputParamModel> parseListTypeOutput(OutputParamParser parser, OutputParamModel output) {
            throw new IllegalStateException("JSONArray中不能包含JSONArray，这种的数据结构，我不解析");
        }

        @Override
        public boolean replaceOutputOrAddValue(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
            return addValueIfNeed(target, model);
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

    boolean isThisType(Object obj) {
        Class objType = obj.getClass();
        return objType == clazz;
    }

    public static OutputParamType get(Object obj) {
        for (OutputParamType type : OutputParamType.values()) {
            if (type.isThisType(obj)) {
                return type;
            }
        }
        return UNKNOWN;
    }

    public OutputParamModel createOutput(ResponseModel response, OutputParamModel parent, String fieldName, Object fieldValue) {
        OutputParamModel output = new OutputParamModel(response, parent, this);
        output.setNameAndValue(fieldName, fieldValue);
        return output;
    }

    /**
     * @return output中的subs
     */
    public void parseValueAndValuesIfCan(OutputParamParser parser, OutputParamModel output) {
        return;
    }

    /**
     * @return output中的subs 默认是不需要解析的
     */
    protected List<OutputParamModel> parseListTypeOutput(OutputParamParser parser, OutputParamModel output) {
        return Collections.EMPTY_LIST;
    }

    public boolean replaceOutputOrAddValue(List<OutputParamModel> outputs, OutputParamModel target, OutputParamModel model) {
        return false;
    }

    protected boolean addValueIfNeed(OutputParamModel target, OutputParamModel model) {
        Object value = model.getValue();
        OutputParamType type = OutputParamType.get(value);
        if (type == NULL) {
            return false;
        }
        if (type != this) {
            throw new IllegalStateException("the value type is not match" +
                    "\nin request " + model.getHigherLevel().getHigherLevel().getName() +
                    "\n and the output name is " + model.getName() + ", its previous type is " + this.name() + ", but curr type is " + type.name());
        }
        target.addValue(value);
        return false;
    }
}
