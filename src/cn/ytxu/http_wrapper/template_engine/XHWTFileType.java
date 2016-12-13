package cn.ytxu.http_wrapper.template_engine;

/**
 * x http wrapper template file type
 * 需要解析的文件:配置文件或者是模板文件
 */
public enum XHWTFileType {
    HttpApi(),
    Request(),
    Response(),
    BaseResponse(),
    StatusCode();


    public static XHWTFileType get(String name) {
        for (XHWTFileType xhwtFileType : XHWTFileType.values()) {
            if (xhwtFileType.name().equals(name)) {
                return xhwtFileType;
            }
        }

        throw new IllegalArgumentException("u setup x-http-wrapper template file type error," +
                " the error name is " + name +
                ", u need modify this name, or add this type to XHWTFileType enum");
    }
}