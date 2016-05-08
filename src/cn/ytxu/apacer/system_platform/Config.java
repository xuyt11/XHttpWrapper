package cn.ytxu.apacer.system_platform;

/**
 * 配置文件
 * @author ytxu 2016-3-21
 * 2016-04-19
 * version -v6
 */
public abstract class Config {
	public static final String inputFileDir = "/Users/newchama/Desktop/NewChama-Data/";

    public static final String BasePath2 = "/Users/newchama/Documents/ytxu/newchama_android/NewChama/";
    public static final String responseEntityFileDir = BasePath2 + "newchama.model/src/main/java/com/newchama/api/";
    public static final String requestFileDir = BasePath2 + "newchama.common/src/main/java/com/newchama/api/";
	
	/** API文档的html文件的路径 */
	public static final String ApiDocHtmlPath = inputFileDir + "apidoc.html";

    public static RequestConfig Api = RequestConfig.getInstance(requestFileDir);

    public static ResponseEntityConfig Entity = ResponseEntityConfig.getInstance(responseEntityFileDir);
    public static BaseResponseConfig BaseResponse = BaseResponseConfig.getInstance(responseEntityFileDir);

    public static StatusCodeConfig statusCode = StatusCodeConfig.getInstance(responseEntityFileDir);

    /** 现阶段,不进行模板方法的构建,有些难度 2016-03-31 */
    public static TemplateConfig Template = TemplateConfig.getInstance(inputFileDir);



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
        /** 获取输入数据的文件夹(需要解析的文件)<br>
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