package cn.ytxu.api_semi_auto_creater.entity;

/**
 * Created by ytxu on 2016/6/16.
 */
public enum ApiVersionModel {
    V1_3_1(1, "1.3.1"),
    V1_4_0(2, "1.4.0"),
    V1_5_0(3, "1.5.0");

    private final int apiIndex;// 该API版本的顺序，可以用于过滤老版本
    private final String versionCode;

    ApiVersionModel(int apiIndex, String versionCode) {
        this.apiIndex = apiIndex;
        this.versionCode = versionCode;
    }

    public int getApiIndex() {
        return apiIndex;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public static ApiVersionModel get(String versionCode) {
        for (ApiVersionModel version : ApiVersionModel.values()) {
            if (version.versionCode.equals(versionCode.trim())) {
                return version;
            }
        }
        throw new RuntimeException("can not find this version code:" + versionCode + ", so you need add it to this enum...");
    }

    public static boolean filterOutThisVersion(ApiVersionModel api) {
        ApiVersionModel lowestApi = V1_3_1;// 最低的版本：can replace this version to setup filter fun
        if (api.apiIndex >= lowestApi.apiIndex) {
            return false;
        }
        return true;
    }

}
