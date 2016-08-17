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
        public List<OutputParamModel> parseSubsIfCan(OutputParamParser parser, OutputParamModel output) {
            JSONObject jObj = (JSONObject) output.getValue();
            Set<Map.Entry<String, Object>> entrys = jObj.entrySet();
            List<OutputParamModel> outputs = parser.getOutputs(entrys, output);
            output.setOutputs(outputs);
            return outputs;
        }

        @Override
        protected List<OutputParamModel> parseListTypeOutput(OutputParamParser parser, OutputParamModel output) {
            JSONArray jArr = (JSONArray) output.getValue();
            List<OutputParamModel> outputs = new ArrayList<>();
            for (int i = 0, size = jArr.size(); i < size; i++) {
                JSONObject jObj = jArr.getJSONObject(i);
                Set<Map.Entry<String, Object>> entrys = jObj.entrySet();
                List<OutputParamModel> models = parser.getOutputs(entrys, output);
                // TODO 需要好好考虑如何过滤
                output.setOutputs(models);
                outputs.addAll(models);
            }
            return outputs;
        }
    },
    JSON_ARRAY(JSONArray.class) {
        @Override
        public List<OutputParamModel> parseSubsIfCan(OutputParamParser parser, OutputParamModel output) {
            JSONArray jArr = (JSONArray) output.getValue();

            int size = jArr.size();
            if (size == 0) {
                output.setSubType(OutputParamType.NULL);
                return Collections.EMPTY_LIST;
            }

            OutputParamType subType = OutputParamType.get(jArr.get(0));
            output.setSubType(subType);
            // 只有是JSONObject类型才能解析，其他的都不需要解析的；
            return subType.parseListTypeOutput(parser, output);
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
    public List<OutputParamModel> parseSubsIfCan(OutputParamParser parser, OutputParamModel output) {
        return Collections.EMPTY_LIST;
    }

    /**
     * @return output中的subs 默认是不需要解析的
     */
    protected List<OutputParamModel> parseListTypeOutput(OutputParamParser parser, OutputParamModel output) {
        return Collections.EMPTY_LIST;
    }

}
