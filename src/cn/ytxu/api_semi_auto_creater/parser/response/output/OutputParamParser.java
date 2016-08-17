package cn.ytxu.api_semi_auto_creater.parser.response.output;

import cn.ytxu.apacer.entity.OutputParamEntity;
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
        getOutputs(entrys);

    }

    private List<OutputParamModel> getOutputs(Collection<Map.Entry<String, Object>> entrys) {
        List<OutputParamModel> outputs = new ArrayList<>(entrys.size());
        for (Map.Entry<String, Object> entry : entrys) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            OutputParamType type = OutputParamType.get(fieldValue);
            OutputParamModel output = type.createOutput(response, fieldName, fieldValue);
            outputs.add(output);
        }
        return outputs;
    }




    private static class Struct {
        private String name;
        private String type;// is enum
        private List<String> contents;

    }

}
