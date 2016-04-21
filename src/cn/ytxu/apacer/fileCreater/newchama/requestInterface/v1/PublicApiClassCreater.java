package cn.ytxu.apacer.fileCreater.newchama.requestInterface.v1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

import cn.ytxu.util.FileUtil;
import cn.ytxu.apacer.Config;
import cn.ytxu.apacer.entity.ApiEnitity;
import cn.ytxu.apacer.entity.CategoryEntity;

import cn.ytxu.util.LogUtil;
import cn.ytxu.util.TextUtil;
import cn.ytxu.apacer.entity.MethodEntity;

/**
 * 公开http接口文件生成器
 * @author ytxu 2016-03-23
 *
 */
public class PublicApiClassCreater {
	private static volatile PublicApiClassCreater instance;
	private PublicApiClassCreater() {
		super();
	}
	public static PublicApiClassCreater getInstance() {
		if (null == instance) {
			synchronized (PublicApiClassCreater.class) {
				if (null == instance) {
					instance = new PublicApiClassCreater();
				}
			}
		}
		return instance;
	}

	/**
	 * 生成一个API接口文件，内部包含了所有的API类的引用
	 */
	public void createApiInterfaceClass(ApiEnitity api) {
		final String version = api.getFormatVersion();
		Writer writer = null;
		try {
			writer = FileUtil.getWriter(Config.Api.getPublicApiFileName(version) + ".java", Config.Api.getDirPath(version));

			// package
			writer.write("package " + Config.Api.getPackageName(version) + ";\n\n");
			// class start
			writer.write("\npublic class " + Config.Api.getPublicApiFileName(version) + " {\n\n");
			
			StringBuffer paramsSb = new StringBuffer(), fieldsSb = new StringBuffer();
			List<CategoryEntity> categorys = api.getCategorys();
			
			for (CategoryEntity category : categorys) {
				createParamsAndFieldsMethod(api, category, paramsSb, fieldsSb);
			}
			
			// params:private static Account account;
			writer.write(paramsSb.toString());
			writer.write("\n");
			
			// fields getter and setter
			writer.write(fieldsSb.toString());

			// class end
			writer.write("\n}");
			writer.flush();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != writer) {
				try { writer.close(); } catch (IOException e) { e.printStackTrace(); }
			}
		}
		
	}

	private void createParamsAndFieldsMethod(ApiEnitity api, CategoryEntity category, StringBuffer paramsSb, StringBuffer fieldsSb) {
		if (null == category) {
			return;
		}
		
		String name = category.getName().trim();
		if (TextUtil.isBlank(name)) {
			LogUtil.i("the category is " + category.toString());
			throw new RuntimeException("the name is null or blank for this category");
		}

        final String currApiVersion = api.getCurrVersionCode();
        List<MethodEntity> methods = category.getMethods();
        if (null == methods || methods.size() <= 0) {
            LogUtil.i("this category in version:" + currApiVersion + ", has not methods...");
            return;
        }


		String className = FileUtil.getClassFileName(name);
		String fieldName = className.substring(0, 1).toLowerCase() + className.substring(1);

		// params:private static Account account;
		paramsSb.append("\tprivate static ").append(className).append(" ").append(fieldName).append(";\n");
		
		// fields getter and setter
		createGetter(fieldsSb, className, fieldName);
		createSetter(fieldsSb, className, fieldName);
		
	}
	
	private void createGetter(StringBuffer fieldsSb, String className, String fieldName) {
		
		
		fieldsSb.append("\tpublic static ").append(className).append(" ");
		fieldsSb.append(fieldName);
		if ("notify".equals(fieldName)) {
			fieldsSb.append("0");
		}
		
		fieldsSb.append("() {\n");
		fieldsSb.append("\t\tif (null == ").append(fieldName).append(") {\n");
		fieldsSb.append("\t\t\t").append(className).append(".registerInstance();\n");
		fieldsSb.append("\t\t}\n");
		fieldsSb.append("\t\treturn ").append(fieldName).append(";\n");
		fieldsSb.append("\t}\n\n");
	}
	
	private void createSetter(StringBuffer fieldsSb, String className, String fieldName) {
		fieldsSb.append("\tprotected static void set").append(className).append("(").append(className).append(" instance) {\n");
		fieldsSb.append("\t\t").append(fieldName).append(" = instance;\n");
		fieldsSb.append("\t}\n\n");
	}

}

//private class HttpApi {
//	private static Account account;
//	
//	
//	public static Account account() {
//		if (null == account) {
//			Account.registerInstance();
//		}
//		return account;
//	}
//	
//	protected static void setAccount(Account instance) {
//		account = instance;
//	}
//}