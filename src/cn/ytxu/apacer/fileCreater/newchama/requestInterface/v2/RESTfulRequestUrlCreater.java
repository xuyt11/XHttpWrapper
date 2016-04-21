package cn.ytxu.apacer.fileCreater.newchama.requestInterface.v2;

import cn.ytxu.apacer.Config;
import cn.ytxu.apacer.entity.MethodEntity;
import cn.ytxu.apacer.entity.RESTfulApiEntity;

import java.util.List;

/**
 * RESTful请求路径生成器
 * @author ytxu 2016-3-22
 *
 */
public class RESTfulRequestUrlCreater {

	
	/** 生成请求url 
	 * @param isSameButOnlyVersionCode */
	public static void generatorRequestUrl(StringBuffer sb, MethodEntity method, boolean isSameButOnlyVersionCode) {
		String currTabStr = isSameButOnlyVersionCode ? Config.TabKeyNoUseStr : Config.TabKeyStr;
		List<RESTfulApiEntity> restfuls = method.getRESTfulApis();
		
		if (null == restfuls || restfuls.size() <= 0) {// 没有RESTful风格的东东，所以直接将url搞上去
			sb.append(currTabStr).append("\trequest.setUrl(getFullUrl(\"").append(method.getUrl()).append("\"));\n");
			return;
		}
		
		// RESTful风格的request url，所以需要进行拼接处理
		StringBuffer formatSb = new StringBuffer();
		StringBuffer argsSb = new StringBuffer();
		int count = 0;
		for (RESTfulApiEntity restful : restfuls) {
			switch (restful.getType()) {
			case restfulParam:
				formatSb.append("%s");
				String param = restful.getRestfulParam();
				if (param.contains("-") || param.contains(":") || param.contains(" ")) {
					if ("YYYY-MM-DD".equals(param)) {
						param = "restfulDateParam" + count + "/** " + param + " */ ";
						count++;
					} else {
						throw new RuntimeException("the RESTful request url is" + method.getUrl() + ", and therestfulParam is " + param + ", and ytxu can not parse this param, so i throw exception...");
					}
				}
				argsSb.append(", ").append(param);
				break;
			case staticStr:
				formatSb.append(restful.getStaticStr());
				break;
			default:
				break;
			}
		}
		
		// 使用String.format进行RESTful风格的url进行格式化
//		String formatStr = TextUtil.getFullUrl(Config.StartUrl, formatSb.toString());
		String formatStr = formatSb.toString();
		sb.append(currTabStr).append("\tString requestUrl = String.format(getFullUrl(\"").append(formatStr).append("\")").append(argsSb.toString()).append(");\n");
		sb.append(currTabStr).append("\trequest.setUrl(requestUrl);\n");
	}

	/** 生成RESTful url中的请求参数 
	 * @param isSameButOnlyVersionCode */
	public static void generatorRequestParam(StringBuffer sb, MethodEntity method, boolean isSameButOnlyVersionCode) {
//		/** urlParams 4 RESTful */ String paramName/** note */, \n
		String currTabStr = isSameButOnlyVersionCode ? Config.TabKeyNoUseStr : Config.TabKeyStr;
		List<RESTfulApiEntity> restfuls = method.getRESTfulApis();
		
		if (null == restfuls || restfuls.size() <= 0) {
			return;
		}
		
		sb.append(currTabStr).append("\t/** urlParams 4 RESTful */ ");
		int count = 0;
		for (RESTfulApiEntity restful : restfuls) {
			switch (restful.getType()) {
			case restfulParam:
				String param = restful.getRestfulParam();
				if (param.contains("-") || param.contains(":") || param.contains(" ")) {
					if ("YYYY-MM-DD".equals(param)) {
						sb.append("String restfulDateParam").append(count).append("/** ").append(param).append(" */, ");
						count++;
					} else {
						throw new RuntimeException("the RESTful request url is" + method.getUrl() + ", and therestfulParam is " + param + ", and ytxu can not parse this param, so i throw exception...");
					}
				} else {
					sb.append("String ").append(param).append(", ");
				}
				break;
			default:
				break;
			}
		}
		sb.append("\n");
	}

}