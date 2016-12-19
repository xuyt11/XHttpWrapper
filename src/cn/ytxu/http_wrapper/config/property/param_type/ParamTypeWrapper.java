package cn.ytxu.http_wrapper.config.property.param_type;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ytxu on 16/12/16.
 */
public class ParamTypeWrapper {

    private static ParamTypeWrapper instance;
    private Map<String, ParamTypeBean> paramTypes;

    public static ParamTypeWrapper getInstance() {
        return instance;
    }

    public static void load(Map<String, ParamTypeBean> paramTypes) {
        LogUtil.i(ParamTypeWrapper.class, "load param types property start...");
        instance = new ParamTypeWrapper(paramTypes);
        LogUtil.i(ParamTypeWrapper.class, "load param types property success...");
    }

    private ParamTypeWrapper(Map<String, ParamTypeBean> paramTypes) {
        this.paramTypes = paramTypes;

        if (Objects.isNull(paramTypes) || paramTypes.isEmpty()) {
            throw new NullPointerException("u need setup param types property...");
        }

        for (ParamTypeEnum paramTypeEnum : ParamTypeEnum.values()) {
            ParamTypeBean paramTypeBean = paramTypes.get(paramTypeEnum.name());

            checkMatchTypeName(paramTypeEnum, paramTypeBean);
            checkParamTypeDefined(paramTypeEnum, paramTypeBean);

            paramTypeBean.setParamTypeEnum(paramTypeEnum);
        }
    }

    private void checkMatchTypeName(ParamTypeEnum paramTypeEnum, ParamTypeBean paramTypeBean) {
        List<String> matchTypeNames = paramTypeBean.getMatchTypeNames();
        if (Objects.isNull(matchTypeNames) || matchTypeNames.isEmpty()) {
            if (ParamTypeEnum.UNKNOWN != paramTypeEnum) {
                throw new IllegalArgumentException("the param type(" + paramTypeEnum.name()
                        + ") property, u need setup match type names...");
            }
        } else {
            for (String matchTypeName : matchTypeNames) {
                if (Objects.isNull(matchTypeName) || matchTypeName.trim().isEmpty()) {
                    throw new IllegalArgumentException("the param type(" + paramTypeEnum.name()
                            + ") property, u don`t setup empty to match type name, u are a bad kid...");
                }
            }
        }
    }

    private void checkParamTypeDefined(ParamTypeEnum paramTypeEnum, ParamTypeBean paramTypeBean) {
        boolean isInvalid = Objects.isNull(paramTypeBean) || paramTypeBean.isInvalid();
        if (ParamTypeEnum.NUMBER == paramTypeEnum) {
            if (!isInvalid) {
                LogUtil.w("this param type(number) property, u need not setup and use it... ");
            }
        } else {
            if (isInvalid) {
                throw new IllegalArgumentException("the param type(" + paramTypeEnum.name() + ") property, don`t setup or setup error...");
            }
        }
    }


    public ParamTypeBean getParamTypeBean(String inputTypeText) {
        for (ParamTypeBean paramType : paramTypes.values()) {
            List<String> matchTypeNames = paramType.getMatchTypeNames();
            for (String matchTypeName : matchTypeNames) {
                if (matchTypeName.equalsIgnoreCase(inputTypeText)) {
                    return paramType;
                }
            }
        }
        return paramTypes.get(ParamTypeEnum.UNKNOWN.name());
    }

    public ParamTypeBean getParamTypeBean(ParamTypeEnum paramTypeEnum) {
        String paramTypeName = paramTypeEnum.name();
        for (Map.Entry<String, ParamTypeBean> paramTypeBeanEntry : paramTypes.entrySet()) {
            String paramTypeKey = paramTypeBeanEntry.getKey();
            if (paramTypeKey.equals(paramTypeName)) {
                return paramTypeBeanEntry.getValue();
            }
        }
        return paramTypes.get(ParamTypeEnum.UNKNOWN.name());
    }

    public String getResponseParamType(OutputParamModel output) {
        return getParamTypeBean(output.getType()).getResponseParamType(output);
    }
}
