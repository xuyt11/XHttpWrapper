package cn.ytxu.http_wrapper.common.enums;

import cn.ytxu.http_wrapper.template_engine.parser.statement.record.retain.RetainModel;

/**
 * 在自动生成的代码文件中，由程序员根据情况，自己手写的代码；
 * 而这部分代码，在下次运行时，会保留下来；
 * 而在其他地方的改动，都会被清除
 */
public enum RetainType {
    Import("import", "//** xhwt.import */") {
        @Override
        protected StringBuffer getRetainContent(RetainModel retain) {
            return retain.getImportRetainContent();
        }

        @Override
        public void appendRetainContent(RetainModel retain, StringBuffer retainContent) {
            retain.appendImport(retainContent);
        }
    },
    Field("field", "//** xhwt.field */") {
        @Override
        protected StringBuffer getRetainContent(RetainModel retain) {
            return retain.getFieldRetainContent();
        }

        @Override
        public void appendRetainContent(RetainModel retain, StringBuffer retainContent) {
            retain.appendField(retainContent);
        }
    },
    Method("method", "//** xhwt.method */") {
        @Override
        protected StringBuffer getRetainContent(RetainModel retain) {
            return retain.getMethodRetainContent();
        }

        @Override
        public void appendRetainContent(RetainModel retain, StringBuffer retainContent) {
            retain.appendMethod(retainContent);
        }
    },
    Other("other", "//** xhwt.other */") {
        @Override
        protected StringBuffer getRetainContent(RetainModel retain) {
            return retain.getOtherRetainContent();
        }

        @Override
        public void appendRetainContent(RetainModel retain, StringBuffer retainContent) {
            retain.appendOther(retainContent);
        }
    };

    private final String name;
    private final String tag;

    public static final String StartTag = "//** xhwt.retain-start */";
    public static final String EndTag = "//** xhwt.retain-end */";

    RetainType(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    public StringBuffer getFormatRetainContent(RetainModel retain) {
        StringBuffer formatRetainContent = new StringBuffer();
        // retain start tag
        formatRetainContent.append(StartTag).append(tag).append("\n");
        // retain data
        formatRetainContent.append(getRetainContent(retain));
        // retain end tag
        formatRetainContent.append(EndTag).append("\n");
        return formatRetainContent;
    }

    protected abstract StringBuffer getRetainContent(RetainModel retain);

    public abstract void appendRetainContent(RetainModel retain, StringBuffer retainContent);


    public static RetainType get(String retainTypeName) {
        for (RetainType type : RetainType.values()) {
            if (type.name.equals(retainTypeName)) {
                return type;
            }
        }
        throw new IllegalStateException("i do not defind this retain type, the error type name is " + retainTypeName);
    }

    public static RetainType getByTag(String lineText) {
        for (RetainType type : RetainType.values()) {
            if (lineText.contains(type.tag)) {
                return type;
            }
        }
        // contains enums tag or not, but it is all enums category retain data
        return Other;
    }

}