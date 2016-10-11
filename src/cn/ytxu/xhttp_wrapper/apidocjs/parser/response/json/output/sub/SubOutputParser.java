package cn.ytxu.xhttp_wrapper.apidocjs.parser.response.json.output.sub;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.json.output.OutputParamParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.json.output.OutputParamType;
import cn.ytxu.xhttp_wrapper.model.response.OutputParamModel;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 解析JSONObject与JSONArray子output
 */
public class SubOutputParser {
    private OutputParamParser parser;
    private OutputParamModel output;
    private JSONObject valueOfJSONObjectType;

    public SubOutputParser(OutputParamParser parser, OutputParamModel output, JSONObject valueOfJSONObjectType) {
        this.parser = parser;
        this.output = output;
        this.valueOfJSONObjectType = valueOfJSONObjectType;
    }

    public void parse() {
        Set<Map.Entry<String, Object>> entrys = valueOfJSONObjectType.entrySet();
        List<OutputParamModel> outputs = parser.getOutputs(entrys, output);
        if (isNotNeedFilter()) {
            output.setSubs(outputs);
            return;
        }
        addOutputsAfterFilter(outputs);
    }

    private boolean isNotNeedFilter() {
        return output.getSubs().size() == 0;
    }

    public void addOutputsAfterFilter(List<OutputParamModel> models) {
        for (OutputParamModel model : models) {
            addOutput2SubsOfOutput(model);
        }
    }

    private void addOutput2SubsOfOutput(OutputParamModel model) {
        List<OutputParamModel> subs = output.getSubs();
        try {
            OutputParamModel target = findSameNameItemByModel(model);
            OutputParamType targetType = target.getType();
            targetType.replaceOutputIfIsNULLOrAddModelSValue2TargetSValuesIfIsObjectOrArrayOtherwiseDoNothing(subs, target, model);
        } catch (NotFoundSameNameItemException ignore) {
            subs.add(model);
        }
    }

    private OutputParamModel findSameNameItemByModel(OutputParamModel model) throws NotFoundSameNameItemException {
        List<OutputParamModel> subs = output.getSubs();
        for (OutputParamModel sub : subs) {
            if (sub.getName().equals(model.getName())) {
                return sub;
            }
        }
        throw new NotFoundSameNameItemException("");
    }

    private static class NotFoundSameNameItemException extends Exception {
        public NotFoundSameNameItemException(String message) {
            super(message);
        }
    }

}