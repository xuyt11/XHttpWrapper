package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output;

import cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create.*;
import cn.ytxu.apacer.entity.FieldEntity;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by ytxu on 2016/5/29.
 */
public class OutputFactory {

    public static OutputParamCreater getOutputParamCreater(String fieldName, Object fieldValue, List<FieldEntity> descParams) {
        OutputType outputType = OutputType.getOutputType(fieldValue);
        return get(outputType, descParams, fieldName);
    }

    private static OutputParamCreater get(OutputType outputType, List<FieldEntity> descParams, String fieldName) {
        try {
            return getOutputParamCreaterOtherwiseThrowException(outputType, descParams);
        } catch (Exception ignore) {
        }

        switch (outputType) {
            case Unknown:
            default:
                try {
                    return getOutputParamCreaterByTypeName(descParams, fieldName);
                } catch (Exception ignore) {
                    return new UnknowOutputParamCreater(descParams);
                }
        }
    }

    private static OutputParamCreater getOutputParamCreaterOtherwiseThrowException(OutputType outputType, List<FieldEntity> descParams) {
        switch (outputType) {
            case String:
                return new StringOutputParamCreater(descParams);
            case Number:
                return new NumberOutputParamCreater(descParams);
            case Integer:
                return new IntegerOutputParamCreater(descParams);
            case Long:
                return new LongOutputParamCreater(descParams);
            case Boolean:
                return new BooleanOutputParamCreater(descParams);
            case JSONObject:
                return new JSONObjOutputParamCreater(descParams);
            case JSONArray:
                return new JSONArrOutputParamCreater(descParams);
            default:
                throw new RuntimeException("can not find output type...");
        }
    }

    private static OutputParamCreater getOutputParamCreaterByTypeName(List<FieldEntity> descParams, String fieldName) {
        if (descParams != null && descParams.size() > 0) {
            for (FieldEntity descParam : descParams) {
                if (fieldName.equals(descParam.getKey())) {
                    OutputType type = OutputType.getOutputTypeByTypeName(descParam.getType());
                    return get(type, descParams);
                }
            }
        }
        throw new RuntimeException("can not find output type by type name...");
    }

    private static OutputParamCreater get(OutputType outputType, List<FieldEntity> descParams) {
        try {
            return getOutputParamCreaterOtherwiseThrowException(outputType, descParams);
        } catch (Exception ignore) {
        }

        switch (outputType) {
            case Unknown:
            default:
                return new UnknowOutputParamCreater(descParams);
        }
    }

    public static OutputParamCreater getOutputParamCreater(String fieldName, JSONArray jsonArr, List<FieldEntity> descParams) {
        if (jsonArr.size() <= 0) {
            return new UnknowOutputParamCreater(descParams);
        }

        Object obj = jsonArr.get(0);
        return getOutputParamCreater(fieldName, obj, descParams);
    }
}
