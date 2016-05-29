package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create;

import cn.ytxu.apacer.entity.OutputParamEntity;
import cn.ytxu.util.LogUtil;
import com.alibaba.fastjson.JSONArray;

/**
 * Created by ytxu on 2016/5/29.
 */
public class UnknowOutputParamCreater implements OutputParamCreater {
    @Override
    public OutputParamEntity getOutputParam4JSONObject(String fieldName, Object fieldValue) {
        // 若字段中的值是null或者i don`t know type,则直接设置为String类型;以后有问题再去看
        LogUtil.i("the value is null or i don`t know type, fieldName:" + fieldName);
        return new OutputParamEntity(fieldName, "String", "this value is null or i don`t know type in result demo");
    }

    @Override
    public OutputParamEntity getOutputParam4JSONArray(OutputParamEntity entity, String fieldName, JSONArray fieldValue) {
        // TODO 先这样吧,等以后在看如何办;应该会将这些东西都给分离吧????
        LogUtil.w("请在index==0的位置将该数组中所有的属性添加上!" + ", fieldName:" + fieldName
                + ", or i don`t know curr class type:" + fieldValue.toString());
        entity.setType(null);
        return entity;
    }
}
