package cn.ytxu.http_wrapper.template_engine;

/**
 * x http wrapper template file type
 * 需要解析的文件:配置文件或者是模板文件
 */
public enum XHWTFileType {
    Json("json") {
        @Override
        public String getFilePath(String xhwtConfigPath) {
            return xhwtConfigPath;
        }
    },
    HttpApi("httpapi"),
    Request("request"),
    Response("response"),
    BaseResponse("baseresponse"),
    StatusCode("statuscode");

    private static final String XHttpWrapperTemplateFileSuffixName = "xhwt";

    protected final String name;

    XHWTFileType(String name) {
        this.name = name;
    }

    /**
     * @param xhwtConfigPath 配置文件的路径
     * @return 目标文件的路径(配置文件或者是模板文件)
     */
    public String getFilePath(String xhwtConfigPath) {
        int pointerIndex = xhwtConfigPath.lastIndexOf(".");
        String prefixOfTempFile = xhwtConfigPath.substring(0, pointerIndex);
        return prefixOfTempFile + "-" + name + "." + XHttpWrapperTemplateFileSuffixName;
    }
}