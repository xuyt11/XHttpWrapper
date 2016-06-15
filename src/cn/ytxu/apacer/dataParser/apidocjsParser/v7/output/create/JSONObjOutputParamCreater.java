package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create;

import cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.OutputParamsParser;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.OutputParamEntity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Created by ytxu on 2016/5/29.
 */
public class JSONObjOutputParamCreater implements OutputParamCreater {
    private List<FieldEntity> descParams;

    public JSONObjOutputParamCreater(List<FieldEntity> descParams) {
        this.descParams = descParams;
    }

    @Override
    public OutputParamEntity getOutputParam4JSONObject(String fieldName, Object fieldValue) {
        OutputParamEntity entity = createrObjectType(fieldName, fieldValue.toString());
        List<OutputParamEntity> subs = OutputParamsParser.parseEntrysToOutputParams((JSONObject) fieldValue, descParams);
        entity.setSubs(subs);
        return entity;
    }

    private static OutputParamEntity createrObjectType(String name, String desc) {
        // 对象类型,直接使用isObject进行判断,不需要用type字段进行判断
        OutputParamEntity entity = new OutputParamEntity(name, null, desc);
        entity.setObject(true);
        return entity;
    }

    @Override
    public OutputParamEntity getOutputParam4JSONArray(OutputParamEntity entity, String fieldName, JSONArray fieldValue) {
        Object obj = fieldValue.get(0);
        List<OutputParamEntity> subs = OutputParamsParser.parseEntrysToOutputParams((JSONObject) obj, descParams);
        entity.setSubs(subs);
        entity.setType("Object");
        return entity;
    }
}
