package cn.ytxu.apacer.fileCreater.newchama.requestInterface.v2;

import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.util.FileUtil;
import cn.ytxu.util.LogUtil;
import cn.ytxu.util.TextUtil;
import cn.ytxu.apacer.Config;
import cn.ytxu.apacer.entity.ApiEnitity;
import cn.ytxu.apacer.entity.CategoryEntity;
import cn.ytxu.apacer.entity.MethodEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

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
            createPackageAndStart(version, writer);

            createParams(api, writer);

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
            BaseCreater.closeWriter(writer);
		}
	}

    private void createPackageAndStart(String version, Writer writer) throws IOException {
        // package
        writer.write("package " + Config.Api.getPackageName(version) + ";\n\n");
        // class start
        writer.write("\npublic class " + Config.Api.getPublicApiFileName(version) + " {\n\n");
    }

    private void createParams(ApiEnitity api, Writer writer) throws IOException {
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
        createField(paramsSb, className, fieldName);

        // fields getter
		createGetter(fieldsSb, className, fieldName);
	}

    private void createField(StringBuffer paramsSb, String className, String fieldName) {
        paramsSb.append("\tprivate static ").append(className).append(" ").append(fieldName).append(";\n");
    }

    /**
     * creater http category class getter<br>
     * public static Account account() {
     *  return Account.getInstance();
     * }
     */
	private void createGetter(StringBuffer fieldsSb, String className, String fieldName) {
		fieldsSb.append("\tpublic static ").append(className).append(" ");
		fieldsSb.append(fieldName);
		if ("notify".equals(fieldName)) {
			fieldsSb.append("0");
		}
		fieldsSb.append("() {\n");

        fieldsSb.append("\t\tif (null == ").append(fieldName).append(") {\n");
		fieldsSb.append("\t\t\t").append(fieldName).append(" = ").append(className).append(".getInstance();\n");
        fieldsSb.append("\t\t}\n");
        fieldsSb.append("\t\treturn ").append(fieldName).append(";\n");
		fieldsSb.append("\t}\n\n");
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


//******************** v2
//public static HttpApi {
//		private static Account account;
//
//		public static Account account() {
//			return Account.getInstance();
//		}
//
//}
	
}

