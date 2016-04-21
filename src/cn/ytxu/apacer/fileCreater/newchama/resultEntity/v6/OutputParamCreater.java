package cn.ytxu.apacer.fileCreater.newchama.resultEntity.v6;

import cn.ytxu.apacer.ConfigV6;
import cn.ytxu.apacer.entity.OutputParamEntity;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.LogUtil;

/**
 * 输出字段与其getter\setter方法生成器
 * 2016-04-11
 */
public class OutputParamCreater {
    private static volatile OutputParamCreater instance;
    private OutputParamCreater() {
        super();
    }
    public static OutputParamCreater getInstance() {
        if (null == instance) {
            synchronized (OutputParamCreater.class) {
                if (null == instance) {
                    instance = new OutputParamCreater();
                }
            }
        }
        return instance;
    }


    /** 创建字段属性与其getter setter
     * @param parent 父字段:若parent是的名称与当前的output一样,且都是数组类型,则参数类型是parent的类型<br>
     *  若parent==null,代表是顶级目录.即是parent为response;且若是response的话,则需要将status_code,message,error,给过滤掉 */
    public void createOutputParam(OutputParamEntity parent, OutputParamEntity output, String parentClassName, int index, boolean needUseParentClassName, StringBuffer fieldSb, StringBuffer getterSb, StringBuffer setterSb) {
        // 需要将status_code,message,error,给过滤掉
        if (null == parent) {// parent`s type is response
            final String name = output.getName();
            for (String filterName : ConfigV6.Entity.BaseResponse.FilterNames) {
                if (filterName.equals(name)) {
                    return;
                }
            }
        }

        index++;
        // object
        if (output.isObject()) {
            createObject(output, parentClassName, index, needUseParentClassName, fieldSb, getterSb, setterSb);
            return;
        }

        // array :parent是的名称与当前的output一样,且都是数组类型,则参数类型是parent的类型
        if (output.isArray()) {
            createArray(parent, output, parentClassName, index, needUseParentClassName, fieldSb, getterSb, setterSb);
            return;
        }

        // base type and String
        createBaseTypeAndString(output, index, fieldSb, getterSb, setterSb);
    }



    //************************** object\array\base type and string judge **************************

    private void createObject(OutputParamEntity output, String parentClassName, int index, boolean needUseParentClassName, StringBuffer fieldSb, StringBuffer getterSb, StringBuffer setterSb) {
        String name = output.getName();
        String bigName = CamelCaseUtils.toCapitalizeCamelCase(name);
//        String type = parentClassName + "." + bigName;
        String type = bigName;
        if (null != output.getCreatedClassName()) {
            type = output.getCreatedClassName();
        }
        //        if ()

        fieldSb.append(createField(index, type, name));
        getterSb.append(createGetter(index, type, name, bigName));
        setterSb.append(createSetter(index, type, name, bigName));
    }

    /** parent是的名称与当前的output一样,且都是数组类型,则参数类型是parent的类型 */
    private void createArray(OutputParamEntity parent, OutputParamEntity output, String parentClassName, int index, boolean needUseParentClassName, StringBuffer fieldSb, StringBuffer getterSb, StringBuffer setterSb) {
        String name = output.getName();
        String bigName = CamelCaseUtils.toCapitalizeCamelCase(name);
        String type = output.getType();
        if (null == type) {
            type = "?";
        } else if ("Object".equals(type)) {
            // parent是的名称与当前的output一样,且都是数组类型,则参数类型是parent的类型
            if (null != parent && parent.isArray() && parent.getName().equalsIgnoreCase(name)) {
                type = parentClassName;
            } else {
                type = bigName + "4" + parentClassName;
            }
            if (null != output.getCreatedClassName()) {
                type = output.getCreatedClassName();
            }
        } else if ("Number".equals(type)) {
            type = "Long";
        } else if ("String".equals(type)) {
            type = "String";
        } else if ("Boolean".equals(type)) {
            type = "Boolean";
        } else {
            // i do not know type
//                throw new RuntimeException("i do not know type:" + type + ", this output is " + output.toString());
            LogUtil.i("i do not know type:" + type + ", this output is " + output.toString());
        }
        type = "List<" + type + ">";
        fieldSb.append(createField(index, type, name));
        getterSb.append(createGetter(index, type, name, bigName));
        setterSb.append(createSetter(index, type, name, bigName));
    }

    private void createBaseTypeAndString(OutputParamEntity output, int index, StringBuffer fieldSb, StringBuffer getterSb, StringBuffer setterSb) {
        String name = output.getName();
        String bigName = CamelCaseUtils.toCapitalizeCamelCase(name);
        String type = output.getType();
        if ("Number".equals(type)) {
            type = "long";
        } else if ("String".equals(type)) {
            type = "String";
        } else if ("Boolean".equals(type)) {
            type = "boolean";
        } else {
            // i do not know type
            throw new RuntimeException("i do not know type:" + type + ", this output is " + output.toString());
        }
        fieldSb.append(createField(index, type, name));
        getterSb.append(createGetter(index, type, name, bigName));
        setterSb.append(createSetter(index, type, name, bigName));
    }



    //************************** field getter setter creater **************************

    public static StringBuffer createGetter(int index, String type, String name, String bigName) {
        final String table = BaseCreater.getTable(index);
        StringBuffer sb = new StringBuffer(table);
        sb.append("public ").append(type).append(" get").append(bigName).append("() {return ").append(name).append(";}\n");
        return sb;
    }

    public static StringBuffer createSetter(int index, String type, String name, String bigName) {
        final String table = BaseCreater.getTable(index);
        StringBuffer sb = new StringBuffer(table);
        sb.append("public void set").append(bigName).append("(").append(type).append(" ").append(name).append(") {this.").append(name).append(" = ").append(name).append(";}\n");
        return sb;
    }

    public static StringBuffer createField(int index, String type, String name) {
        final String table = BaseCreater.getTable(index);
        return new StringBuffer(table + "private " + type + " " + name + ";\n");
    }

}
