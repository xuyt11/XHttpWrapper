package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create;

import cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.OutputParamsParser;
import cn.ytxu.apacer.entity.OutputParamEntity;
import com.alibaba.fastjson.JSONArray;

/**
 * Created by ytxu on 2016/5/29.
 */
public class JSONArrayOutputParamCreater implements IOutputParamCreater {
    @Override
    public OutputParamEntity getOutputParam(String fieldName, Object fieldValue) {
        OutputParamEntity entity = OutputParamsParser.parseJSONArrayToOutputParam(fieldName, (JSONArray) fieldValue);
        return entity;
    }
}
