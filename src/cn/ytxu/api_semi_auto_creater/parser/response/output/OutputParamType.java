package cn.ytxu.api_semi_auto_creater.parser.response.output;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Collections;
import java.util.List;

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
            return parser.parseJSONObject(output);
        }
    },
    JSON_ARRAY(JSONArray.class) {
        @Override
        public List<OutputParamModel> parseSubsIfCan(OutputParamParser parser, OutputParamModel output) {
            return parser.parseArray(output);
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
     * @return 是否需要解析output参数中的outputs
     */
    public List<OutputParamModel> parseSubsIfCan(OutputParamParser parser, OutputParamModel output) {
        return Collections.EMPTY_LIST;
    }
}
