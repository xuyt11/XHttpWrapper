package cn.ytxu.xhttp_wrapper.common.enums;

import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.record.retain.RetainModel;

/**
 * 在自动生成的代码文件中，由程序员根据情况，自己手写的代码；
 * 而这部分代码，在下次运行时，会保留下来；
 * 而在其他地方的改动，都会被清除
 */
public enum RetainType {
    Import("import") {
        @Override
        public StringBuffer getRetainContent(RetainModel retain) {
            return retain.getImportData();
        }
    },
    Field("field") {
        @Override
        public StringBuffer getRetainContent(RetainModel retain) {
            return retain.getFieldData();
        }
    },
    Method("method") {
        @Override
        public StringBuffer getRetainContent(RetainModel retain) {
            return retain.getMethodData();
        }
    },
    Other("other") {
        @Override
        public StringBuffer getRetainContent(RetainModel retain) {
            return retain.getOtherData();
        }
    };

    private final String name;

    RetainType(String name) {
        this.name = name;
    }

    public abstract StringBuffer getRetainContent(RetainModel retain);

    public static RetainType get(String retainTypeName) {
        for (RetainType type : RetainType.values()) {
            if (type.name.equals(retainTypeName)) {
                return type;
            }
        }
        throw new IllegalStateException("i do not defind this retain type, the error type name is " + retainTypeName);
    }
}