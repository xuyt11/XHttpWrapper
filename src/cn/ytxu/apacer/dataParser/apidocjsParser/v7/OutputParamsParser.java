package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.OutputParamEntity;
import cn.ytxu.apacer.entity.ResponseEntity;
import cn.ytxu.util.LogUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
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
        List<OutputParamEntity> outputs = parseJSONObjectToOutputParams(responseContentJObj);
        response.setOutputParams(outputs);

        OutputParamEntity.setResponse(outputs, response);
        return response;
    }

    /**
     * 分析Json对象(JsonObject)(输出参数对象)的属性
     */
    private List<OutputParamEntity> parseJSONObjectToOutputParams(JSONObject fieldValue) {
        List<OutputParamEntity> outputs = new ArrayList<>(fieldValue.size());
        for (Map.Entry<String, Object> entry : fieldValue.entrySet()) {
            OutputParamEntity entity = parseJSONObjectEntryToOutputParam(entry);
            outputs.add(entity);
        }
        return outputs;
    }

    /**
     * 分析出输出参数中对象的属性
     */
    public OutputParamEntity parseJSONObjectEntryToOutputParam(Map.Entry<String, Object> entry) {
        String fieldName = entry.getKey();
        Object fieldValue = entry.getValue();
        if (null == fieldValue) {// 若字段中的值是null,则直接设置为String类型;以后有问题再去看
            LogUtil.i("the value is null, fieldName:" + fieldName);
            return new OutputParamEntity(fieldName, "String", "this value is null in result demo");
        }

        Class fieldType = fieldValue.getClass();
        if (fieldType == String.class) {
            return new OutputParamEntity(fieldName, "String", fieldValue.toString());
        }
        if (fieldType == Integer.class) {
            return new OutputParamEntity(fieldName, "Number", fieldValue.toString());
        }
        if (fieldType == Boolean.class) {
            return new OutputParamEntity(fieldName, "Boolean", fieldValue.toString());
        }
        if (fieldType == JSONObject.class) {
            OutputParamEntity entity = createrObjectType(fieldName, fieldValue.toString());
            List<OutputParamEntity> subs = parseJSONObjectToOutputParams((JSONObject) fieldValue);
            entity.setSubs(subs);
            return entity;
        }
        if (fieldType == JSONArray.class) {
            OutputParamEntity entity = parseJSONArrayToOutputParam(fieldName, (JSONArray) fieldValue);
            return entity;
        }
        // i don`t know type
        throw new RuntimeException("i don`t know curr class type:" + fieldType);
    }

    /**
     * 分析Json数组(JsonArray): 若数组中只是int或String类型的要进行反设置其类型,否则其类型是一个自定一个实体类
     */
    private OutputParamEntity parseJSONArrayToOutputParam(String fieldName, JSONArray fieldValue) {
        LogUtil.i("parseJSONArrayToOutputParam fieldName:" + fieldName + ", fieldValue:" + fieldValue);
        OutputParamEntity entity = createrArrayType(fieldName, fieldValue.toString());

        if (fieldValue.size() <= 0) {
            // TODO 先这样吧,等以后在看如何办;应该会将这些东西都给分离吧????
            LogUtil.w("请在index==0的位置将该数组中所有的属性添加上!" + ", fieldName:" + fieldName);
            entity.setType(null);
            return entity;
        }

        Object obj = fieldValue.get(0);
        Class objType = obj.getClass();
        String fieldType;
        if (objType == String.class) {
            fieldType = "String";
        } else if (objType == Integer.class) {
            fieldType = "Number";
        } else if (objType == Boolean.class) {
            fieldType = "Boolean";
        } else if (objType == JSONObject.class) {
            fieldType = "Object";
            List<OutputParamEntity> subs = parseJSONObjectToOutputParams((JSONObject) obj);
            entity.setSubs(subs);
        } else {
            // i don`t know type
            throw new RuntimeException("parserApiDocHtmlCode2DocumentEntity output array, i don`t know curr class type:" + objType);
        }

        entity.setType(fieldType);
        return entity;
    }

    private OutputParamEntity createrObjectType(String name, String desc) {
        // 对象类型,直接使用isObject进行判断,不需要用type字段进行判断
        OutputParamEntity entity = new OutputParamEntity(name, null, desc);
        entity.setObject(true);
        return entity;
    }

    private OutputParamEntity createrArrayType(String name, String desc) {
        OutputParamEntity entity = new OutputParamEntity(name, null, desc);
        entity.setArray(true);
        return entity;
    }

    // enum:jsonObject, jsonArray, String, Integer, Boolean, Double?,

}
