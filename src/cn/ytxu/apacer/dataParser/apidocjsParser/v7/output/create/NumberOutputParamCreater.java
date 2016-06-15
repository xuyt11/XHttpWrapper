package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create;

import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.OutputParamEntity;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by ytxu on 2016/5/29.
 */
public class NumberOutputParamCreater implements OutputParamCreater {
    private List<FieldEntity> descParams;

    public NumberOutputParamCreater(List<FieldEntity> descParams) {
        this.descParams = descParams;
    }

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
