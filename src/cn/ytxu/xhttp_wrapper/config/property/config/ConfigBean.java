package cn.ytxu.xhttp_wrapper.config.property.config;

/**
 * Created by Administrator on 2016/9/7.
 */
public class ConfigBean {
    public static final ConfigBean DEFAULT = new ConfigBean();

    /**
     * 自动生成文件的字符编码
     */
    private String auto_generate_file_charset = "UTF-8";

    private ConfigBean() {
    }

    public String getAuto_generate_file_charset() {
        return auto_generate_file_charset;
    }

    public void setAuto_generate_file_charset(String auto_generate_file_charset) {
        this.auto_generate_file_charset = auto_generate_file_charset;
    }

}
