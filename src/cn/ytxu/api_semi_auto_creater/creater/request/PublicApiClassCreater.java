package cn.ytxu.api_semi_auto_creater.creater.request;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.entity.ApiEnitity;
import cn.ytxu.apacer.entity.CategoryEntity;
import cn.ytxu.apacer.entity.MethodEntity;
import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.api_semi_auto_creater.entity.DocumentModel;
import cn.ytxu.api_semi_auto_creater.entity.SectionModel;
import cn.ytxu.util.FileUtil;
import cn.ytxu.util.LogUtil;
import cn.ytxu.util.TextUtil;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/7/10.
 * 公开http接口文件生成器
 */
public class PublicApiClassCreater {
    private DocumentModel document;
    private List<String> versions;
    private List<PublicApi> publicApis;

    public PublicApiClassCreater(DocumentModel document) {
        this.document = document;
    }

    public void start() {
        getProperty();
        createPublicApiClass();
    }

    private void getProperty() {
        versions = document.getVersions();

        List<SectionModel> sections = document.getSections();
        publicApis = new ArrayList<>(sections.size());
        for (SectionModel section : sections) {
            PublicApi publicApi = new PublicApi(section, versions);
            publicApis.add(publicApi);
        }
    }

    private void createPublicApiClass() {
        for (String version : versions) {

            final String dirPath = Config.Api.getDirPath(version);
            final String fileName = Config.Api.getPublicApiFileName(version) + ".java";

            BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
                // package
                createPackageAndStart(version, retain, writer);

//                createParams(api, writer, retain);

                // class end
                createClassEnd(writer, retain);
            });
        }
    }

    /**
     * 生成一个API接口文件，内部包含了所有的API类的引用
     */
    public void createApiInterfaceClass(ApiEnitity api) {
        final String version = api.getFormatVersion();
        final String dirPath = Config.Api.getDirPath(version);
        final String fileName = Config.Api.getPublicApiFileName(version) + ".java";

        BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
            // package
            createPackageAndStart(version, retain, writer);

            createParams(api, writer, retain);

            // class end
            createClassEnd(writer, retain);
        });
    }

    private void createPackageAndStart(String version, RetainEntity retain, Writer writer) throws IOException {
        // package
        writer.write("package " + Config.Api.getPackageName(version) + ";\n\n");
        if (null != retain) {
            writer.write(retain.getImportData().toString());
            writer.write("\n");
        }

        // class start
        writer.write("\npublic class " + Config.Api.getPublicApiFileName(version) + " {\n\n");
    }

    private void createClassEnd(Writer writer, RetainEntity retain) throws IOException {
        if (null != retain) {
            writer.write(retain.getMethodData().toString());
            writer.write("\n");
            writer.write(retain.getOtherData().toString());
        }

        writer.write("\n}");
        writer.flush();
    }

    private void createParams(ApiEnitity api, Writer writer, RetainEntity retain) throws IOException {
        StringBuffer paramsSb = new StringBuffer(), fieldsSb = new StringBuffer();
        List<CategoryEntity> categorys = api.getCategorys();

        for (CategoryEntity category : categorys) {
            createParamsAndFieldsMethod(api, category, paramsSb, fieldsSb);
        }

        // params:private static Account account;
        writer.write(paramsSb.toString());
        writer.write("\n");
        if (null != retain) {
            writer.write(retain.getFieldData().toString());
            writer.write("\n");
        }

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
     * return Account.getInstance();
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

