package cn.ytxu.apacer.config;

/**
 * 配置文件
 * @author ytxu 2016-3-21
 * 2016-04-19
 * version -v6
 */
public class Config {
    /** 编码格式 */
	public static final String CharsetName = "UTF-8";
    /** Token:需要在方法参数、http header时，给注释掉；（注释掉而不是删除：1、防止以后要用到；2、可以给调用者以提示） */
	public static final String TokenName_Authorization = "Authorization";
	/** 字符：\t */
	public static final String TabKeyStr = "\t";
	/** 字符：//\t */
	public static final String TabKeyNoUseStr = "//\t";


    private static ConfigDir mConfigDir;

    public static RequestConfig Api;
    public static ResponseEntityConfig Entity;
    public static BaseResponseConfig BaseResponse;
    public static StatusCodeConfig statusCode;
    /** 现阶段,不进行模板方法的构建,有些难度 2016-03-31 */
    public static TemplateConfig Template;

    static {
        ConfigFactory.setConfigData();
    }

    static void setConfigDirThenCreateOtherConfig(ConfigDir configDir) {
        mConfigDir = configDir;

        Api = RequestConfig.getInstance(configDir.getRequestDir());
        Entity = ResponseEntityConfig.getInstance(configDir.getResponseEntityDir());
        BaseResponse = BaseResponseConfig.getInstance(configDir.getResponseEntityDir());
        statusCode = StatusCodeConfig.getInstance(configDir.getResponseEntityDir());
        Template = TemplateConfig.getInstance(configDir.getInputDir());
    }

    /** API文档的html文件的路径 */
    public static String getApiDocHtmlPath() {
        return mConfigDir.getInputDir() + "apidoc.html";
    }



    /** 配置文件目录 */
    public interface ConfigDir {
        /** 获取输入数据的文件夹(需要解析的文件)<br>
         * 例如：apidochtml(API文档的html文件)、template(模板文件)的文件夹 */
        String getInputDir();

        /** 自动生成文件所在的文件夹 */
        String getAutoCreaterDir();

        /** 获取请求文件所在的目录 */
        String getRequestDir();

        /** 获取响应实体类文件所在的目录 */
        String getResponseEntityDir();
    }

}