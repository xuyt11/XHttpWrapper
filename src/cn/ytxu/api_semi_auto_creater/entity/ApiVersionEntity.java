package cn.ytxu.api_semi_auto_creater.entity;

/**
 * Created by ytxu on 2016/6/16.
 */
public enum ApiVersionEntity {
    V1_3_1(1, "1.3.1"),
    V1_4_0(2, "1.4.0"),
    V1_5_0(3, "1.5.0");

    private final int apiIndex;// 该API版本的顺序，可以用于过滤老版本
    private final String versionCode;

    ApiVersionEntity(int apiIndex, String versionCode) {
        this.apiIndex = apiIndex;
        this.versionCode = versionCode;
    }

    public int getApiIndex() {
        return apiIndex;
    }

    public String getVersionCode() {
        return versionCode;
    }

}
