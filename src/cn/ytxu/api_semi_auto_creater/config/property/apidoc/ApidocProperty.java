package cn.ytxu.api_semi_auto_creater.config.property.apidoc;


import cn.ytxu.util.OSPlatform;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/2.
 */
public class ApidocProperty {

    private static ApidocProperty instance;

    private List<ApidocFileAddressesBean> apidocFileAddresses;

    public static ApidocProperty getInstance() {
        return instance;
    }

    public static void load(List<ApidocFileAddressesBean> apidocFileAddresses) {
        instance = new ApidocProperty(apidocFileAddresses);
    }

    private ApidocProperty(List<ApidocFileAddressesBean> apidocFileAddresses) {
        this.apidocFileAddresses = apidocFileAddresses;
        if (Objects.isNull(apidocFileAddresses) || apidocFileAddresses.size() < 1) {
            throw new RuntimeException("u don`t set apidoc path");
        }
    }

    public String getHtmlPath() {
        OSPlatform os = OSPlatform.getCurrentOSPlatform();
        String osName = os.getOsName();
        for (ApidocFileAddressesBean bean : apidocFileAddresses) {
            if (osName.equalsIgnoreCase(bean.getOSName())) {
                return bean.getAddress();
            }
        }
        throw new IllegalArgumentException("not found the match apidoc path," +
                " and the current os name is " + osName);
    }
}
