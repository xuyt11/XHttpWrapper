package cn.ytxu.api_semi_auto_creater.config.property.param_type;

import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.parser.response.output.OutputParamType;

import java.util.Objects;

/**
 * Created by ytxu on 2016/8/28.
 * 参数类型枚举
 */
public enum ElementType {
    NULL("type.null", OutputParamType.NULL),
    DATE("type.date", null, "Date", "DateTime"),// date类型不会出现在json中，
    FILE("type.file", null, "File") {// 只有请求方法中有file类型

        @Override
        public String getETContentByOutput(OutputParamModel output) {
            throw new IllegalArgumentException("output param can not object type, output:" + output.toString());
        }
    },
    INTEGER("type.integer", OutputParamType.INTEGER, "Integer"),
    LONG("type.long", OutputParamType.LONG, "Long"),
    FLOAT("type.float", OutputParamType.FLOAT, "Float"),
    DOUBLE("type.double", OutputParamType.DOUBLE, "Double"),
    NUMBER("type.number", OutputParamType.NUMBER, "Number"),// FUTURE 未来将会删除掉的类型，这样的类型，不能知道精确类型
    BOOLEAN("type.boolean", OutputParamType.BOOLEAN, "Boolean"),
    STRING("type.string", OutputParamType.STRING, "String"),
    OBJECT("type.object", OutputParamType.JSON_OBJECT) {
        @Override
        public String getRequestETContentByInput(InputParamModel input) {
            throw new IllegalArgumentException("input param can not object type, input:" + input.toString());
        }

        @Override
        public String getETContentByOutput(OutputParamModel output) {
            return output.entity_class_name();
        }
    },
    ARRAY("type.array", OutputParamType.JSON_ARRAY, "Array", "List") {
        @Override
        public String getETContentByOutput(OutputParamModel output) {
            String name = super.getETContentByOutput(output);
            ElementType subElementType = ElementType.getTypeByOutputType(output.getSubType());
            String subElementTypeStr = getSubTypeContent(output, subElementType);
            name = name.replace("${object}", subElementTypeStr);
            return name;
        }

        private String getSubTypeContent(OutputParamModel output, ElementType subElementType) {
            String subElementTypeStr;
            if (subElementType == OBJECT) {
                subElementTypeStr = subElementType.getETContentByOutput(output);
            } else {
                subElementTypeStr = getElementRequestTypeContent(subElementType);
            }
            return subElementTypeStr;
        }
    };

    private final String propertyKey;// 在配置文件中的key
    private final OutputParamType outputType;
    private final String[] inputTypes;

    ElementType(String propertyKey, OutputParamType outputType, String... inputTypes) {
        this.propertyKey = propertyKey;
        this.outputType = outputType;
        this.inputTypes = inputTypes;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public String getETContenByInput(InputParamModel input) {
        return getElementTypeContent(this);
    }

    private static String getElementTypeContent(ElementType elementType) {
        ElementTypeProperty property = ElementTypeProperty.getByElementType(elementType);
        return property.getElementType();
    }

    public String getRequestETContentByInput(InputParamModel input) {
        return getElementRequestTypeContent(this);
    }

    private static String getElementRequestTypeContent(ElementType elementType) {
        ElementTypeProperty property = ElementTypeProperty.getByElementType(elementType);
        return property.getElementRequestType();
    }

    public String getETContentByOutput(OutputParamModel output) {
        return getElementTypeContent(this);
    }

    public static ElementType getTypeByOutputType(OutputParamType outputType) {
        for (ElementType type : ElementType.values()) {
            if (type.outputType == outputType) {
                return type;
            }
        }
        return NULL;
    }

    public static ElementType getTypeByInput(InputParamModel input) {
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
}