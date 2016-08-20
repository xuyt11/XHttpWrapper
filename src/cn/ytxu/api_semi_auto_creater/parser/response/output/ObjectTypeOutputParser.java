package cn.ytxu.api_semi_auto_creater.parser.response.output;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by ytxu on 2016/8/20.
 * 对象类型的输出参数的解析器
 */
public class ObjectTypeOutputParser {

    private OutputParamParser parser;
    private OutputParamModel output;

    public ObjectTypeOutputParser(OutputParamParser parser, OutputParamModel output) {
        this.parser = parser;
        this.output = output;
    }

    public void start() {
        parseValue();
        parseValues();
    }

    private void parseValue() {
        JSONObject jObj = (JSONObject) output.getValue();
        Set<Map.Entry<String, Object>> entrys = jObj.entrySet();
        List<OutputParamModel> outputs = parser.getOutputs(entrys, output);
        output.setSubs(outputs);
    }

    private void parseValues() {
        List<Object> values = output.getValues();
        for (Object value : values) {
            if (Objects.isNull(value)) {
                continue;
            }
            parseOneOfValues((JSONObject) value);
        }
    }

    private void parseOneOfValues(JSONObject value) {
        Set<Map.Entry<String, Object>> entrys = value.entrySet();
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
            addModel(model);
        }
    }

    private void addModel(OutputParamModel model) {
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
