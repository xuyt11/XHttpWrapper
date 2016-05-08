package cn.ytxu.apacer.fileCreater.newchama.resultEntity.v6;

import cn.ytxu.apacer.system_platform.Config;
import cn.ytxu.apacer.entity.MethodEntity;
import cn.ytxu.apacer.entity.OutputParamEntity;
import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.LogUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by newchama on 16/4/14.
 */
public class OutputClassCreater {
    private static volatile OutputClassCreater instance;
    private OutputClassCreater() {
        super();
    }
    public static OutputClassCreater getInstance() {
        if (null == instance) {
            synchronized (OutputClassCreater.class) {
                if (null == instance) {
                    instance = new OutputClassCreater();
                }
            }
        }
        return instance;
    }


    // 3.2 在field中,若类型(type)是Object或Array类型的,需要判断usedClassName,
    //      若不为空,代表该class生成时类名是该值.所以,该field的type也要为该值.
    public void create(final OutputParamEntity output, final String packageName, String dirPath) {
        final int tabIndex = 0;
        final String className;
        if (null == output.getParent()) {// data
            className = output.getDataClassName();
        } else if (null != output.getCreatedClassName()) {// sub rename
            className = output.getCreatedClassName();
        } else {// sub
            className = CamelCaseUtils.toCapitalizeCamelCase(output.getName());
        }

        String fileName = className + ".java";
        BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
            createPackageAndImports(writer, output, retain, packageName, className, tabIndex);

            List<OutputParamEntity> subs = output.getSubs();
            if (null != subs && subs.size() > 0) {
                StringBuffer fieldSb = new StringBuffer(), getterSb = new StringBuffer(), setterSb = new StringBuffer();
                for (OutputParamEntity sub : subs) {
                    createOutputParam(sub, tabIndex, fieldSb, getterSb, setterSb);
                }
                writer.write(fieldSb.toString());
                if (null != retain) {
                    writer.write("\n");
                    writer.write(retain.getFieldData().toString());
                }
                writer.write("\n");

                writer.write(getterSb.toString());
                writer.write(setterSb.toString());
                if (null != retain) {
                    writer.write("\n");
                    writer.write(retain.getMethodData().toString());
                    writer.write("\n");
                    writer.write(retain.getOtherData().toString());
                    writer.write("\n");
                }
            }

            createClassEnd(writer, tabIndex);
        });
    }


    // package and imports
    private void createPackageAndImports(Writer writer, OutputParamEntity output, RetainEntity retain, String packageName, String className, int tabIndex) throws IOException {
        writer.write("package " + packageName + ";\n\n");
        writer.write("import java.util.List;\n\n");// 每一个都加上List类，防止其中有List参数
        if (null != retain) {
            writer.write(retain.getImportData().toString());
            writer.write("\n");
        }

        String tab = BaseCreater.getTable(tabIndex);
        
        // set method request tip node
        if (output.getResponse() != null && output.getResponse().getMethod() != null) {
            MethodEntity method = output.getResponse().getMethod();
            writer.write(tab + "/**\n");
            writer.write(tab + " * " + method.getDescrption() + "\n");
            writer.write(tab + " * @version " + method.getVersionCode() + "\n");
            writer.write(tab + " * @requestUrl " + method.getUrl() + "\n");
            writer.write(tab + " */\n");
        }

        writer.write(tab + "public class " + className + " {\n\n");
    }

    // class end
    private void createClassEnd(Writer writer, int tabIndex) throws IOException {
        String tab = BaseCreater.getTable(tabIndex);

        writer.write(tab + "}\n\n");
        writer.flush();
    }

    /** 创建字段属性与其getter setter */
    private void createOutputParam(OutputParamEntity output, int tabIndex, StringBuffer fieldSb, StringBuffer getterSb, StringBuffer setterSb) {
        // 需要将status_code,message,error,给过滤掉
        if (null == output.getParent()) {// parent`s type is response
            final String name = output.getName();
            for (String filterName : Config.BaseResponse.FilterNames) {
                if (filterName.equals(name)) {
                    return;
                }
            }
        }

        tabIndex++;
        String name = output.getName();
        String bigName = CamelCaseUtils.toCapitalizeCamelCase(name);

        String type;
        if (output.isObject()) {// object
            type = getType4Object(output);
        } else if (output.isArray()) {// array :parent是的名称与当前的output一样,且都是数组类型,则参数类型是parent的类型
            type = getType4Array(output);
        } else {// base type and String
            type = getType4BaseTypeAndString(output);
        }
        createFieldAndGetterAndSetter(tabIndex, type, name, bigName, fieldSb, getterSb, setterSb);
    }


    //************************** object\array\base type and string judge **************************

    private String getType4Object(OutputParamEntity output) {
        String name = output.getName();
        String bigName = CamelCaseUtils.toCapitalizeCamelCase(name);
        String type = bigName;
        if (null != output.getCreatedClassName()) {
            type = output.getCreatedClassName();
        }
        return type;
    }

    /** parent是的名称与当前的output一样,且都是数组类型,则参数类型是parent的类型 */
    private String getType4Array(OutputParamEntity output) {
        String name = output.getName();
        String bigName = CamelCaseUtils.toCapitalizeCamelCase(name);
        String type = output.getType();
        if (null == type) {
            type = "?";
        } else if ("Number".equals(type)) {
            type = "Long";
        } else if ("String".equals(type)) {
            type = "String";
        } else if ("Boolean".equals(type)) {
            type = "Boolean";
        } else if ("Object".equals(type)) {
            // parent是的名称与当前的output一样,且都是数组类型,则参数类型是parent的类型
            if (null == output.getParent()) {// data
                type = output.getDataClassName();
            } else if (null != output.getCreatedClassName()) {// sub rename
                type = output.getCreatedClassName();
            } else {// sub
                type = bigName;
            }
        } else {
            LogUtil.i("i do not know type:" + type + ", this output is " + output.toString());
        }
        type = "List<" + type + ">";
        return type;
    }

    private String  getType4BaseTypeAndString(OutputParamEntity output) {
        String type = output.getType();
        if ("Number".equals(type)) {
            type = "long";
        } else if ("String".equals(type)) {
            type = "String";
        } else if ("Boolean".equals(type)) {
            type = "boolean";
        } else {
            throw new RuntimeException("i do not know type:" + type + ", this output is " + output.toString());
        }
        return type;
    }


    //************************** getter setter field creater **************************

    private static void createFieldAndGetterAndSetter(int index, String type, String name, String bigName, StringBuffer fieldSb, StringBuffer getterSb, StringBuffer setterSb) {
        fieldSb.append(createField(index, type, name));
        getterSb.append(createGetter(index, type, name, bigName));
        setterSb.append(createSetter(index, type, name, bigName));
    }

    private static StringBuffer createGetter(int index, String type, String name, String bigName) {
        final String table = BaseCreater.getTable(index);
        StringBuffer sb = new StringBuffer(table);
        sb.append("public ").append(type).append(" get").append(bigName).append("() {return ").append(name).append(";}\n");
        return sb;
    }

    private static StringBuffer createSetter(int index, String type, String name, String bigName) {
        final String table = BaseCreater.getTable(index);
        StringBuffer sb = new StringBuffer(table);
        sb.append("public void set").append(bigName).append("(").append(type).append(" ").append(name).append(") {this.").append(name).append(" = ").append(name).append(";}\n");
        return sb;
    }

    private static StringBuffer createField(int index, String type, String name) {
        final String table = BaseCreater.getTable(index);
        return new StringBuffer(table + "private " + type + " " + name + ";\n");
    }

}
