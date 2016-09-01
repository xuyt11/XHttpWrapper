package cn.ytxu.api_semi_auto_creater.config.property.element_type;

import cn.ytxu.api_semi_auto_creater.config.PropertyEntity;
import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.parser.response.output.OutputParamType;

import java.util.Objects;

/**
 * Created by ytxu on 2016/8/28.
 * 参数类型枚举
 */
public enum ElementType {
    NULL(OutputParamType.NULL) {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getNullET();
        }
    },
    // date类型不会出现在json中，
    DATE(null, "Date", "DateTime") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getDateET();
        }
    },
    // 只有请求方法中有file类型
    FILE(null, "File") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getFileET();
        }

        @Override
        protected String getElementTypeByOutput(ElementTypeProperty etProperty, OutputParamModel output) {
            throw new RuntimeException("output param must not be file type");
        }
    },
    INTEGER(OutputParamType.INTEGER, "Integer") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getIntegerET();
        }
    },
    LONG(OutputParamType.LONG, "Long") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getLongET();
        }
    },
    FLOAT(OutputParamType.FLOAT, "Float") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getFloatET();
        }
    },
    DOUBLE(OutputParamType.DOUBLE, "Double") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getDoubleET();
        }
    },
    // FUTURE 未来将会删除掉的类型，这样的类型，不能知道精确类型
    NUMBER(OutputParamType.NUMBER, "Number") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getNumberET();
        }
    },
    BOOLEAN(OutputParamType.BOOLEAN, "Boolean") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getBooleanET();
        }
    },
    STRING(OutputParamType.STRING, "String") {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getStringET();
        }
    },
    // tip: 对象类型不能在request parameter list中出现
    OBJECT(OutputParamType.JSON_OBJECT) {
        @Override
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            throw new RuntimeException("program can not call this");
        }

        @Override
        protected String getElementTypeByOutput(ElementTypeProperty etProperty, OutputParamModel output) {
            return output.entity_class_name();
        }

        @Override
        protected String getElementTypeByInput(ElementTypeProperty etProperty, InputParamModel input) {
            throw new RuntimeException("input param must not be object type");
        }

        @Override
        protected String getElementRequestTypeByInput(ElementTypeProperty etProperty, InputParamModel input) {
            throw new RuntimeException("input param must not be object type");
        }
    },
    // ${object} -->使用其进行替换
    ARRAY(OutputParamType.JSON_ARRAY, "Array", "List") {
        protected PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum) {
            return elementTypeEnum.getArrayET();
        }

        @Override
        protected String getElementTypeByOutput(ElementTypeProperty etProperty, OutputParamModel output) {
            String etContent = super.getElementTypeByOutput(etProperty, output);
            ElementType subElementType = ElementType.getTypeByOutputType(output.getSubType());
            String subElementTypeStr = getSubTypeContent(etProperty, output, subElementType);
            etContent = etContent.replace("${object}", subElementTypeStr);
            return etContent;
        }

        private String getSubTypeContent(ElementTypeProperty etProperty, OutputParamModel output, ElementType subElementType) {
            String subElementTypeStr;
            if (subElementType == OBJECT) {
                subElementTypeStr = OBJECT.getElementTypeByOutput(etProperty, output);
            } else {
                subElementTypeStr = subElementType.getElementRequestTypeByInput(etProperty, null);
            }
            return subElementTypeStr;
        }
    };

    private final OutputParamType outputType;
    private final String[] inputTypes;

    ElementType(OutputParamType outputType, String... inputTypes) {
        this.outputType = outputType;
        this.inputTypes = inputTypes;
    }

    protected static ElementType getTypeByOutputType(OutputParamType outputType) {
        for (ElementType type : ElementType.values()) {
            if (type.outputType == outputType) {
                return type;
            }
        }
        return NULL;
    }

    protected String getElementTypeByOutput(ElementTypeProperty etProperty, OutputParamModel output) {
        return getEtBean(etProperty).getElement_type();
    }

    protected static ElementType getTypeByInput(InputParamModel input) {
        String inputTypeStr = input.getType();
        for (ElementType type : ElementType.values()) {
            String[] inputTypes = type.inputTypes;
            if (Objects.isNull(inputTypes)) {
                continue;
            }
            for (String inputType : inputTypes) {
                if (inputType.equalsIgnoreCase(inputTypeStr)) {
                    return type;
                }
            }
        }
        return NULL;
    }

    protected String getElementTypeByInput(ElementTypeProperty etProperty, InputParamModel input) {
        return getEtBean(etProperty).getElement_type();
    }

    protected String getElementRequestTypeByInput(ElementTypeProperty etProperty, InputParamModel input) {
        return getEtBean(etProperty).getElement_request_type();
    }

    protected abstract PropertyEntity.ElementTypeEnumBean.EtBean getEtBean(ElementTypeProperty elementTypeEnum);
}