package cn.ytxu.http_wrapper.template_engine.parser.model;

/**
 * 2016-12-11
 */
public class XHWTFileDirModel {
    private String osName;// 对应系统的名称,必须要与OSPlatform中的一致
    private String path;// 文件夹的路径字符串

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}