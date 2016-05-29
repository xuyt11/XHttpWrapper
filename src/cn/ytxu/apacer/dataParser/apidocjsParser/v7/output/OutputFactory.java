package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output;

import cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create.*;

/**
 * Created by ytxu on 2016/5/29.
 */
public class OutputFactory {

    public static IOutputParamCreater getOutputParamCreater(Object obj) {
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
                return new JSONObjectOutputParamCreater();
            case JSONArray:
                return new JSONArrayOutputParamCreater();
            case Unknown:
            default:
                return new UnknowOutputParamCreater();
        }
    }
}
