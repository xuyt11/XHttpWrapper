package cn.ytxu.api_semi_auto_creater.parser.response.output;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * Created by ytxu on 2016/8/17.
 */
public class OutputParamParser {

    private ResponseModel response;
    private JSONObject bodyJObj;

    public OutputParamParser(ResponseModel response, JSONObject bodyJObj) {
        this.response = response;
        this.bodyJObj = bodyJObj;
    }

    public void start() {
        Set<Map.Entry<String, Object>> entrys = bodyJObj.entrySet();
        List<OutputParamModel> outputs = getOutputsOfResponse(entrys);
        // TODO 需要在output中获取outputs循环遍历，知道所有的outputs都没有JSONObject,JSONArray了
        // 判断依据是当前是否需要解析outputs,若需要，则需要解析子outputs
        parseSubsOfOutputs(outputs);
        response.setOutputs(outputs);
    }

    private List<OutputParamModel> getOutputsOfResponse(Collection<Map.Entry<String, Object>> entrys) {
        return getOutputs(entrys, null);
    }

    private List<OutputParamModel> getOutputs(Collection<Map.Entry<String, Object>> entrys, OutputParamModel parent) {
        List<OutputParamModel> outputs = new ArrayList<>(entrys.size());
        for (Map.Entry<String, Object> entry : entrys) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            OutputParamType type = OutputParamType.get(fieldValue);
            OutputParamModel output = type.createOutput(response, parent, fieldName, fieldValue);
            outputs.add(output);
        }
        return outputs;
    }

    private void parseSubsOfOutputs(List<OutputParamModel> outputs) {
        List<OutputParamModel> subOutputs = new ArrayList<>();
        for (OutputParamModel output : outputs) {
            List<OutputParamModel> models = output.getType().parseSubsIfCan(this, output);
            if (models.size() > 0) {
                subOutputs.addAll(models);
            }
        }

        if (subOutputs.size() > 0) {
            parseSubsOfOutputs(subOutputs);
        }
    }

    public List<OutputParamModel> parseJSONObject(OutputParamModel output) {
        JSONObject jObj = (JSONObject) output.getValue();
        Set<Map.Entry<String, Object>> entrys = jObj.entrySet();
        List<OutputParamModel> outputs = getOutputs(entrys, output);
        output.setOutputs(outputs);
        return outputs;
    }

    public List<OutputParamModel> parseArray(OutputParamModel output) {
        JSONArray jArr = (JSONArray) output.getValue();

        // TODO 需要去好好想想，如何做
        for (int i = 0, size = jArr.size(); i < size; i++) {
            List<OutputParamModel> outputs = parsebject(jArr.get(i), output);
        }

        output.setOutputs(outputs);
    }

    private List<OutputParamModel> parsebject(Object obj, OutputParamModel output) {

    }


}
