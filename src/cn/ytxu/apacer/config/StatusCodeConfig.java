package cn.ytxu.apacer.config;

public class StatusCodeConfig {

    private static StatusCodeConfig instance;
    private String responseEntityFileDir;// 自动生成的响应实体类文件所在目录

    private StatusCodeConfig(String responseEntityFileDir) {
        this.responseEntityFileDir = responseEntityFileDir;
    }

    public static StatusCodeConfig getInstance(String responseEntityFileDir) {
        if (instance == null) {
            instance = new StatusCodeConfig(responseEntityFileDir);
        }
        return instance;
    }

    /**
     * 接口文件保存目录
     */
    public final String DirPath = responseEntityFileDir + "";
    /**
     * 接口文件的包名
     */
    public final String PackageName = "com.newchama.api";
    public final String StatusCodeFileName = "StatusCode";// 状态码类
    public final String StatusCode = "StatusCode";// 状态码类

}