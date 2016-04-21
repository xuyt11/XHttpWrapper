package cn.ytxu.apacer.dataParser.apidocjsParser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.ytxu.util.LogUtil;
import cn.ytxu.util.TextUtil;

import cn.ytxu.apacer.entity.RESTfulApiEntity;

/**
 * RESTful API解析器
 * @author ytxu 2016-3-22
 *
 */
public class RESTfulAPIParser {
	private static final Pattern idOrDatePattern = Pattern.compile("[\\{]{1}.{2,}?[\\}]{1}");
	private static final Pattern multiPattern = Pattern.compile("[\\[]{1}.{2,}?[\\]]{1}");

	
	/**
	 * 解析转换请求路径(url)，生成一个List<br>
	 * 例如：<br>
	 * 1、id型的：{id}、{feedback_id}、{recommend_id}...<br>
	 * 2、多选型的：[ios|android|web]<br>
	 * 3、时间型的：{YYYY-MM-DD}<br>
	 * 2016-3-22
	 */
	public static List<RESTfulApiEntity> parse(String requestUrl) {
		if (null == requestUrl) {
			LogUtil.i(RESTfulAPIParser.class, "the request url is null，this is error status...");
			return null;
		}

		requestUrl = parseMultiPattern(requestUrl);
		
        
		Matcher m = idOrDatePattern.matcher(requestUrl);
		if (!m.find()) {// 
			return null;
		}
		int preStart = 0, preEnd = 0;// 之前find到的start、end
        String subStr;
        List<RESTfulApiEntity> restfulEntities = new ArrayList<RESTfulApiEntity>();
		do {
        	int start = m.start();
        	int end = m.end();
        	String group = m.group();
			
        	if (preStart == 0) {
        		subStr = requestUrl.substring(preStart, start);
        	} else {
        		subStr = requestUrl.substring(preEnd, start);
        	}
        	if (!TextUtil.isBlank(subStr)) {
        		restfulEntities.add(RESTfulApiEntity.createStaticStr(subStr));
        	}

        	String restfulParam = group.substring(1, group.length() - 1);
        	restfulEntities.add(RESTfulApiEntity.createRESTfulParam(restfulParam));
        	preStart = start;
        	preEnd = end;
		} while (m.find());
		subStr = requestUrl.substring(preEnd);
		if (!TextUtil.isBlank(subStr)) {
			restfulEntities.add(RESTfulApiEntity.createStaticStr(subStr));
		}
		
		return restfulEntities;
	}

	/**
	 * 解析是否有多选的类型：<br>
	 * 1、没有，直接返回requestUrl；<br>
	 * 2、若是有平台多选的话，直接替换；<br>
	 * 3、其他情况，抛出运行时异常，进行分析后，再添加解析代码
	 * @param requestUrl
	 * @return requestUrl
	 */
	private static String parseMultiPattern(String requestUrl) {
		Matcher m = multiPattern.matcher(requestUrl);
        if (m.find()) {// 现阶段值匹配一个多选的，未来再做while循环
        	String group = m.group();
        	if (group.contains("android")) {// 现阶段只匹配不同平台的接口
        		LogUtil.i(RESTfulAPIParser.class, "this requestUrl is " + requestUrl + ", and the multi pattern group is " + group);
        		requestUrl = requestUrl.replace(group, "android");// 直接替换requestUrl，不需要进行参数的添加等的处理
        	} else {// 直接抛出异常，在控制台中查看requestUrl、group，再去进行代码的添加
        		throw new RuntimeException("this requestUrl is " + requestUrl + ", and the multi pattern group is " + group);
        	}
        }
        
        if (m.find()) {// 若还有第二个匹配的多选型RESTful参数，抛出异常，查看控制台日志，再进行处理
        	String group = m.group();
        	throw new RuntimeException("this requestUrl is " + requestUrl + ", and has more then two match, and curr the multi pattern group is " + group);
        }
        
        return requestUrl;
	}

	

	
	public static void main(String[] args) {
		String restfulUrl = "http://www.ytxu.com/delete/{recommend_id}/";
		String restfulUrl2 = "http://www.ytxu.com/{id}/{recommend_id}";
//		Pattern p = Pattern.compile("^.{1,}[\\{]{1}.{2,}?[\\}]{1}.{0,}$");
		Pattern p = Pattern.compile("[\\{]{1}.{2,}?[\\}]{1}");
		Matcher m = p.matcher(restfulUrl);
        while (m.find()) {
        	
        	System.out.println("group count:" + m.groupCount());
        	System.out.println("group start:" + m.start());
        	System.out.println("group end:" + m.end());
        	System.out.println(m.group());
        }
        System.out.println("----------------");
        
        m = p.matcher(restfulUrl2);
        while (m.find()) {
        	
        	System.out.println("2group count:" + m.groupCount());
        	System.out.println("2group start:" + m.start());
        	System.out.println("2group end:" + m.end());
        	System.out.println(m.group());
        }

        System.out.println("================");
        
    	int preStart = 0, preEnd = 0;
        m = p.matcher(restfulUrl2);
        StringBuffer sb = new StringBuffer();
        int count = 0;
        String subStr;
		while (m.find()) {
        	int start = m.start();
        	int end = m.end();
        	String group = m.group();

        	System.out.println("group:" + group.substring(1, group.length() - 1));
        	if (preStart == 0) {
        		subStr = restfulUrl2.substring(preStart, start);
        		System.out.println(subStr);
        	} else {
        		subStr = restfulUrl2.substring(preEnd, start);
        		System.out.println(subStr);
        	}
        	
        	sb.append(subStr).append("{").append(count++).append("}");
        	preStart = start;
        	preEnd = end;
		}
		subStr = restfulUrl2.substring(preEnd);
		if (null == subStr) {
			System.out.println("end is null");
		} else {
			System.out.println("end str size:" + subStr.length());
		}
		System.out.println("end:" + subStr);
		sb.append(subStr);
		System.out.println("占位符字符串:" + sb.toString());
		

        System.out.println("++++++++++++++++++++");
        m = multiPattern.matcher("/get/[ios|android|web]/");
        while (m.find()) {
        	System.out.println("3group count:" + m.groupCount());
        	System.out.println("3group start:" + m.start());
        	System.out.println("3group end:" + m.end());
        	System.out.println(m.group());
        	if (m.group().contains("android")) {// 现阶段只匹配不同平台的接口
        		System.out.println("/get/[ios|android|web]/".replace(m.group(), "android"));
        	}
        }

        
		
	}
	
	
}
