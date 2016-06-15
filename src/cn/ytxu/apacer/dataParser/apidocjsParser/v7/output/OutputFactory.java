package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output;

import cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create.*;
import cn.ytxu.apacer.entity.FieldEntity;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by ytxu on 2016/5/29.
 */
public class OutputFactory {

    public static OutputParamCreater getOutputParamCreater(Object obj, List<FieldEntity> descParams) {
        OutputType outputType = OutputType.getOutputType(obj);
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
            case Unknown:
            default:
                return new UnknowOutputParamCreater(descParams);
        }
    }

    public static OutputParamCreater getOutputParamCreater(JSONArray jsonArr, List<FieldEntity> descParams) {
        if (jsonArr.size() <= 0) {
            return new UnknowOutputParamCreater(descParams);
        }

        Object obj = jsonArr.get(0);
        return getOutputParamCreater(obj, descParams);
    }
}
