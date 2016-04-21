package cn.ytxu.apacer.templateParser;

import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.MethodEntity;
import cn.ytxu.apacer.entity.RESTfulApiEntity;

import java.util.List;

/**
 * Created by newchama on 16/3/29.
 */
public class TemplateFieldCreater {

    public static StringBuffer createHeaders(MethodEntity method, String methodContent, boolean isOneLine, String contentStartStr) {
        List<FieldEntity> fields = method.getHeaders();
        return create(fields, methodContent, isOneLine, contentStartStr);
    }

    public static StringBuffer createFields(MethodEntity method, String methodContent, boolean isOneLine, String contentStartStr) {
        List<FieldEntity> fields = method.getInputParameters();
        return create(fields, methodContent, isOneLine, contentStartStr);
    }

    private static StringBuffer create(List<FieldEntity> fields, String methodContent, boolean isOneLine, String contentStartStr) {
        if (null == fields || fields.size() <= 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        if (isOneLine) {
            sb.append(contentStartStr);
        }

        String fieldType, fieldName, fieldDesc, fieldContent;
        for (FieldEntity field : fields) {
            field = getTypeInAndroid(field);
            fieldType = field.getType();
            fieldName = field.getKey();
            fieldDesc = field.getDescription();

            fieldContent = methodContent.replace("{ytxu.fieldType}", fieldType).replace("{ytxu.fieldName}", fieldName).replace("{ytxu.fieldDesc}", fieldDesc);
            sb.append(fieldContent);
            if (!isOneLine) {
                sb.append("\n");
            }
        }

        return sb;
    }

    private static FieldEntity getTypeInAndroid(FieldEntity field) {
        if (null == field) return null;

        String type = field.getType();

        if (null == type) return null;

        if (type.equalsIgnoreCase("String")) {
            type = "String";
        } else if (type.equalsIgnoreCase("Number")) {
            type = "Long";// 不能是long,要不为可选类型的时候,就不能是用空判断了,而值类型的也不知道如何去判断了
        } else if (type.equalsIgnoreCase("Date")) {// 需要通过SimpleDateForm转换成String，具体的还要看需求
			type = "String";
            String desc = field.getDescription();
            field.setDescription(desc + ", and the type is Date, so if you don`t know the format, you must look apidoc...");
        } else if (type.equalsIgnoreCase("List") || type.equalsIgnoreCase("Array")) {// 是否需要转成String？
            type = "String";
            String desc = field.getDescription();
            field.setDescription(desc + ", and the type is List, so you need to convert the correct String...");
        }

        field.setType(type);
        return field;
    }

    private static FieldEntity getTypeInIOS(FieldEntity field) {
        // TODO
        return field;
    }


    private static String getTypeForRESTfulParam() {
        return "String";
    }

    public static StringBuffer createRESTfuls(MethodEntity method, String methodContent, boolean isOneLine, String contentStartStr) {
        List<RESTfulApiEntity> restfuls = method.getRESTfulApis();

        if (null == restfuls || restfuls.size() <= 0) {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        if (isOneLine) {
            sb.append(contentStartStr);
        }

        String fieldType, fieldName, fieldContent;
        for (RESTfulApiEntity restful : restfuls) {
            switch (restful.getType()) {
            case staticStr:// not param, so do nothing...
                break;
            case restfulParam:
                fieldType = getTypeForRESTfulParam();
                fieldName = restful.getRestfulParam();
                fieldContent = methodContent.replace("{ytxu.restfulType}", fieldType).replace("{ytxu.restfulParam}", fieldName);
                sb.append(fieldContent);
                if (!isOneLine) {
                    sb.append("\n");
                }
                break;
            default:
                break;
            }
        }

        return sb;
    }
}
