package cn.ytxu.http_wrapper.common.util;

public class CamelCaseUtils {

	private static final char SEPARATOR = '_';

	/** 下划线命名法 */
	public static String toUnderlineName(String s) {
		if (s == null) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < (s.length() - 1)) {
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i >= 0) && Character.isUpperCase(c)) {
				if (!upperCase || !nextUpperCase) {
					if (i > 0)
						sb.append(SEPARATOR);
				}
				upperCase = true;
			} else {
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

	/** 驼峰命名法 */
	public static String toCamelCase(String s) {
		if (s == null) {
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == SEPARATOR) {
				upperCase = true;
			} else if (upperCase) {
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	/** 首字母大写的驼峰命名法 */
	public static String toCapitalizeCamelCase(String s) {
		if (s == null) {
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	/** 将空格转换为下划线 */
	public static String convertSpace2UnderLine(String s) {
		if (null == s) {
			return null;
		}
		
		return s.replace(" ", "_");
	}

	/** 首字母大写 */
	public static String toUpperFirst(String s) {
		if (s == null) {
			return null;
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static void main(String[] args) {
		System.out.println(CamelCaseUtils.toUnderlineName("ISOCertifiedStaff"));
		System.out.println(CamelCaseUtils.toUnderlineName("CertifiedStaff"));
		System.out.println(CamelCaseUtils.toUnderlineName("UserID"));
		System.out.println(CamelCaseUtils.toCamelCase("iso_certified_staff"));
		System.out.println(CamelCaseUtils.toCamelCase("certified_staff"));
		System.out.println(CamelCaseUtils.toCamelCase("user_id"));
		System.out.println(CamelCaseUtils.toCapitalizeCamelCase("user_id"));
	}
}