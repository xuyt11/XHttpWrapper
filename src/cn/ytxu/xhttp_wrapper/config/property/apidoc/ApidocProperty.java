package cn.ytxu.xhttp_wrapper.config.property.apidoc;


import cn.ytxu.util.OSPlatform;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2016/9/2.
 */
public class ApidocProperty {

    private static ApidocProperty instance;

    private List<ApidocOutputDataFileBean> apidocFileAddresses;

    public static ApidocProperty getInstance() {
        return instance;
    }

    public static void load(List<ApidocOutputDataFileBean> apidocFileAddresses) {
        instance = new ApidocProperty(apidocFileAddresses);
    }

    private ApidocProperty(List<ApidocOutputDataFileBean> apidocFileAddresses) {
        this.apidocFileAddresses = apidocFileAddresses;
        if (Objects.isNull(apidocFileAddresses) || apidocFileAddresses.size() < 1) {
            throw new RuntimeException("u don`t set apidoc path");
        }
    }

    public String getApiDataJsonPath() {
        OSPlatform os = OSPlatform.getCurrentOSPlatform();
        String osName = os.getOsName();
        for (ApidocOutputDataFileBean bean : apidocFileAddresses) {
            if (osName.equalsIgnoreCase(bean.getOSName())) {
                return bean.getAddress();
            }
        }
        throw new IllegalArgumentException("not found the match apidoc path," +
                " and the current os name is " + osName);
    }
}