package cn.ytxu.xhttp_wrapper.config.property.base_config;

import java.util.List;

/**
 * Created by Administrator on 2016/9/7.
 */
public class BaseConfigBean {
    public static final BaseConfigBean DEFAULT = new BaseConfigBean();

    /**
     * 自动生成文件的字符编码
     */
    private String api_data_file_charset = "UTF-8";

    /**
     * 编译模式
     * enum:mutil_version,non_version;
     * default:mutil_version
     */
    private String compile_model = CompileModelType.mutil_version.name();

    /**
     * 目标版本的顺序枚举:顺序为升序
     * 顺序的枚举出所有目标的版本号：
     * 若没有出现在其中，就会过自动过滤掉，不会出现在生成文件中；
     * 若一个目标request需要生成，则至少需要支持该request的最高版本；
     */
    private List<String> order_versions;

    private BaseConfigBean() {
    }

    public String getApiDataFileCharset() {
        return api_data_file_charset;
    }

    public String getCompileModelName() {
        return compile_model;
    }

    public CompileModelType getCompileModelType() {
        return CompileModelType.getByName(compile_model);
    }

    public List<String> getOrderVersions() {
        return order_versions;
    }
}
