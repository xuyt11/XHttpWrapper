package cn.ytxu.apacer.templateParser;

import cn.ytxu.util.LogUtil;
import cn.ytxu.apacer.entity.CategoryEntity;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.MethodEntity;
import cn.ytxu.apacer.entity.RESTfulApiEntity;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by newchama on 16/3/29.
 */
public class TemplateMethodCreater {

    public static void create(CategoryEntity category, List<String> methodContents, Writer writer) throws IOException {
        List<MethodEntity> methods = category.getMethods();
        if (null == methods || methods.size() <= 0) {
            LogUtil.i("the methods is null or empty for this category, so do nothing...");
            return;
        }

        if (null == methodContents || methodContents.size() <= 0) {
            LogUtil.i("the methodContents is null or empty for this template file, so do nothing...");
            return;
        }

        for (MethodEntity method : methods) {
            String methodCode = createOneMethodCode(method, methodContents);
            writer.write(methodCode);
        }
    }

    private static String createOneMethodCode(MethodEntity method, List<String> methodContents) {
        StringBuffer methodContentSb = new StringBuffer();

        boolean ifStart = false;// if判断是否开始
        boolean isUrlParser = false;
        for (String methodContent :  methodContents) {
//            if (methodContent.contains("{ytxu.if}")) {
//                ifStart = true;
//                isUrlParser = methodContent.contains("{ytxu.url}");
//            } else if (ifStart) {
//                if (methodContent.contains("{ytxu.else}")) {
//
//                } else if (methodContent.contains("{ytxu.ifend}")) {// if end, so go to parserApiDocHtmlCode2DocumentEntity
//                    ifStart = false;
//                } else {
//
//                }
            // 是否有该参数,若参数为空,则该行不输入
            if (methodContent.contains("{ytxu.ifHas}")) {
                if (methodContent.contains("{ytxu.RESTful}")) {
                    List<RESTfulApiEntity> restfuls = method.getRESTfulApis();
                    if (null == restfuls || restfuls.size() <= 0) {
                        continue;
                    }
                    methodContentSb.append(methodContent.replace("{ytxu.ifHas}", "").replace("{ytxu.RESTful}", "")).append("\n");
                } else if (methodContent.contains("{ytxu.headers}")) {
                    List<FieldEntity> headers = method.getHeaders();
                    if (null == headers || headers.size() <= 0) {
                        continue;
                    }
                    methodContentSb.append(methodContent.replace("{ytxu.ifHas}", "").replace("{ytxu.headers}", "")).append("\n");
                } else if (methodContent.contains("{ytxu.fields}")) {
                    List<FieldEntity> fields = method.getInputParameters();
                    if (null == fields || fields.size() <= 0) {
                        continue;
                    }
                    methodContentSb.append(methodContent.replace("{ytxu.ifHas}", "").replace("{ytxu.fields}", "")).append("\n");
                }

            // simple replace text
            } else if (methodContent.contains("{ytxu.description}")) {
                methodContentSb.append(methodContent.replace("{ytxu.description}", method.getDescrption())).append("\n");
            } else if (methodContent.contains("{ytxu.version}")) {
                methodContentSb.append(methodContent.replace("{ytxu.version}", method.getVersionCode())).append("\n");
            } else if (methodContent.contains("{ytxu.requestUrl}")) {
                methodContentSb.append(methodContent.replace("{ytxu.requestUrl}", method.getUrl())).append("\n");
            } else if (methodContent.contains("{ytxu.methodName}")) {
                methodContentSb.append(methodContent.replace("{ytxu.methodName}", method.getMethodName())).append("\n");
            } else if (methodContent.contains("{ytxu.method}")) {
                methodContentSb.append(methodContent.replace("{ytxu.method}", method.getMethodType().toUpperCase())).append("\n");

            // list headers or fields or RESTfuls
            } else if (methodContent.contains("{ytxu.list}")) {
                if (methodContent.contains("{ytxu.headers}")) {
                    generatorHeaders(method, methodContentSb, methodContent);
                } else if (methodContent.contains("{ytxu.fields}")) {
                    generatorFields(method, methodContentSb, methodContent);
                } else if (methodContent.contains("{ytxu.RESTfuls}")) {
                    generatorRESTfuls(method, methodContentSb, methodContent);
                }
            // else normal text
            } else {
                methodContentSb.append(methodContent).append("\n");
            }
        }

        return methodContentSb.toString();
    }

    private static void generatorRESTfuls(MethodEntity method, StringBuffer methodContentSb, String methodContent) {
        boolean isOneLine = methodContent.contains("{ytxu.OneLine}");
        String contentStartStr = null;
        if (isOneLine) {
            contentStartStr = methodContent.substring(0, methodContent.indexOf("{ytxu.list}"));
            if (null != contentStartStr) {
                methodContent = methodContent.replace(contentStartStr, "");
            }
        }

        methodContent = methodContent.replace("{ytxu.list}", "").replace("{ytxu.RESTfuls}", "").replace("{ytxu.OneLine}", "");
        StringBuffer fieldsSb = TemplateFieldCreater.createRESTfuls(method, methodContent, isOneLine, contentStartStr);

        if (null != fieldsSb) {
            methodContentSb.append(fieldsSb);
            if (isOneLine) {
                methodContentSb.append("\n");
            }
        }
    }

    private static void generatorFields(MethodEntity method, StringBuffer methodContentSb, String methodContent) {
        boolean isOneLine = methodContent.contains("{ytxu.OneLine}");
        String contentStartStr = null;
        if (isOneLine) {
            contentStartStr = methodContent.substring(0, methodContent.indexOf("{ytxu.list}"));
            if (null != contentStartStr) {
                methodContent = methodContent.replace(contentStartStr, "");
            }
        }

        methodContent = methodContent.replace("{ytxu.list}", "").replace("{ytxu.fields}", "").replace("{ytxu.OneLine}", "");
        StringBuffer fieldsSb = TemplateFieldCreater.createFields(method, methodContent, isOneLine, contentStartStr);

        if (null != fieldsSb) {
            methodContentSb.append(fieldsSb);
            if (isOneLine) {
                methodContentSb.append("\n");
            }
        }
    }

    private static void generatorHeaders(MethodEntity method, StringBuffer methodContentSb, String methodContent) {
        boolean isOneLine = methodContent.contains("{ytxu.OneLine}");
        String contentStartStr = null;
        if (isOneLine) {
            contentStartStr = methodContent.substring(0, methodContent.indexOf("{ytxu.list}"));
            if (null != contentStartStr) {
                methodContent = methodContent.replace(contentStartStr, "");
            }
        }

        methodContent = methodContent.replace("{ytxu.list}", "").replace("{ytxu.headers}", "").replace("{ytxu.OneLine}", "");
        StringBuffer fieldsSb = TemplateFieldCreater.createHeaders(method, methodContent, isOneLine, contentStartStr);

        if (null != fieldsSb) {
            methodContentSb.append(fieldsSb);
            if (isOneLine) {
                methodContentSb.append("\n");
            }
        }
    }

}
