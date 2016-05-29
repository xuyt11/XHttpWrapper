package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create;

import cn.ytxu.apacer.entity.OutputParamEntity;
import cn.ytxu.util.LogUtil;

/**
 * Created by ytxu on 2016/5/29.
 */
public class UnknowOutputParamCreater implements IOutputParamCreater {
    @Override
    public OutputParamEntity getOutputParam(String fieldName, Object fieldValue) {
        // 若字段中的值是null或者i don`t know type,则直接设置为String类型;以后有问题再去看
        LogUtil.i("the value is null or i don`t know type, fieldName:" + fieldName);
        return new OutputParamEntity(fieldName, "String", "this value is null or i don`t know type in result demo");
    }
}
