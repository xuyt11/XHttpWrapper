package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create.OutputParamCreater;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.OutputParamEntity;
import cn.ytxu.apacer.entity.ResponseEntity;
import cn.ytxu.util.LogUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 响应参数的解析器
 * 2016-04-05
 */
public class OutputParamsParser {

    public List<ResponseEntity> parseResponseContent(List<ResponseEntity> responses, List<FieldEntity> descParams) {
        int size = responses.size();
        List<ResponseEntity> responseList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ResponseEntity response = responses.get(i);
            response = parseResponseContent(response);
            responseList.add(response);
        }

        // add a same field object to response object, if thier name and type is same
        for (ResponseEntity response : responseList) {
            // TODO must use response, descParams
        }

        return responseList;
    }

    private ResponseEntity parseResponseContent(ResponseEntity response) {
        String responseContent = response.getResponseContent();
        LogUtil.i("response desc:" + response.getResponseDesc() + ", jsonStr:" + responseContent);
        JSONObject responseContentJObj;
        try {
            responseContentJObj = JSON.parseObject(responseContent);
        } catch (JSONException e) {
            // TODO 这是返回数据的Json格式有问题,以后解决
            e.printStackTrace();
            LogUtil.i("返回数据的Json格式有问题,以后解决: " + response.toString());
            return response;
        }

        if (responseContentJObj.containsKey(Config.BaseResponse.StatusCode)) {
            response.setStatusCode(String.valueOf(responseContentJObj.getInteger(Config.BaseResponse.StatusCode)));
        } else {// TODO 未来要他们统一返回格式,有些result没有这个字段
            LogUtil.i("can not have status code:" + response.toString());
        }
        List<OutputParamEntity> outputs = parseEntrysToOutputParams(responseContentJObj);
        response.setOutputParams(outputs);

        OutputParamEntity.setResponse(outputs, response);
        return response;
    }

    /**
     * 分析Json对象(JsonObject)(输出参数对象)的属性
     */
    public static List<OutputParamEntity> parseEntrysToOutputParams(JSONObject fieldValue) {
        return parseEntrysToOutputParams(fieldValue.entrySet());
    }

    public static List<OutputParamEntity> parseEntrysToOutputParams(Collection<Map.Entry<String, Object>> entrys) {
        List<OutputParamEntity> outputs = new ArrayList<>(entrys.size());
        for (Map.Entry<String, Object> entry : entrys) {
            OutputParamEntity entity = parseJSONObjectEntryToOutputParam(entry);
            outputs.add(entity);
        }
        return outputs;
    }

    /**
     * 分析出输出参数中对象的属性
     */
    private static OutputParamEntity parseJSONObjectEntryToOutputParam(Map.Entry<String, Object> entry) {
        String fieldName = entry.getKey();
        Object fieldValue = entry.getValue();
        OutputParamCreater outputCreater = OutputFactory.getOutputParamCreater(fieldValue);
        OutputParamEntity output = outputCreater.getOutputParam4JSONObject(fieldName, fieldValue);
        return output;
    }

    // enum:jsonObject, jsonArray, String, Integer, Boolean, Double?,

}
