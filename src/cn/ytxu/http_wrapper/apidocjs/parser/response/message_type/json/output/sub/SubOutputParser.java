package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.OutputParamParser;
import cn.ytxu.http_wrapper.config.property.param_type.ParamTypeEnum;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * 解析JSONObject与JSONArray子output
 */
public class SubOutputParser {
    private final OutputParamParser parser;
    private final OutputParamModel output;
    private final JSONObject valueOfJSONObjectType;

    public SubOutputParser(OutputParamParser parser, OutputParamModel output, JSONObject valueOfJSONObjectType) {
        this.parser = parser;
        this.output = output;
        this.valueOfJSONObjectType = valueOfJSONObjectType;
    }

    public void parse() {
        List<OutputParamModel> outputs = parser.getOutputs(valueOfJSONObjectType, output);
        if (outputs.isEmpty()) {
            return;
        }
        if (isNotNeedFilter()) {// 第一次设置output的subs,所以直接set，不需要过滤
            output.setSubs(outputs);
            return;
        }
        addOutputsAfterFilter(outputs);
    }

    private boolean isNotNeedFilter() {
        return output.getSubs().isEmpty();
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
            ParamTypeEnum targetType = target.getType();
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
        throw new NotFoundSameNameItemException();
    }

    private static class NotFoundSameNameItemException extends Exception {
    }

}