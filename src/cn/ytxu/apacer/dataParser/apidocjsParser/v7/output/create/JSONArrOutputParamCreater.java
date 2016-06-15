package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create;

import cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.OutputFactory;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.OutputParamEntity;
import cn.ytxu.util.LogUtil;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * Created by ytxu on 2016/5/29.
 */
public class JSONArrOutputParamCreater implements OutputParamCreater {
    private List<FieldEntity> descParams;

    public JSONArrOutputParamCreater(List<FieldEntity> descParams) {
        this.descParams = descParams;
    }

    @Override
    public OutputParamEntity getOutputParam4JSONObject(String fieldName, Object fieldValue) {
        OutputParamEntity entity = parseJSONArrayToOutputParam(fieldName, (JSONArray) fieldValue);
        return entity;
    }

    /**
     * 分析Json数组(JsonArray): 若数组中只是int或String类型的要进行反设置其类型,否则其类型是一个自定一个实体类
     */
    private OutputParamEntity parseJSONArrayToOutputParam(String fieldName, JSONArray fieldValue) {
        LogUtil.i("parseJSONArrayToOutputParam fieldName:" + fieldName + ", fieldValue:" + fieldValue);
        OutputParamEntity entity = createrArrayType(fieldName, fieldValue.toString());
        OutputParamCreater outputCreater = OutputFactory.getOutputParamCreater(fieldName, fieldValue, descParams);
        OutputParamEntity output = outputCreater.getOutputParam4JSONArray(entity, fieldName, fieldValue);
        return output;
    }

    private OutputParamEntity createrArrayType(String name, String desc) {
        OutputParamEntity entity = new OutputParamEntity(name, null, desc);
        entity.setArray(true);
        return entity;
    }

    @Override
    public OutputParamEntity getOutputParam4JSONArray(OutputParamEntity entity, String fieldName, JSONArray fieldValue) {
        throw new RuntimeException("can not call it...");
    }
}
