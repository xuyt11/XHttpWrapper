package cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output.sub.SubOutputParser;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

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
        setSubType();
        if (needParseValueAndValues()) {
            parseValue();
            parseValues();
        }
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
            JSONArray jArr = (JSONArray) value;
            if (setSubTypeIfCan(jArr)) {
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

    private boolean needParseValueAndValues() {
        return OutputParamType.JSON_OBJECT == output.getSubType();
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
            JSONObject subOfValue = null;
            try {
                subOfValue = value.getJSONObject(i);
            } catch (ClassCastException e) {
                e.printStackTrace();
                throw new ClassCastException(e.getMessage());
            }
            new SubOutputParser(parser, output, subOfValue).parse();
        }
    }

}
