package cn.ytxu.apacer.fileCreater.newchama.requestInterface.v2;

import cn.ytxu.util.FileUtil;
import cn.ytxu.util.LogUtil;
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
 * 类(class)生成器
 * @author ytxu 2016-3-21
 *
 */
public class RequestInterfaceCreater {
    private static volatile RequestInterfaceCreater instance;

    private RequestInterfaceCreater() {
        super();
    }

    public static RequestInterfaceCreater getInstance() {
        if (null == instance) {
            synchronized (RequestInterfaceCreater.class) {
                if (null == instance) {
                    instance = new RequestInterfaceCreater();
                }
            }
        }
        return instance;
    }

	public void create(CategoryEntity category, ApiEnitity api) {
		if (null == category) {
			LogUtil.i("this category is null, so do nothing...");
			return;
		}

		String name = category.getName().trim();
		if (null == name) {
			LogUtil.i("the name is null for this category, so do nothing...");
			return;
		}

		List<MethodEntity> methods = category.getMethods();
		if (null == methods || methods.size() <= 0) {
			LogUtil.i("the methods is null or empty for this category, so do nothing...");
			return;
		}

		final String version = api.getFormatVersion();
		String classFileName = FileUtil.getClassFileName(name);
		String classFileFullName = classFileName + ".java";
		Writer writer = null;
		try {
			writer = FileUtil.getWriter(classFileFullName, Config.Api.getDirPath(version));

			// package and imports
			createPackageAndImports(writer, version);
			// class start, create private a default constructor and a public registerInstance method, and thrie params
			createConstructorAndRegisterInstance(writer, category, classFileName, version);
			// methods
			createMethods(methods, writer);

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


	// package and imports
	private void createPackageAndImports(Writer writer, String formatVersion) throws IOException {
        writer.write("package " + Config.Api.getPackageName(formatVersion) + ";\n\n");

		writer.write("import android.content.Context;\n\n");
		writer.write("import com.loopj.android.http.RequestHandle;\n");
		writer.write("import com.loopj.android.http.ResponseHandlerInterface;\n\n");
        writer.write("import com.newchama.api.BaseApi;\n\n");
        writer.write("import com.newchama.tool.net.NetRequest;\n");
		writer.write("import com.newchama.tool.net.NetRequest.Method;\n");
		writer.write("import com.newchama.tool.net.NetWorker;\n\n");
		writer.write("import java.io.File;\n");// 每一个都加上file类，防止其中有file参数
		writer.write("import java.util.Date;\n");// 每一个都加上Date类，防止其中有Date参数
		writer.write("import java.util.List;\n\n");// 每一个都加上List类，防止其中有List参数
		writer.write("import org.json.JSONArray;\n\n");// 每一个都加上JSONArray类，防止其中有JSONArray参数
	}

    /**
     * class start, create private a default constructor and a public registerInstance method, and their params<br>
     * public static class Inner {
     *  public static Outter instance = new Outter();
     * }
     * private Outter() {super();}
     * public static Outter getInstance() {
     *  return Inner.instance;
     * }
     */
	private void createConstructorAndRegisterInstance(Writer writer, CategoryEntity category, String classFileName, String formatVersion) throws IOException {
		// class start
		writer.write("\npublic class " + classFileName + " extends " + Config.Api.BaseApiFileName + " {\n\n");

//		writer.write("\tprivate volatile static " + classFileName + " instance;\n");
//		// private constructor
//		writer.write("\tprivate " + classFileName + "() {}\n");
//		// public register instance method
//		writer.write("\tpublic static void " + Config.Api.RegisterInstanceMethodName + "() {\n");
//		writer.write("\t\tif (instance == null) {\n");
//		writer.write("\t\t\tsynchronized (" + classFileName + ".class) {\n");
//		writer.write("\t\t\t\tif (instance == null) {\n");
//		writer.write("\t\t\t\t\tinstance = new " + classFileName + "();\n");
//		writer.write("\t\t\t\t}\n");
//		writer.write("\t\t\t}\n");
//		writer.write("\t\t}\n");
//		writer.write("\t\t" + Config.Api.getPublicApiFileName(formatVersion) + ".set" + classFileName + "(instance);\n");
//		writer.write("\t}\n\n");


        writer.write("\tpublic static class Helper {\n");
        writer.write("\t\tpublic static final " + classFileName + " instance = new " + classFileName + "();\n");
        writer.write("\t}\n");
        writer.write("\tprivate " + classFileName + "() {super();}\n");
        writer.write("\tpublic static " + classFileName + " getInstance() {\n");
        writer.write("\t\treturn Helper.instance;\n");
        writer.write("\t}\n\n");


	}

	private void createMethods(List<MethodEntity> methods, Writer writer) throws IOException {
		MethodEntity preMethod = null;
		for (MethodEntity method : methods) {
			boolean isSameButOnlyVersionCode = MethodEntity.isSameMethodButOnlyVersionCode(preMethod, method);
			String methodStr = MethodCreater.create(method, isSameButOnlyVersionCode);
			if (null != methodStr) {
				writer.write(methodStr);
			}
			preMethod = method;
		}
	}



}