package cn.ytxu.apacer.fileCreater.newchama.requestInterface.v2;

import cn.ytxu.util.TextUtil;
import cn.ytxu.apacer.Config;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.MethodEntity;

import java.util.List;

/**
 * 字段(field)生成器
 * @author ytxu 2016-3-21
 */
public class FieldCreater {
	
	/**
	 * 生成方法的形参，格式：fieldType fieldKey,<br>
	 */
	public static void generatorMethodParam(StringBuffer sb, FieldEntity field) {
		String type = field.getType();
		if (TextUtil.isBlank(type)) {
			return;
		}
		
		String key = field.getKey();
		if (Config.TokenName_Authorization.equalsIgnoreCase(key)) {// 过滤掉Token
			sb.append(" /**").append(type).append(" ").append(key).append(",*/");
			return;
		}
		
		if (type.equalsIgnoreCase("String")) {
			type = "String";
		} else if (type.equalsIgnoreCase("Number")) {
			type = "Long";// 不能是long,要不为可选类型的时候,就不能是用空判断了,而值类型的也不知道如何去判断了
		} else if (type.equalsIgnoreCase("Date")) {// 需要通过SimpleDateForm转换成String，具体的还要看需求
//			type = "Date";
		} else if (type.equalsIgnoreCase("List") || type.equalsIgnoreCase("Array")) {// 是否需要转成String？
			type = "List";
		}
		
		sb.append(" ").append(type).append(" ").append(field.getKey()).append(",");
	}
	
	/**
	 * 生成请求的头部字段，list-->request.addHeader(key, value);<br>
	 */
	public static void generatorRequestHeader(StringBuffer sb, MethodEntity method, boolean isSameButOnlyVersionCode) {
		List<FieldEntity> headers = method.getHeaders();
		if (null == headers || headers.size() <= 0) {
			return;
		}

		String currTabStr = isSameButOnlyVersionCode ? Config.TabKeyNoUseStr : Config.TabKeyStr;
		for (FieldEntity header : headers) {
			String headerParamName = header.getKey();
			// 过滤掉Token，设置工作交给获取到的时候，由程序员，直接在HttpClient SingleInstance中设置到header中去
			if (Config.TokenName_Authorization.equalsIgnoreCase(headerParamName)) {
				sb.append("//");
				sb.append(currTabStr).append("\trequest.addHeader(\"").append(headerParamName).append("\", ")
					.append(headerParamName).append(");");
				sb.append("// 过滤掉Token，设置工作交给获取到的时候，由程序员，直接在HttpClient SingleInstance中设置到header中去\n");// add note
			} else {
				sb.append(currTabStr).append("\trequest.addHeader(\"").append(headerParamName).append("\", ")
					.append(headerParamName).append(");\n");
			}
		}
		sb.append("\t\t\n");
	}
	
	/**
	 * 生成请求的参数字段，list-->request.addParam(key, value);<br>
	 */
	public static void generatorRequestParam(StringBuffer sb, MethodEntity method, boolean isSameButOnlyVersionCode) {
		List<FieldEntity> inputs = method.getInputParameters();
		if (null == inputs || inputs.size() <= 0) {
			return;
		}

		String currTabStr = isSameButOnlyVersionCode ? Config.TabKeyNoUseStr : Config.TabKeyStr;
		for (FieldEntity param : inputs) {
			generatorRequestParam(sb, currTabStr, param);
		}
		sb.append(currTabStr).append("\t\n");
	}

	private static void generatorRequestParam(StringBuffer sb, String currTabStr, FieldEntity param) {
		boolean isOptional = param.isOptional();
		String paramName = param.getKey();

		sb.append(currTabStr).append("\t");
		if (isOptional) {// 是否为可选字段
			sb.append("if (null != ").append(paramName).append(") ");
		}
		sb.append("request.addParam(\"").append(paramName).append("\", ");
		if (isListType(param)) {
            sb.append("new JSONArray(").append(paramName).append(").toString()");
        } else {
            sb.append(paramName);
        }
		sb.append(");\n");
	}

	private static boolean isListType(FieldEntity field) {
		String type = field.getType();
		if (TextUtil.isBlank(type)) {
			return false;
		}
		
		return type.equalsIgnoreCase("List") || type.equalsIgnoreCase("Array");
	}
	

}