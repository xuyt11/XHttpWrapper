package cn.ytxu.apacer.fileCreater.newchama.requestInterface.v2;


import cn.ytxu.apacer.Config;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.MethodEntity;

import java.util.List;

/**
 * 方法(method)生成器
 * @author ytxu 2016-3-21
 *
 */
public class MethodCreater {
	private static String currTabStr = Config.TabKeyStr;

	public static String create(MethodEntity method, boolean isSameButOnlyVersionCode) {
		if (null == method) {
			return null;
		}
		
		currTabStr = isSameButOnlyVersionCode ? Config.TabKeyNoUseStr : Config.TabKeyStr;
		
		StringBuffer sb = new StringBuffer();
		
		generatorMethodNotes(sb, method, isSameButOnlyVersionCode);// notes
		generatorMethodTitle(sb, method, isSameButOnlyVersionCode);// method start
		
		sb.append(currTabStr).append("\tNetRequest request = new NetRequest();\n");
		sb.append(currTabStr).append("\trequest.setMethod(Method.").append(method.getMethodType().toUpperCase()).append(");\n\n");
		
		// set url, and need parse it if contains{xx_id}\[ios|android|web]\{YYYY-MM-DD}
		RESTfulRequestUrlCreater.generatorRequestUrl(sb, method, isSameButOnlyVersionCode);
		
		FieldCreater.generatorRequestHeader(sb, method, isSameButOnlyVersionCode);// headers
		FieldCreater.generatorRequestParam(sb, method, isSameButOnlyVersionCode);// input params
		
		// TODO out params parser
		
		// method end
		sb.append(currTabStr).append("\treturn NetWorker.execute(").append(Config.Api.AndroidContextParamName).append(", request, response);\n");
		sb.append(currTabStr).append("}\n\n");
		
		return sb.toString();
	}
	
	private static void generatorMethodNotes(StringBuffer sb, MethodEntity method, boolean isSameButOnlyVersionCode) {
		sb.append(currTabStr).append("/**\n");
		sb.append(currTabStr).append(" * " + method.getDescrption() + "\n");
		sb.append(currTabStr).append(" * @version ").append(method.getVersionCode()).append("\n");
		sb.append(currTabStr).append(" * @requestUrl ").append(method.getUrl()).append("\n");

//		list--> * filed fieldDesc \n
		List<FieldEntity> headers = method.getHeaders();
		if (null != headers && headers.size() > 0) {
			for(int i=0, count=headers.size(); i<count; i++) {
                generatorNoteParam(sb, headers, i);
            }
			sb.append(currTabStr).append(" * \n");
		}

//		list--> * fieldType fieldKey \n
		List<FieldEntity> inputs = method.getInputParameters();
		if (null != inputs && inputs.size() > 0) {
			for(int i=0, count=inputs.size(); i<count; i++) {
                generatorNoteParam(sb, inputs, i);
            }
            sb.append(currTabStr).append(" * \n");
		}

		sb.append(currTabStr).append(" */\n");
	}

    private static void generatorNoteParam(StringBuffer sb, List<FieldEntity> fields, int index) {
        FieldEntity field = fields.get(index);
        boolean isOptional = field.isOptional();
        sb.append(currTabStr).append(" * @param ").append(field.getKey()).append(" ");
        if (isOptional) {
            sb.append("isOptional ");
        }
        sb.append(field.getDescription()).append("\n");
    }

//	public static RequestHandle methodName(Context context,
//	/** urlParams RESTful*/ String paramName,
//	/** headers */ fieldType fieldKey, 
//	/** fields */ fieldType fieldKey, 
//	ResponseHandlerInterface response) {\n
	/**
	 * 生成方法的头部
	 * @param isSameButOnlyVersionCode 
	 */
	private static void generatorMethodTitle(StringBuffer sb, MethodEntity method, boolean isSameButOnlyVersionCode) {
//		public static RequestHandle methodName(Context context,
		// 将参数名称从context改为cxt001，防止在之后出现参数名称为context的参数。
		sb.append(currTabStr).append("public RequestHandle ").append(method.getMethodName()).append("(Context ").append(Config.Api.AndroidContextParamName).append(", \n");

//		/** urlParams 4 RESTful */ String paramName, \n
		RESTfulRequestUrlCreater.generatorRequestParam(sb, method, isSameButOnlyVersionCode);
		
//		/** headers */ list-->fieldType fieldKey, \n
		List<FieldEntity> headers = method.getHeaders();
		if (null != headers && headers.size() > 0) {
			sb.append(currTabStr).append("\t/** headers */ ");
			for(int i=0, count=headers.size(); i<count; i++) {
				FieldCreater.generatorMethodParam(sb, headers.get(i));
			}
			sb.append("\n");
		}

//		/** fields */ list-->fieldType fieldKey, \n
		List<FieldEntity> inputs = method.getInputParameters();
		if (null != inputs && inputs.size() > 0) {
			sb.append(currTabStr).append("\t/** fields */ ");
			for(int i=0, count=inputs.size(); i<count; i++) {
				FieldCreater.generatorMethodParam(sb, inputs.get(i));
			}
			sb.append("\n");
		}
		
		sb.append(currTabStr).append("\tResponseHandlerInterface response) {\n");
	}
	
	
	
	
	
}

//	/**
//	 * descrption<br>
//	 * @version xxx
//	 * @requestUrl xxx
//	 * list-->@param filed fieldDesc
//	 */
//	public static RequestHandle methodName(Context context,
//	/** urlParams RESTful*/ String paramName,
//	/** headers */ fieldType fieldKey, 
//	/** fields */ fieldType fieldKey, 
//	ResponseHandlerInterface response) {
//		NetRequest request = new NetRequest();
//		request.setMethod(Method. + method.getMethodType().toUpperCase());
//		request.setUrl();
//		list-->request.addHeader(key, value);
//		list-->request.addParam(key, value);
//		request.setUrl(Config.BaseUrl + method.getUrl());
//		
//		return NetWorker.execute(context, request, response);
//	}