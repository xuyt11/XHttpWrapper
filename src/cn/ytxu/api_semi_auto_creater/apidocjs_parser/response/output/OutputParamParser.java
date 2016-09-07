package cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
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


}
