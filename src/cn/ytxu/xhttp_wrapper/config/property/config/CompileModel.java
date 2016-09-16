package cn.ytxu.xhttp_wrapper.config.property.config;

/**
 * Created by ytxu on 2016/9/16.
 * 整个工具的编译模式
 */
public enum CompileModel {
    /**
     * 多版本模式：相同request有多版本
     */
    mutil_version("mutil_version"),
    /**
     * 无版本模式：request已有最新的版本
     */
    no_version("no_version");

    private final String name;

    CompileModel(String name) {
        this.name = name;
    }

    public static CompileModel getByName(String compileModelStr) {
        for (CompileModel compileModel : CompileModel.values()) {
            if (compileModel.name.equals(compileModelStr)) {
                return compileModel;
            }
        }
        return mutil_version;
    }
}
