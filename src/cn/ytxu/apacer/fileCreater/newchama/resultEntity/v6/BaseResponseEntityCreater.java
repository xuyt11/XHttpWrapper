package cn.ytxu.apacer.fileCreater.newchama.resultEntity.v6;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.dataParser.apidocjsParser.v7.OutputParamsParser;
import cn.ytxu.apacer.entity.*;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.util.CamelCaseUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * base response entity creater
 * 2016-04-12
 */
public class BaseResponseEntityCreater {
    private static volatile BaseResponseEntityCreater instance;
    private BaseResponseEntityCreater() {
        super();
    }
    public static BaseResponseEntityCreater getInstance() {
        if (null == instance) {
            synchronized (BaseResponseEntityCreater.class) {
                if (null == instance) {
                    instance = new BaseResponseEntityCreater();
                }
            }
        }
        return instance;
    }


    public void create(DocumentEntity doc, List<ApiEnitity> apis) {
        List<OutputParamEntity> outputs = getOutputs(apis);

        String classFileName = Config.BaseResponse.ClassName;
        String fileName = classFileName + ".java";
        String dirPath = Config.BaseResponse.DirPath;

        BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
            int tabIndex = 0;
            // package and imports
            createPackageAndImports(writer, retain, classFileName);

            // result fields and getter and setter method of result fileds
            createFieldsAndTheirGetterAndSetter(writer, retain, tabIndex);

            // create Error class
            createErrorClass(writer, outputs, doc, tabIndex);

            if (null != retain) {
                writer.write(retain.getOtherData().toString());
                writer.write("\n");
            }

            // class end
            createClassEnd(writer);
        });
    }

    private void createErrorClass(Writer writer, List<OutputParamEntity> outputs, DocumentEntity doc, int tabIndex) throws IOException {
        tabIndex++;
        final String table = BaseCreater.getTable(tabIndex);
        String fieldName = Config.BaseResponse.Error;
        String className = CamelCaseUtils.toCapitalizeCamelCase(fieldName);
        writer.write(table + "public static class " + className + " {\n\n");

        StringBuffer fieldSb = new StringBuffer(), getterSb = new StringBuffer(), setterSb = new StringBuffer();
        for (OutputParamEntity output : outputs) {
            OutputParamCreater.getInstance().createOutputParam(null, output, className, tabIndex, false, fieldSb, getterSb, setterSb);
        }

        writer.write(fieldSb.toString());
        writer.write("\n");
        writer.write(getterSb.toString());
        writer.write(setterSb.toString());
        writer.write("\n");
        writer.write(table + "}\n\n");
    }

    private void createFieldsAndTheirGetterAndSetter(Writer writer, RetainEntity retain, int tabIndex) throws IOException {
        tabIndex++;
        String[] fieldNames = Config.BaseResponse.FieldNames;// and their type is String
        StringBuffer fieldSb = new StringBuffer(), getterSb = new StringBuffer(), setterSb = new StringBuffer();
        for (String fieldName : fieldNames) {
            String type = "String";
            if (Config.BaseResponse.Error.equals(fieldName)) {
                type = "Error";
            } else if (Config.BaseResponse.Data.equals(fieldName)) {
                type = "T";
            } else if(Config.BaseResponse.StatusCode.equals(fieldName)) {
                type = "int";
            }
            String bigName = CamelCaseUtils.toCapitalizeCamelCase(fieldName);
            fieldSb.append(OutputParamCreater.createField(tabIndex, type, fieldName));
            getterSb.append(OutputParamCreater.createGetter(tabIndex, type, fieldName, bigName));
            setterSb.append(OutputParamCreater.createSetter(tabIndex, type, fieldName, bigName));
        }

        writer.write(fieldSb.toString());
        writer.write("\n");
        if (null != retain) {
            writer.write(retain.getFieldData().toString());
            writer.write("\n");
        }

        writer.write(getterSb.toString());
        writer.write(setterSb.toString());
        writer.write("\n");
        if (null != retain) {
            writer.write(retain.getMethodData().toString());
            writer.write("\n");
        }

    }


    // package and imports
    protected void createPackageAndImports(Writer writer, RetainEntity retain, String classFileName) throws IOException {
        writer.write("package " + Config.BaseResponse.PackageName + ";\n\n");

        writer.write("import java.util.List;\n\n");// 每一个都加上List类，防止其中有List参数
        if (null != retain) {
            writer.write(retain.getImportData().toString());
            writer.write("\n");
        }

        writer.write("\npublic class " + classFileName + "<T> {\n\n");
    }

    // class end
    private void createClassEnd(Writer writer) throws IOException {
        writer.write("\n}");
        writer.flush();
    }


    //************************** parserApiDocHtmlCode2DocumentEntity **************************

    private List<OutputParamEntity> getOutputs(List<ApiEnitity> apis) {
        List<ResponseEntity> responses = getResponseEntities(apis);
        List<JSONObject> errorJObjs = getErrorJObjArr(responses);
        List<Map.Entry<String, Object>> entrys = getEntrys(errorJObjs);

        List<OutputParamEntity> outputs = new ArrayList<>();
        for (Map.Entry<String, Object> entry : entrys) {
            OutputParamEntity output = OutputParamsParser.parserOutputParam(-1, -1, entry);
            outputs.add(output);
        }
        return outputs;
    }

    private List<Map.Entry<String, Object>>  getEntrys(List<JSONObject> errorJObjs) {
        List<Map.Entry<String, Object>> entrys = new ArrayList<>();
        for (JSONObject errorJObj : errorJObjs) {
            if (null == errorJObj) {
                continue;
            }

            for (Map.Entry<String, Object> entry : errorJObj.entrySet()) {
                entrys.add(entry);
            }
        }

        List<String> fieldNames = new ArrayList<>();
        List<Map.Entry<String, Object>> filterEntrys = new ArrayList<>();
        for (Map.Entry<String, Object> entry : entrys) {
            String fieldName = entry.getKey();
            if (null == fieldName || fieldNames.contains(fieldName)) {
                continue;
            }
            filterEntrys.add(entry);
        }

        return filterEntrys;
    }

    private List<JSONObject> getErrorJObjArr(List<ResponseEntity> responses) {
        List<JSONObject> errorJObjs = new ArrayList<>();
        for (ResponseEntity response : responses) {
            if (null == response) {
                continue;
            }

            String jsonStr = response.getResponseContent();
            try {
                JSONObject jObj = JSON.parseObject(jsonStr);
                if (!jObj.containsKey(Config.BaseResponse.Error)) {// TODO 未来要他们统一返回格式,有些result没有这个字段
                    continue;
                }

                JSONObject errorJObj = jObj.getJSONObject(Config.BaseResponse.Error);
                errorJObjs.add(errorJObj);
            } catch (JSONException e) {
                // TODO 这是返回数据的Json格式有问题,以后解决
                e.printStackTrace();
            }
        }
        return errorJObjs;
    }

    private List<ResponseEntity> getResponseEntities(List<ApiEnitity> apis) {
        List<CategoryEntity> cates = new ArrayList<>();
        for (ApiEnitity api : apis) {
            cates.addAll(api.getCategorys());
        }

        List<MethodEntity> methods = new ArrayList<>();
        for (CategoryEntity cate : cates) {
            if (null == cate) {
                continue;
            }

            methods.addAll(cate.getMethods());
        }

        List<ResponseEntity> responses = new ArrayList<>();
        for (MethodEntity method : methods) {
            if (null == method) {
                continue;
            }

            responses.addAll(method.getResponses());
        }
        return responses;
    }


}
