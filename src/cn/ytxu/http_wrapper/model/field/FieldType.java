package cn.ytxu.http_wrapper.model.field;

import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.config.property.element_type.FieldTypeEnumBean;

import java.util.Objects;

/**
 * Created by ytxu on 2016/8/28.
 * 参数类型枚举
 */
public enum FieldType {
    NULL() {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getNullET();
        }
    },
    // date类型不会出现在json中，
    DATE("Date", "DateTime") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getDateET();
        }
    },
    // 只有请求方法中有file类型
    FILE("File") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getFileET();
        }
    },
    INTEGER("Integer") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getIntegerET();
        }
    },
    LONG("Long") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getLongET();
        }
    },
    FLOAT("Float") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getFloatET();
        }
    },
    DOUBLE("Double") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getDoubleET();
        }
    },
    // FUTURE 未来将会删除掉的类型，这样的类型，不能知道精确类型
    NUMBER("Number") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getNumberET();
        }
    },
    BOOLEAN("Boolean") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getBooleanET();
        }
    },
    STRING("String") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getStringET();
        }
    },
    // tip: 对象类型不能在request parameter list中出现
    OBJECT() {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getObjectET();
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
    ARRAY("Array", "List") {
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getArrayET();
        }
    },
    // ${object} -->使用其进行替换
    MAP("Map", "Dictionary", "Dict") {
        @Override
        protected FieldTypeEnumBean.EtBean getEtBean() {
            return ConfigWrapper.getFieldType().getMapET();
        }
    };

    private final String[] inputTypes;

    FieldType(String... inputTypes) {
        this.inputTypes = inputTypes;
    }

    public static FieldType getByFieldTypeStr(String fieldTypeStr) {
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
        return getEtBean().getField_type();
    }

    protected String getInputFieldOptionalTypeNameType() {
        return getEtBean().getField_optional_type();
    }

    protected abstract FieldTypeEnumBean.EtBean getEtBean();
}