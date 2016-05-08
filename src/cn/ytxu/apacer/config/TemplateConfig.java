package cn.ytxu.apacer.config;

/**
 * 现阶段,不进行模板方法的构建,有些难度 2016-03-31
 */
public class TemplateConfig {

    private static TemplateConfig instance;
    private String inputFileDir;// 输入数据文件所在目录(需要解析)

    private TemplateConfig(String inputFileDir) {
        this.inputFileDir = inputFileDir;
    }

    public static TemplateConfig getInstance(String inputFileDir) {
        if (instance == null) {
            instance = new TemplateConfig(inputFileDir);
        }
        return instance;
    }

    /**
     * 模板接口文件保存目录
     */
    public String DirPath = inputFileDir + "tempapi/";
    /**
     * template文件的路径
     */
    public String FilePath = inputFileDir + "template.txt";

}