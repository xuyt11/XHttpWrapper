package cn.ytxu.apacer.system_platform;

/**
 * 基础的响应实体类
 */
public class ResponseEntityConfig {

    private static ResponseEntityConfig instance;
    private String responseEntityFileDir;// 自动生成的响应实体类文件所在目录

    private ResponseEntityConfig(String responseEntityFileDir) {
        this.responseEntityFileDir = responseEntityFileDir;
    }

    public static ResponseEntityConfig getInstance(String responseEntityFileDir) {
        if (instance == null) {
            instance = new ResponseEntityConfig(responseEntityFileDir);
        }
        return instance;
    }

    /**
     * 实体类文件保存目录
     */
    private String DirPath = responseEntityFileDir + "%s/entity/";
    /**
     * 实体类文件保存目录---v5
     */
    private String DirPath4Category = responseEntityFileDir + "%s/entity/%s/";
    /**
     * 实体类文件的包名
     */
    private String PackageName = "com.newchama.api.%s.entity";
    /**
     * 实体类文件的包名---v5
     */
    private String PackageName4Category = "com.newchama.api.%s.entity.%s";

    public String getPackageName(String versionCode) {
        return String.format(PackageName, versionCode);
    }

    public String getPackageName4Category(String versionCode, String categoryName) {
        return String.format(PackageName4Category, versionCode, categoryName);
    }

    public String getDirPath(String versionCode) {
        return String.format(DirPath, versionCode);
    }

    public String getDirPath4Category(String versionCode, String categoryName) {
        return String.format(DirPath4Category, versionCode, categoryName);
    }
}