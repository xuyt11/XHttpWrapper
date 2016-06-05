package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create;

import cn.ytxu.apacer.entity.OutputParamEntity;
import com.alibaba.fastjson.JSONArray;

/**
 * Created by ytxu on 2016/5/29.
 */
public class NumberOutputParamCreater implements OutputParamCreater {
    @Override
    public OutputParamEntity getOutputParam4JSONObject(String fieldName, Object fieldValue) {
        return new OutputParamEntity(fieldName, "Number", fieldValue.toString());
    }

    @Override
    public OutputParamEntity getOutputParam4JSONArray(OutputParamEntity entity, String fieldName, JSONArray fieldValue) {
        entity.setType("Number");
        return entity;
    }
}