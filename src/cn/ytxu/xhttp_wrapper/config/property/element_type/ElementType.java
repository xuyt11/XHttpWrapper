package cn.ytxu.xhttp_wrapper.config.property.element_type;

import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.xhttp_wrapper.common.enums.OutputParamType;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.response.OutputParamModel;

import java.util.Objects;

/**
 * Created by ytxu on 2016/8/28.
 * 参数类型枚举
 */
public enum ElementType {
    NULL(OutputParamType.NULL) {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getNullET();
        }
    },
    // date类型不会出现在json中，
    DATE(null, "Date", "DateTime") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getDateET();
        }
    },
    // 只有请求方法中有file类型
    FILE(null, "File") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getFileET();
        }

        @Override
        protected String getElementTypeByOutput(OutputParamModel output) {
            throw new RuntimeException("output param must not be file type");
        }
    },
    INTEGER(OutputParamType.INTEGER, "Integer") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getIntegerET();
        }
    },
    LONG(OutputParamType.LONG, "Long") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getLongET();
        }
    },
    FLOAT(OutputParamType.FLOAT, "Float") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getFloatET();
        }
    },
    DOUBLE(OutputParamType.DOUBLE, "Double") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getDoubleET();
        }
    },
    // FUTURE 未来将会删除掉的类型，这样的类型，不能知道精确类型
    NUMBER(OutputParamType.NUMBER, "Number") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getNumberET();
        }
    },
    BOOLEAN(OutputParamType.BOOLEAN, "Boolean") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getBooleanET();
        }
    },
    STRING(OutputParamType.STRING, "String") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getStringET();
        }
    },
    // tip: 对象类型不能在request parameter list中出现
    OBJECT(OutputParamType.JSON_OBJECT) {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            throw new RuntimeException("program can not call this");
        }

        @Override
        protected String getElementTypeByOutput(OutputParamModel output) {
            return output.entity_class_name();
        }

        @Override
        public String getElementTypeByInput() {
            throw new RuntimeException("input param must not be object type");
        }

        @Override
        public String getElementRequestTypeByInput() {
            throw new RuntimeException("input param must not be object type");
        }
    },
    // ${object} -->使用其进行替换
    ARRAY(OutputParamType.JSON_ARRAY, "Array", "List") {
        protected FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum) {
            return elementTypeEnum.getArrayET();
        }

        @Override
        protected String getElementTypeByOutput(OutputParamModel output) {
            String etContent = super.getElementTypeByOutput(output);
            ElementType subElementType = ElementType.getTypeByOutputType(output.getSubType());
            String subElementTypeStr = getSubTypeContent(output, subElementType);
            etContent = etContent.replace("${object}", subElementTypeStr);
            return etContent;
        }

        private String getSubTypeContent(OutputParamModel output, ElementType subElementType) {
            String subElementTypeStr;
            if (subElementType == OBJECT) {
                subElementTypeStr = OBJECT.getElementTypeByOutput(output);
            } else {
                subElementTypeStr = subElementType.getElementRequestTypeByInput();
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

    protected String getElementTypeByOutput(OutputParamModel output) {
        return getEtBean(ConfigWrapper.getFieldType()).getField_type();
    }

    public static ElementType getTypeByInput(String inputTypeText) {
        for (ElementType type : ElementType.values()) {
            String[] inputTypes = type.inputTypes;
            if (Objects.isNull(inputTypes)) {
                continue;
            }
            for (String inputType : inputTypes) {
                if (inputType.equalsIgnoreCase(inputTypeText)) {
                    return type;
                }
            }
        }
        return NULL;
    }

    // TODO need delete
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

    public String getElementTypeByInput() {
        return getEtBean(ConfigWrapper.getFieldType()).getField_type();
    }

    public String getElementRequestTypeByInput() {
        return getEtBean(ConfigWrapper.getFieldType()).getField_optional_type();
    }

    protected abstract FieldTypeEnumBean.EtBean getEtBean(FieldTypeWrapper elementTypeEnum);
}