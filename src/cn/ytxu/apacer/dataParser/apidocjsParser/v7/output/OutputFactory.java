package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output;

import cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create.*;
import com.alibaba.fastjson.JSONArray;

/**
 * Created by ytxu on 2016/5/29.
 */
public class OutputFactory {

    public static OutputParamCreater getOutputParamCreater(Object obj) {
        OutputType outputType = OutputType.getOutputType(obj);
        switch (outputType) {
            case String:
                return new StringOutputParamCreater();
            case Number:
                return new NumberOutputParamCreater();
            case Integer:
                return new IntegerOutputParamCreater();
            case Long:
                return new LongOutputParamCreater();
            case Boolean:
                return new BooleanOutputParamCreater();
            case JSONObject:
                return new JSONObjOutputParamCreater();
            case JSONArray:
                return new JSONArrOutputParamCreater();
            case Unknown:
            default:
                return new UnknowOutputParamCreater();
        }
    }

    public static OutputParamCreater getOutputParamCreater(JSONArray jsonArr) {
        if (jsonArr.size() <= 0) {
            return new UnknowOutputParamCreater();
        }

        Object obj = jsonArr.get(0);
        return getOutputParamCreater(obj);
    }
}
