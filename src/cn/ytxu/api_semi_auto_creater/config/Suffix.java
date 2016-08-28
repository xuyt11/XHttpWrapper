package cn.ytxu.api_semi_auto_creater.config;

/**
 * 需要解析的文件的后缀
 */
public enum Suffix {
    Properties("properties"),
    HttpApi("xha"),
    Request("xreq"),
    Response("xres");

    private final String name;

    Suffix(String name) {
        this.name = name;
    }

    /**
     * @param tempPrefixName 前缀名
     * @return temp文件的名称
     */
    public String getTempFileName(String tempPrefixName) {
        return tempPrefixName + "." + name;
    }
}