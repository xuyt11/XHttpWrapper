//package com.newchama.apiUtil.fileCreater.abs;
//
//import cn.ytxu.util.FileUtil;
//import cn.ytxu.util.LogUtil;
//import cn.ytxu.apacer.Config;
//import CategoryEntity;
//import MethodEntity;
//import MethodCreater;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.io.Writer;
//import java.util.List;
//
///**
// * a abstract file creater for api request method file and api result entity file
// * 2016-04-07
// */
//public abstract class AbsFileCreater {
//    private Writer writer = null;
//
//    public void create(CategoryEntity category) {
//        if (null == category) {
//            LogUtil.i("this category is null, so do nothing...");
//            return;
//        }
//
//        String name = category.getName().trim();
//        if (null == name) {
//            LogUtil.i("the name is null for this category, so do nothing...");
//            return;
//        }
//
//        List<MethodEntity> methods = category.getMethods();
//        if (null == methods || methods.size() <= 0) {
//            LogUtil.i("the methods is null or empty for this category, so do nothing...");
//            return;
//        }
//
//        String classFileName = FileUtil.getClassFileName(name);
//        String classFileFullName = classFileName + ".java";
//        Writer writer = null;
//        try {
//            writer = FileUtil.getWriter(classFileFullName, Config.Api.DirPath);
//
//            // package
//            writer.write("package " + Config.Api.PackageName + ";\n\n");
//            // imports
//            createImports(writer);
//            // class start, create private a default constructor and a public registerInstance method, and thrie params
//            createConstructorAndRegisterInstance(writer, category, classFileName);
//            // methods
//            createMethods(methods, writer);
//
//            // class end
//            writer.write("\n}");
//            writer.flush();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != writer) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//    // package
//    protected void createPackage(Writer writer, String packageName) throws IOException {
//        writer.write("package " + packageName + ";\n\n");
//    }
//
//    // imports
//    protected abstract void createImports(Writer writer) throws IOException;
//
//    // class start, create private a default constructor and a public registerInstance method, and their params
//    private void createConstructorAndRegisterInstance(Writer writer, CategoryEntity category, String classFileName) throws IOException {
//        // class start
//        writer.write("\npublic class " + classFileName + " extends " + Config.Api.BaseApiFileName + " {\n\n");
//
//        writer.write("\tprivate volatile static " + classFileName + " instance;\n");
//        // private constructor
//        writer.write("\tprivate " + classFileName + "() {}\n");
//        // public register instance method
//        writer.write("\tpublic static void " + Config.Api.RegisterInstanceMethodName + "() {\n");
//        writer.write("\t\tif (instance == null) {\n");
//        writer.write("\t\t\tsynchronized (" + classFileName + ".class) {\n");
//        writer.write("\t\t\t\tif (instance == null) {\n");
//        writer.write("\t\t\t\t\tinstance = new " + classFileName + "();\n");
//        writer.write("\t\t\t\t}\n");
//        writer.write("\t\t\t}\n");
//        writer.write("\t\t}\n");
//        writer.write("\t\t" + Config.Api.PublicApiFileName + ".set" + classFileName + "(instance);\n");
//        writer.write("\t}\n\n");
//
//    }
//
//    private void createMethods(List<MethodEntity> methods, Writer writer) throws IOException {
//        MethodEntity preMethod = null;
//        for (MethodEntity method : methods) {
//            boolean isSameButOnlyVersionCode = MethodEntity.isSameMethodButOnlyVersionCode(preMethod, method);
//            String methodStr = MethodCreater.create(method, isSameButOnlyVersionCode);
//            if (null != methodStr) {
//                writer.write(methodStr);
//            }
//            preMethod = method;
//        }
//    }
//
//
//}
