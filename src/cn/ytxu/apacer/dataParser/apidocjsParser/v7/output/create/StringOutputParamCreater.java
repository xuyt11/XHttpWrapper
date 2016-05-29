package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output.create;

import cn.ytxu.apacer.entity.OutputParamEntity;

/**
 * Created by ytxu on 2016/5/29.
 */
public class StringOutputParamCreater implements IOutputParamCreater {
    @Override
    public OutputParamEntity getOutputParam(String fieldName, Object fieldValue) {
        return new OutputParamEntity(fieldName, "String", fieldValue.toString());
    }
}
