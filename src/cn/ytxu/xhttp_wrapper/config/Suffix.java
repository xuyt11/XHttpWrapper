package cn.ytxu.xhttp_wrapper.config;

/**
 * 需要解析的文件的后缀
 */
public enum Suffix {
    Json("json"),
    HttpApi("xha"),
    Request("xreq"),
    Response("xres"),
    BaseResponse("xbres"),
    StatusCode("xsc");

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