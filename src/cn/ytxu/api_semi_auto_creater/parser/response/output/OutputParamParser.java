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
        parseValueAndValuesOfOutputsThenParseSubsIfCan(outputs);
        response.setOutputs(outputs);
    }


    //********************** parse output of response **********************
    private List<OutputParamModel> getOutputsOfResponse(Collection<Map.Entry<String, Object>> entrys) {
        List<OutputParamModel> outputs = new ArrayList<>(entrys.size());
        for (Map.Entry<String, Object> entry : entrys) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            OutputParamType type = OutputParamType.get(fieldValue);
            OutputParamModel output = type.createOutput(response, null, fieldName, fieldValue);
            outputs.add(output);
        }
        return outputs;
    }

    public List<OutputParamModel> getOutputs(Collection<Map.Entry<String, Object>> entrys, OutputParamModel parent) {
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


    //********************** loop parse outputs and its subs **********************
    private void parseValueAndValuesOfOutputsThenParseSubsIfCan(List<OutputParamModel> outputs) {
        List<OutputParamModel> allSubsOfOuputs = parseValueAndValuesOfOutputsAndReturnAllSubsOfOutputs(outputs);
        if (isNeedParseSubs(allSubsOfOuputs)) {
            parseValueAndValuesOfOutputsThenParseSubsIfCan(allSubsOfOuputs);
        }
    }

    private List<OutputParamModel> parseValueAndValuesOfOutputsAndReturnAllSubsOfOutputs(List<OutputParamModel> outputs) {
        List<OutputParamModel> subsOfOutputs = new ArrayList<>();
        for (OutputParamModel output : outputs) {
            List<OutputParamModel> subsOfOutput = parseValueAndValuesOfOutputAndReturnSubsOfOutput(output);
            if (isNeedAdd(subsOfOutput)) {
                subsOfOutputs.addAll(subsOfOutput);
            }
        }
        return subsOfOutputs;
    }

    private List<OutputParamModel> parseValueAndValuesOfOutputAndReturnSubsOfOutput(OutputParamModel output) {
        output.getType().parseValueAndValuesIfCan(this, output);
        return output.getSubs();
    }

    private boolean isNeedAdd(List<OutputParamModel> subsOfOutput) {
        return subsOfOutput.size() > 0;
    }

    private boolean isNeedParseSubs(List<OutputParamModel> allSubsOfOuputs) {
        return allSubsOfOuputs.size() > 0;
    }


    //********************** parse value and values of output **********************
    public void parseValueAndValuesForObjectTypeOutput(OutputParamModel output) {
        parseValueForObjectTypeOutput(output);
        parseValuesForObjectTypeOutput(output);
    }

    private void parseValueForObjectTypeOutput(OutputParamModel output) {
        JSONObject jObj = (JSONObject) output.getValue();
        Set<Map.Entry<String, Object>> entrys = jObj.entrySet();
        List<OutputParamModel> outputs = getOutputs(entrys, output);
        output.setSubs(outputs);
    }

    private void parseValuesForObjectTypeOutput(OutputParamModel output) {
        List<Object> values = output.getValues();
        for (Object value : values) {
            if (Objects.isNull(value)) {
                continue;
            }
            parseOneOfValuesForObjectType(output, (JSONObject) value);
        }
    }

    private void parseOneOfValuesForObjectType(OutputParamModel output, JSONObject value) {
        Set<Map.Entry<String, Object>> entrys = value.entrySet();
        List<OutputParamModel> outputs = getOutputs(entrys, output);
        // TODO 过滤后才能添加到subs中
    }

    public void parseValueAndValuesForArrayTypeOutput(OutputParamModel output) {
        parseValueForArrayTypeOutput(output);
        parseValuesForArrayTypeOutput(output);
    }

    private void parseValueForArrayTypeOutput(OutputParamModel output) {
        JSONArray jArr = (JSONArray) output.getValue();
        if (jArr.size() == 0) {
            output.setSubType(OutputParamType.NULL);
            return;
        }

        OutputParamType subType = OutputParamType.get(jArr.get(0));
        output.setSubType(subType);
        // 只有是JSONObject类型才能解析，其他的都不需要解析的；
        // tip:并且不能是JSONArray类型，这个类型我不解析；即：JSONArray中不能包含JSONArray，这种的数据结构，我不解析
        subType.parseValueOfArrayType(this, output);
    }

    private void parseValuesForArrayTypeOutput(OutputParamModel output) {
        // TODO 解析values
    }
}
