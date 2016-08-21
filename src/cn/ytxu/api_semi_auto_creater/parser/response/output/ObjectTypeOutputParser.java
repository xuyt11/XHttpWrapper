package cn.ytxu.api_semi_auto_creater.parser.response.output;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.parser.response.output.sub.JSONObjectOrJSONArraySubOutputParser;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

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
        parseJSONObject((JSONObject) output.getValue());
    }

    private void parseValues() {
        List<Object> values = output.getValues();
        for (Object value : values) {
            parseJSONObject((JSONObject) value);
        }
    }

    private void parseJSONObject(JSONObject value) {
        new JSONObjectOrJSONArraySubOutputParser(parser, output, value).parse();
    }

}
