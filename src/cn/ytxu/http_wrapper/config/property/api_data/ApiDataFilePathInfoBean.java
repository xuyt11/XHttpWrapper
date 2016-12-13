package cn.ytxu.http_wrapper.config.property.api_data;

/**
 * api_data数据文件的多操作系统配置
 */
public class ApiDataFilePathInfoBean {
    private String OSName;
    private String address;

    public String getOSName() {
        return OSName;
    }

    public void setOSName(String OSName) {
        this.OSName = OSName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
