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

    public static List<ResponseEntity> parser(int categoryIndex, int methodIndex, List<ResponseEntity> responses, List<FieldEntity> descParams) {
        int size = responses.size();
        List<ResponseEntity> responseList = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            ResponseEntity response = responses.get(i);
            parserOneResponse(categoryIndex, methodIndex, response);
            responseList.add(response);
        }

        // add a same field object to response object, if thier name and type is same
        for (ResponseEntity response : responseList) {
            // TODO must use response, descParams


        }

        return responseList;
    }

    private static void parserOneResponse(int categoryIndex, int methodIndex, ResponseEntity response) {
        String jsonStr = response.getResponseContent();
        LogUtil.i("parserOutputArray categoryIndex:" + categoryIndex + ", methodIndex:" + methodIndex
                + "response desc:" + response.getResponseDesc() + ", jsonStr:" + jsonStr);
        JSONObject jObj = null;
        try {
            jObj = JSON.parseObject(jsonStr);
        } catch (JSONException e) {
            // TODO 这是返回数据的Json格式有问题,以后解决
            e.printStackTrace();
            return;
        }

        if (jObj.containsKey(Config.BaseResponse.StatusCode)) {// TODO 未来要他们统一返回格式,有些result没有这个字段
            response.setStatusCode(String.valueOf(jObj.getInteger(Config.BaseResponse.StatusCode)));
        } else {
            LogUtil.i("can not have status code:parserOutputArray categoryIndex:" + categoryIndex + ", methodIndex:" + methodIndex
                    + "response desc:" + response.getResponseDesc() + ", jsonStr:" + jsonStr);
        }
        List<OutputParamEntity> outputs = parserOutputObject(categoryIndex, methodIndex, jObj);
        response.setOutputParams(outputs);

        OutputParamEntity.setResponse(outputs, response);
    }

    /** 分析Json对象(JsonObject)(输出参数对象)的属性 */
    private static List<OutputParamEntity> parserOutputObject(int categoryIndex, int methodIndex, JSONObject fieldValue) {
        List<OutputParamEntity> outputs = new ArrayList<>(fieldValue.size());

        for (Map.Entry<String, Object> entry : fieldValue.entrySet()) {
            OutputParamEntity entity = parserOutputParam(categoryIndex, methodIndex, entry);
            outputs.add(entity);
        }

        return outputs;
    }

    /** 分析Json数组(JsonArray): 若数组中只是int或String类型的要进行反设置其类型,否则其类型是一个自定一个实体类  */
    private static OutputParamEntity parserOutputArray(int categoryIndex, int methodIndex, String fieldName, JSONArray fieldValue) {
        LogUtil.i("parserOutputArray categoryIndex:" + categoryIndex + ", methodIndex:" + methodIndex + "fieldName:" + fieldName + ", fieldValue:" + fieldValue);
        OutputParamEntity entity = OutputParamEntity.createrArrayType(fieldName, fieldValue.toString());

        if (fieldValue.size() <= 0) {
            // TODO 先这样吧,等以后在看如何办;应该会将这些东西都给分离吧????
            LogUtil.w("请在index==0的位置将该数组中所有的属性添加上!" + ", categoryIndex:" + categoryIndex + ", methodIndex:" + methodIndex + ", fieldName:" + fieldName);
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
            List<OutputParamEntity> subs = parserOutputObject(categoryIndex, methodIndex, (JSONObject) obj);
            entity.setSubs(subs);
        } else {
            // i don`t know type
            throw new RuntimeException("parserApiDocHtmlCode2DocumentEntity output array, i don`t know curr class type:" + objType + ", categoryIndex:" + categoryIndex + ", methodIndex:" + methodIndex);
        }

        entity.setType(fieldType);
        return entity;
    }


    /** 分析出输出参数中对象的属性 */
    public static OutputParamEntity parserOutputParam(int categoryIndex, int methodIndex, Map.Entry<String, Object> entry) {
        String fieldName = entry.getKey();
//        LogUtil.i("fieldName:" + fieldName + ", categoryIndex:" + categoryIndex + ", methodIndex:" + methodIndex);
        Object fieldValue = entry.getValue();
        if (null == fieldValue) {// 若字段中的值是null,则直接设置为String类型;以后有问题再去看
            LogUtil.i("the value is null, fieldName:" + fieldName + ", categoryIndex:" + categoryIndex + ", methodIndex:" + methodIndex);
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
            OutputParamEntity entity = OutputParamEntity.createrObjectType(fieldName, fieldValue.toString());

            List<OutputParamEntity> subs = parserOutputObject(categoryIndex, methodIndex, (JSONObject) fieldValue);
            entity.setSubs(subs);

            return entity;
        }

        if (fieldType == JSONArray.class) {
            OutputParamEntity entity = parserOutputArray(categoryIndex, methodIndex, fieldName, (JSONArray) fieldValue);
            return entity;
        }

        // i don`t know type
        throw new RuntimeException("i don`t know curr class type:" + fieldType + ", categoryIndex:" + categoryIndex + ", methodIndex:" + methodIndex);
    }


    // enum:jsonObject, jsonArray, String, Integer, Boolean, Double?,

}
