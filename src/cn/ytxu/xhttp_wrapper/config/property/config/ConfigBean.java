package cn.ytxu.xhttp_wrapper.config.property.config;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class ConfigBean {
    public static final ConfigBean DEFAULT = new ConfigBean();

    /**
     * 自动生成文件的字符编码
     */
    private String auto_generate_file_charset = "UTF-8";
    /**
     * 编译模式
     * enum:mutil_version,non_version;
     * default:mutil_version;
     */
    private String compile_model;
    /**
     * 目标版本的顺序枚举:顺序为升序
     * 顺序的枚举出所有目标的版本号：
     * 若没有出现在其中，就会过自动过滤掉，不会出现在生成文件中；
     * 若一个目标request需要生成，则至少需要支持该request的最高版本；
     */
    private List<String> order_versions;

    private ConfigBean() {
    }

    public String getAuto_generate_file_charset() {
        return auto_generate_file_charset;
    }

    public void setAuto_generate_file_charset(String auto_generate_file_charset) {
        this.auto_generate_file_charset = auto_generate_file_charset;
    }

    public String getCompile_model() {
        return compile_model;
    }

    public void setCompile_model(String compile_model) {
        this.compile_model = compile_model;
    }

    public List<String> getOrder_versions() {
        return order_versions;
    }

    public void setOrder_versions(List<String> order_versions) {
        this.order_versions = order_versions;
    }
}
