package cn.ytxu.apacer.config;

public class RequestConfig {

    private static RequestConfig instance;
    private String requestFileDir;// 自动生成的请求文件所在目录

    private RequestConfig(String requestFileDir) {
        this.requestFileDir = requestFileDir;
    }

    public static RequestConfig getInstance(String requestFileDir) {
        if (instance == null) {
            instance = new RequestConfig(requestFileDir);
        }
        return instance;
    }

    /**
     * 接口文件的包名
     */
    private final String PackageName = "com.newchama.api.%s.api";
    /**
     * API中所有url的头部
     */
    public final String StartUrl = "http://test.newchama.com";
    public final String ApiDocUrl = "http://apidoc.newchama.com";// API文档的url
    public final String BaseApiFileName = "BaseApi";// 所有http接口中的基类
    private final String PublicApiFileName = "HttpApi%s";// 公开给外部调用的Http类
    // 将参数名称从context改为cxt001，防止在之后出现参数名称为context的参数。
    public final String AndroidContextParamName = "cxt001";

    public String getPackageName(String versionCode) {
        return String.format(PackageName, versionCode);
    }

    public String getDirPath(String versionCode) {
        return String.format(getDirPath(), versionCode);
    }

    public String getPublicApiFileName(String formatVersionCode) {
        return String.format(PublicApiFileName, formatVersionCode);
    }

    /**
     * 接口文件保存目录
     */
    public String getDirPath() {
        return requestFileDir + "%s/api/";
    }
}