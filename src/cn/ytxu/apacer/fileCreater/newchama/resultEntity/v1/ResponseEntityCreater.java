package cn.ytxu.apacer.fileCreater.newchama.resultEntity.v1;

import cn.ytxu.apacer.entity.DocumentEntity;
import cn.ytxu.apacer.entity.OutputParamEntity;
import cn.ytxu.apacer.entity.ResponseEntity;
import cn.ytxu.apacer.entity.StatusCodeEntity;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.LogUtil;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;

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


    public void createOneResponseEntity(Writer writer, ResponseEntity response, String parentClassName, DocumentEntity doc) throws IOException {
        int index = 2;
        createResponseEntityNote(writer, response, index);

        List<StatusCodeEntity> statusCodes = doc.getStatusCodes();
        String statusCode = response.getStatusCode();
        StatusCodeEntity entity = StatusCodeEntity.getTarget(statusCodes, statusCode);
        if (null == entity) {
            writer.write("\n");
            return;
        }

        final String table = BaseCreater.getTable(index);
        String responseClassName = CamelCaseUtils.toCapitalizeCamelCase(entity.getName());
        writer.write(table + "public static class " + responseClassName + " {\n");

        createResponseEntityClass(writer, response, (parentClassName + "." + responseClassName), index);

        writer.write(table + "}\n");
    }

    private void createResponseEntityNote(Writer writer, ResponseEntity response, int index) throws IOException {
        final String table = BaseCreater.getTable(index);
        writer.write("\n");
        writer.write(table + "/**\n");
        writer.write(table + " * @resultDesc " + response.getResponseDesc() + "\n");
        writer.write(table + " */\n");
    }

    private void createResponseEntityClass(Writer writer, ResponseEntity response, String parentClassName, int index) throws IOException {
        List<OutputParamEntity> outputs = response.getOutputParams();
        if (null == outputs || outputs.size() <= 0) {
            return;
        }

        StringBuffer fieldSb = new StringBuffer(), getterSb = new StringBuffer(), setterSb = new StringBuffer();

        // 创建字段属性与其getter setter
        for (OutputParamEntity output : outputs) {
            OutputParamCreater.getInstance().createOutputParam(null, output, parentClassName, index, fieldSb, getterSb, setterSb);
        }

        writer.write(fieldSb.toString());
        writer.write("\n");
        writer.write(getterSb.toString());
        writer.write(setterSb.toString());
        writer.write("\n");

        // 创建对象类型的字段的class
        createOutputParamClass(writer, null, outputs, parentClassName, index);
    }

    /** @param parent 若parent的名称与当前的output一样,且都是数组类型,则不生成该class */
    private void createOutputParamClass(Writer writer, OutputParamEntity parent, List<OutputParamEntity> outputs, String parentClassName, int index) throws IOException {
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

            createOutputParamClass(writer, output, (parentClassName + "." + className), index);

            writer.write(table + "}\n");
        }
    }

    private void createOutputParamClass(Writer writer, OutputParamEntity outputParam, String parentClassName, int index) throws IOException {
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
        createOutputParamClass(writer, outputParam, outputs, parentClassName, index);
    }



}
