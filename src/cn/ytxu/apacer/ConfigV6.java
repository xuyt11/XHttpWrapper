package cn.ytxu.apacer;

/**
 * 配置文件
 * @author ytxu 2016-3-21
 * 2016-04-19
 * version -v6
 */
public class ConfigV6 {
	public static final String BasePath = "/Users/newchama/Desktop/NewChama-Data/";

    public static final String BasePath2 = "/Users/newchama/Documents/ytxu/newchama_android/NewChama/";
    public static final String BaseEntityLibOutputPath = BasePath2 + "newchama.model/src/main/java/com/newchama/api/";
    public static final String BaseCommonLibOutputPath = BasePath2 + "newchama.common/src/main/java/com/newchama/api/";
	
	/** API文档的html文件的路径 */
	public static final String ApiDocHtmlPath = BasePath + "apidoc.html";

    public static final class Api {
        /** 接口文件保存目录 */
        private static final String DirPath = BaseCommonLibOutputPath + "%s/api/";
        /** 接口文件的包名*/
        private static final String PackageName = "com.newchama.api.%s.api";
        /** API中所有url的头部 */
        public static final String StartUrl = "http://test.newchama.com";
        public static final String ApiDocUrl = "http://apidoc.newchama.com";// API文档的url
        public static final String BaseApiFileName = "BaseApi";// 所有http接口中的基类
        private static final String PublicApiFileName = "HttpApi%s";// 公开给外部调用的Http类
        public static final String RegisterInstanceMethodName = "registerInstance";// 统一注册各个http分类接口的实例的方法名称
        // 将参数名称从context改为cxt001，防止在之后出现参数名称为context的参数。
        public static final String AndroidContextParamName = "cxt001";

        public static String getPackageName(String versionCode) {
            return String.format(PackageName, versionCode);
        }

        public static String getDirPath(String versionCode) {
            return String.format(DirPath, versionCode);
        }

        public static String getPublicApiFileName(String formatVersionCode) {
            return String.format(PublicApiFileName, formatVersionCode);
        }
    }

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


    public static final class statusCode {
        /** 接口文件保存目录 */
        public static final String DirPath = BaseEntityLibOutputPath + "";
        /** 接口文件的包名*/
        public static final String PackageName = "com.newchama.api";
        public static final String StatusCodeFileName = "StatusCode";// 状态码类

    }

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
	
	
	
	
	
	
	
}