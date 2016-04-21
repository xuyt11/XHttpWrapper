package cn.ytxu.apacer.fileCreater.newchama.resultEntity.v3;

import cn.ytxu.apacer.entity.*;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.LogUtil;
import cn.ytxu.apacer.Config;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * 响应对象生成器
 * 2016-04-07
 */
public class ResponseEntityCreater {
    private static volatile ResponseEntityCreater instance;
    private ResponseEntityCreater() {
        super();
    }
    public static ResponseEntityCreater getInstance() {
        if (null == instance) {
            synchronized (ResponseEntityCreater.class) {
                if (null == instance) {
                    instance = new ResponseEntityCreater();
                }
            }
        }
        return instance;
    }


    public void createOneResponseEntity(Writer writer, ResponseEntity response, DocumentEntity doc, MethodEntity method) throws IOException {
        int index = 1;

        List<StatusCodeEntity> statusCodes = doc.getStatusCodes();
        String statusCode = response.getStatusCode();
        StatusCodeEntity entity = StatusCodeEntity.getTarget(statusCodes, statusCode);
        if (null == entity) {
            writer.write("\n");
            return;
        }

        // 获取到response的data字段
        List<OutputParamEntity> outputs = response.getOutputParams();
        if (null == outputs || outputs.size() <= 0) {
            return;
        }

        OutputParamEntity dataOutput = null;
        for (OutputParamEntity output : outputs) {
            if (Config.Entity.BaseResponse.Data.equals(output.getName())) {
                dataOutput = output;
                break;
            }
        }
        if (null == dataOutput) {
            return;
        }

        String entityClassName = CamelCaseUtils.toUpperFirst(method.getMethodName());
        createRequestStart(method, writer);
        createOutputClass(writer, null, dataOutput, entityClassName, index);
        createRequestEnd(writer);
    }

    /** @param parent 若parent的名称与当前的output一样,且都是数组类型,则不生成该class */
    private void createOutputsClass(Writer writer, OutputParamEntity parent, List<OutputParamEntity> outputs, String parentClassName, int index) throws IOException {
        index++;
        final String table = BaseCreater.getTable(index);

        for (OutputParamEntity output : outputs) {
            // 既不是数组又不是对象,那就是基本类型与String,而这些类型不需要去自定义类
            if (!output.isArray() && !output.isObject()) {
                continue;
            }

            // 若parent的名称与当前的output一样,且都是数组类型,则不生成该class
            if (null != parent && parent.isArray() && output.isArray() && parent.getName().equalsIgnoreCase(output.getName())) {
                LogUtil.w("parent的名称与当前的output一样,且都是数组类型,则不生成该output的class, and parentClassName is " + parentClassName);
                continue;
            }

            String className = CamelCaseUtils.toCapitalizeCamelCase(output.getName());
            writer.write(table + "public static class " + className + " {\n");

            createOutputClass(writer, parent, output, (parentClassName + "." + className), index);

            writer.write(table + "}\n");
        }
    }

    /** @param parent 若parent是null,则代表这个output是response中的,而不是在output中的sub */
    private void createOutputClass(Writer writer, OutputParamEntity parent, OutputParamEntity outputParam, String parentClassName, int index) throws IOException {
        List<OutputParamEntity> outputs = outputParam.getSubs();
        if (null == outputs || outputs.size() <= 0) {
            return;
        }

        StringBuffer fieldSb = new StringBuffer(), getterSb = new StringBuffer(), setterSb = new StringBuffer();

        // 创建字段属性与其getter setter
        for (OutputParamEntity output : outputs) {
            OutputParamCreater.getInstance().createOutputParam(outputParam, output, parentClassName, index, fieldSb, getterSb, setterSb);
        }

        writer.write(fieldSb.toString());
        writer.write("\n");
        writer.write(getterSb.toString());
        writer.write(setterSb.toString());
        writer.write("\n");

        // 创建对象类型的字段的class
        createOutputsClass(writer, outputParam, outputs, parentClassName, index);
    }




    //***************************** entity request method start *****************************
    private void createRequestStart(MethodEntity method, Writer writer) throws IOException {
        String entityClassName = CamelCaseUtils.toUpperFirst(method.getMethodName());
        String entityCurrVersionCode = method.getVersionCode();
        String entityDesc = method.getDescrption();

        writer.write("\n");
        writer.write("\t/**\n");
        writer.write("\t * @version " + entityCurrVersionCode + "\n");
        writer.write("\t * @requestDesc " + entityDesc + "\n");
        writer.write("\t */\n");
        writer.write("\tpublic static class " + entityClassName + " {\n\n");

    }

    private void createRequestEnd(Writer writer) throws IOException {
        writer.write("\n\t}\n");
    }

    //***************************** entity request method end   *****************************


}
