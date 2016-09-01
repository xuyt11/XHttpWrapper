package cn.ytxu.api_semi_auto_creater.config.property.apidoc;

/**
 * apidoc.html文件地址的配置，包括多操作系统的配置
 */
public class ApidocFileAddressesBean {
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
