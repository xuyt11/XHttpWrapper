package cn.ytxu.apacer.fileCreater.newchama.resultEntity.v2;

import cn.ytxu.apacer.entity.*;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.FileUtil;
import cn.ytxu.util.LogUtil;
import cn.ytxu.apacer.Config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

/**
 * 请求结果中的实体类生成器
 * 2016-04-07
 */
public class ResultEntityCreater {
    private static volatile ResultEntityCreater instance;

    private ResultEntityCreater() {
        super();
    }

    public static ResultEntityCreater getInstance() {
        if (null == instance) {
            synchronized (ResultEntityCreater.class) {
                if (null == instance) {
                    instance = new ResultEntityCreater();
                }
            }
        }
        return instance;
    }

    public void create(CategoryEntity category, ApiEnitity api, DocumentEntity doc) {
        if (null == category) {
            LogUtil.i("this category is null, so do nothing...");
            return;
        }

        String name = category.getName().trim();
        if (null == name) {
            LogUtil.i("the name is null for this category, so do nothing...");
            return;
        }

        List<MethodEntity> methods = category.getMethods();
        if (null == methods || methods.size() <= 0) {
            LogUtil.i("the methods is null or empty for this category, so do nothing...");
            return;
        }

        final String version = api.getFormatVersion();
        String classFileName = FileUtil.getClassFileName(name) + "Entity";
        String classFileFullName = classFileName + ".java";
        Writer writer = null;
        try {
            writer = FileUtil.getWriter(classFileFullName, Config.Entity.getDirPath(version));

            // package and imports
            createPackageAndImports(writer, classFileName, version);

            // result fields and getter and setter method of result fileds
            createResultFieldsForEachMethods(writer, methods, doc);

            // class end
            createClassEnd(writer);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            BaseCreater.closeWriter(writer);
        }
    }

    private void createResultFieldsForEachMethods(Writer writer, List<MethodEntity> methods, DocumentEntity doc) throws IOException {
        MethodEntity preMethod = null;
        for (MethodEntity method : methods) {
            // 只解析第一个(最新版本)的请求返回数据
            boolean isNotSameVersionCodeRequest = MethodEntity.isNotSameVersionCodeRequest(preMethod, method);
            if (isNotSameVersionCodeRequest) {
                continue;
            }

            List<ResponseEntity> responses = method.getResponses();
            if (null == responses) {
                continue;
            }

            createRequestStart(method, writer);
            for (ResponseEntity response : responses) {
                if (!"0".equals(response.getStatusCode())) {// 只将成功的数据做成实体类
                    continue;
                }

                ResponseEntityCreater creater = ResponseEntityCreater.getInstance();
                String entityClassName = CamelCaseUtils.toUpperFirst(method.getMethodName());
                creater.createOneResponseEntity(writer, response, (entityClassName), doc);
            }

            createRequestEnd(writer);
            preMethod = method;
        }
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
        writer.write("\tpublic static class " + entityClassName + " extends ResponseEntity {\n\n");

    }

    private void createRequestEnd(Writer writer) throws IOException {
        writer.write("\n\t}\n");
    }

    //***************************** entity request method end   *****************************



    //***************************** entity file start *****************************
    // package and imports
    protected void createPackageAndImports(Writer writer, String classFileName, String version) throws IOException {
        writer.write("package " + Config.Entity.getPackageName(version) + ";\n\n");

        writer.write("import com.newchama.api.ResponseEntity;\n\n");// 响应的基类
        writer.write("import java.util.List;\n\n");// 每一个都加上List类，防止其中有List参数

        writer.write("\npublic class " + classFileName + " {\n\n");
    }

    // class end
    private void createClassEnd(Writer writer) throws IOException {
        writer.write("\n}");
        writer.flush();
    }


    //***************************** entity file end   *****************************



}
