package cn.ytxu.apacer.config;

public class BaseResponseConfig {

    private static BaseResponseConfig instance;
    private String responseEntityFileDir;// 自动生成的响应实体类文件所在目录

    private BaseResponseConfig(String responseEntityFileDir) {
        this.responseEntityFileDir = responseEntityFileDir;
    }

    public static BaseResponseConfig getInstance(String responseEntityFileDir) {
        if (instance == null) {
            instance = new BaseResponseConfig(responseEntityFileDir);
        }
        return instance;
    }


    public String ClassName = "ResponseEntity";
    /**
     * 实体类文件的包名
     */
    public String PackageName = "com.newchama.api";

    public String StatusCode = "status_code";
    public String Message = "message";
    public String Error = "error";
    public String Data = "data";
    /**
     * 过滤掉的属性,这些都在基础响应实体类中都有写,所以其他的子类不需要添加了
     */
    public String[] FilterNames = {StatusCode, Message, Error};
    public String[] FieldNames = {StatusCode, Message, Error, Data};

    /**
     * 实体类文件保存目录
     */
    public String getDirPath() {
        return responseEntityFileDir + "";
    }
}