package cn.ytxu.apacer.system_platform;

/**
 * 配置文件
 * @author ytxu 2016-3-21
 * 2016-04-19
 * version -v6
 */
public abstract class Config {
	public static final String BasePath = "/Users/newchama/Desktop/NewChama-Data/";

    public static final String BasePath2 = "/Users/newchama/Documents/ytxu/newchama_android/NewChama/";
    public static final String BaseEntityLibOutputPath = BasePath2 + "newchama.model/src/main/java/com/newchama/api/";
    public static final String BaseCommonLibOutputPath = BasePath2 + "newchama.common/src/main/java/com/newchama/api/";
	
	/** API文档的html文件的路径 */
	public static final String ApiDocHtmlPath = BasePath + "apidoc.html";

    public static RequestConfig Api = RequestConfig.getInstance(BaseCommonLibOutputPath);


    public static final class Entity {
        /** 实体类文件保存目录 */
        private static final String DirPath = BaseEntityLibOutputPath + "%s/entity/";
        /** 实体类文件保存目录---v5 */
        private static final String DirPath4Category = BaseEntityLibOutputPath + "%s/entity/%s/";
        /** 实体类文件的包名*/
        private static final String PackageName = "com.newchama.api.%s.entity";
        /** 实体类文件的包名---v5*/
        private static final String PackageName4Category = "com.newchama.api.%s.entity.%s";

        public static String getPackageName(String versionCode) {
            return String.format(PackageName, versionCode);
        }

        public static String getPackageName4Category(String versionCode, String categoryName) {
            return String.format(PackageName4Category, versionCode, categoryName);
        }

        public static String getDirPath(String versionCode) {
            return String.format(DirPath, versionCode);
        }

        public static String getDirPath4Category(String versionCode, String categoryName) {
            return String.format(DirPath4Category, versionCode, categoryName);
        }

        /** 基础的响应实体类 */
        public static final class BaseResponse {
            public static final String ClassName = "ResponseEntity";
            /** 实体类文件保存目录 */
            public static final String DirPath = BaseEntityLibOutputPath + "";
            /** 实体类文件的包名*/
            public static final String PackageName = "com.newchama.api";

            public static final String StatusCode = "status_code";
            public static final String Message = "message";
            public static final String Error = "error";
            public static final String Data = "data";
            /** 过滤掉的属性,这些都在基础响应实体类中都有写,所以其他的子类不需要添加了 */
            public static final String[] FilterNames = {StatusCode, Message, Error};
            public static final String[] FieldNames = {StatusCode, Message, Error, Data};

        }
    }


    public static StatusCodeConfig statusCode = StatusCodeConfig.getInstance(BaseEntityLibOutputPath);

    /** 现阶段,不进行模板方法的构建,有些难度 2016-03-31 */
    public static final class Template {
        /** 模板接口文件保存目录 */
        public static final String DirPath = BasePath + "tempapi/";
        /** template文件的路径 */
        public static final String FilePath = BasePath + "template.txt";

    }



    /** 编码格式 */
	public static final String CharsetName = "UTF-8";


    /** Token:需要在方法参数、http header时，给注释掉；（注释掉而不是删除：1、防止以后要用到；2、可以给调用者以提示） */
	public static final String TokenName_Authorization = "Authorization";
	
	/** 字符：\t */
	public static final String TabKeyStr = "\t";
	/** 字符：//\t */
	public static final String TabKeyNoUseStr = "//\t";


    /** 配置文件目录 */
    public interface ConfigDir {
        /** 获取输入数据的文件夹<br>
         * 例如：apidochtml(API文档的html文件)、template(模板文件)的文件夹 */
        public abstract String getInputDir();

        /** 自动生成文件所在的文件夹 */
        public abstract String getAutoCreaterDir();

        /** 获取请求文件所在的目录 */
        public abstract String getRequestDir();

        /** 获取响应实体类文件所在的目录 */
        public abstract String getEntityDir();
    }

}