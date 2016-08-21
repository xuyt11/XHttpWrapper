package cn.ytxu.api_semi_auto_creater.parser.response.output;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ytxu on 2016/8/21.
 * 数组类型的输出参数的解析器
 * tip: JSONArray中不能包含JSONArray，这种的数据结构，我不解析
 */
public class ArrayTypeOutputParser {

    private OutputParamParser parser;
    private OutputParamModel output;

    public ArrayTypeOutputParser(OutputParamParser parser, OutputParamModel output) {
        this.parser = parser;
        this.output = output;
    }

    public void start() {
        // 1 set sub type
        setSubType();
        // 2 parse outputs
        parseValue();
        parseValues();
    }

    private void setSubType() {
        if (setSubTypeByValueIfCan()) {
            return;
        }
        if (setSubTypeByValuesIfCan()) {
            return;
        }
        output.setSubType(OutputParamType.NULL);
    }

    private boolean setSubTypeByValueIfCan() {
        JSONArray jArr = (JSONArray) output.getValue();
        return setSubTypeIfCan(jArr);
    }

    private boolean setSubTypeByValuesIfCan() {
        List<Object> values = output.getValues();
        for (Object value : values) {
            if (setSubTypeIfCan((JSONArray) value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return has set sub type, and the value is equals can set sub type
     */
    private boolean setSubTypeIfCan(JSONArray jArr) {
        boolean canSetSubType = canSetSubType(jArr);
        if (canSetSubType) {
            OutputParamType subType = OutputParamType.get(jArr.get(0));
            output.setSubType(subType);
        }
        return canSetSubType;
    }

    private boolean canSetSubType(JSONArray jArr) {
        return jArr.size() != 0;
    }

    private void parseValue() {
        parseJSONArray((JSONArray) output.getValue());
    }

    private void parseValues() {
        List<Object> values = output.getValues();
        for (Object value : values) {
            parseJSONArray((JSONArray) value);
        }
    }

    private void parseJSONArray(JSONArray value) {
        for (int i = 0, size = value.size(); i < size; i++) {
            JSONObject subOfValue = value.getJSONObject(i);
            parseJSONObject(subOfValue);
        }
    }

    private void parseJSONObject(JSONObject subOfValue) {
        Set<Map.Entry<String, Object>> entrys = subOfValue.entrySet();
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
