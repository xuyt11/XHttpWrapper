package cn.ytxu.http_wrapper.common.util;

public class TextUtil {

	
	public static boolean isEmpty(String str) {
		return null == str;
	}
	
	public static boolean isBlank(String str) {
		if (isEmpty(str))
			return true;
		
		return str.trim().length() <= 0;
	}
	

	/**
	 * 获取到全路径的地址
	 */
	public static String getFullUrl(String homePage, String url) {
		if (isUrl(url)) {
			return url;
		}
		
		boolean hasSeparator1 = homePage.endsWith("/");
		boolean hasSeparator2 = url.startsWith("/");

		if ((hasSeparator1 && !hasSeparator2) || (!hasSeparator1 && hasSeparator2)) {// 其中一个有/符号
			return homePage + url;
		}
		
		if (!hasSeparator1 && !hasSeparator2) {// 两个都没有/符号
			return homePage + "/" + url;
		}
		
		// 两个都有/符号
		return homePage + url.substring(1);
	}
	
	public static boolean isUrl(String url) {
		if (null == url || url.trim().length() <= 0) {
			return false;
		}
		
		if (url.startsWith("http://") || url.startsWith("https://")) {
			return true;
		}
		
		return false;
	}
	
	
}