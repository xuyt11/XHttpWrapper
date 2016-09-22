package cn.ytxu.xhttp_wrapper.model.field;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output.OutputParamType;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.config.property.element_type.ElementTypeEnumBean;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created by ytxu on 2016/8/28.
 * 参数类型枚举
 */
public enum FieldType {
    NULL(OutputParamType.NULL) {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getNullET();
        }
    },
    // date类型不会出现在json中，
    DATE(null, "Date", "DateTime") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getDateET();
        }
    },
    // 只有请求方法中有file类型
    FILE(null, "File") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getFileET();
        }

        @Override
        protected String getElementTypeByOutput(OutputParamModel output) {
            throw new RuntimeException("output param must not be file type");
        }
    },
    INTEGER(OutputParamType.INTEGER, "Integer") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getIntegerET();
        }
    },
    LONG(OutputParamType.LONG, "Long") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getLongET();
        }
    },
    FLOAT(OutputParamType.FLOAT, "Float") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getFloatET();
        }
    },
    DOUBLE(OutputParamType.DOUBLE, "Double") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getDoubleET();
        }
    },
    // FUTURE 未来将会删除掉的类型，这样的类型，不能知道精确类型
    NUMBER(OutputParamType.NUMBER, "Number") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getNumberET();
        }
    },
    BOOLEAN(OutputParamType.BOOLEAN, "Boolean") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getBooleanET();
        }
    },
    STRING(OutputParamType.STRING, "String") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getStringET();
        }
    },
    // tip: 对象类型不能在request parameter list中出现
    OBJECT(OutputParamType.JSON_OBJECT) {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getObjectET();
        }

        @Override
        protected String getElementTypeByOutput(OutputParamModel output) {
            return output.entity_class_name();
        }

        @Override
        protected String getInputFieldTypeNameByInput() {
            throw new RuntimeException("input param must not be object type");
        }

        @Override
        protected String getInputFieldOptionalTypeNameType() {
            throw new RuntimeException("input param must not be object type");
        }
    },
    // ${object} -->使用其进行替换
    ARRAY(OutputParamType.JSON_ARRAY, "Array", "List") {
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getArrayET();
        }

        @Override
        protected String getElementTypeByOutput( OutputParamModel output) {
            String etContent = super.getElementTypeByOutput( output);
            FieldType subElementType = FieldType.getTypeByOutputType(output.getSubType());
            String subElementTypeStr = getSubTypeContent(output, subElementType);
            etContent = etContent.replace("${object}", subElementTypeStr);
            return etContent;
        }

        private String getSubTypeContent(OutputParamModel output, FieldType subElementType) {
            String subElementTypeStr;
            if (subElementType == OBJECT) {
                subElementTypeStr = OBJECT.getElementTypeByOutput(output);
            } else {
                subElementTypeStr = subElementType.getInputFieldOptionalTypeNameType();
            }
            return subElementTypeStr;
        }
    },
    // ${object} -->使用其进行替换
    MAP(OutputParamType.JSON_OBJECT, "Map", "Dictionary", "Dict") {
        @Override
        protected ElementTypeEnumBean.EtBean getEtBean() {
            return Property.getElementTypeProperty().getMapET();
        }
        // TODO implements other method
    };

    private final OutputParamType outputType;
    private final String[] inputTypes;

    FieldType(OutputParamType outputType, String... inputTypes) {
        this.outputType = outputType;
        this.inputTypes = inputTypes;
    }

    protected static FieldType getTypeByOutputType(OutputParamType outputType) {
        for (FieldType type : FieldType.values()) {
            if (type.outputType == outputType) {
                return type;
            }
        }
        return NULL;
    }

    protected String getElementTypeByOutput(OutputParamModel output) {
        return getEtBean().getElement_type();
    }

    protected static FieldType getByFieldTypeStr(String fieldTypeStr) {
        for (FieldType type : FieldType.values()) {
            String[] inputTypes = type.inputTypes;
            if (Objects.isNull(inputTypes)) {
                continue;
            }
            for (String inputType : inputTypes) {
                if (inputType.equalsIgnoreCase(fieldTypeStr)) {
                    return type;
                }
            }
        }
        return NULL;
    }

    protected String getInputFieldTypeNameByInput() {
        return getEtBean().getElement_type();
    }

    protected String getInputFieldOptionalTypeNameType() {
        return getEtBean().getElement_request_type();
    }

    protected abstract ElementTypeEnumBean.EtBean getEtBean();
}