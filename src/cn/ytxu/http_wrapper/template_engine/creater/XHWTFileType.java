package cn.ytxu.http_wrapper.template_engine.creater;

import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.List;

/**
 * x http wrapper template file type
 * 需要解析的文件:配置文件或者是模板文件
 */
public enum XHWTFileType {
    HttpApi() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getHttpApiReflectiveDatas(versions);
        }
    },
    Request() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getRequestReflectiveDatas(versions);
        }
    },
    RequestParam() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getRequestParamReflectiveDatas(versions);
        }
    },
    Response() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getResponseReflectiveDatas(versions);
        }
    },
    BaseResponse() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getBaseResponseReflectiveDatas(versions);
        }
    },
    StatusCode() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getStatusCodeReflectiveDatas(versions);
        }
    };


    public abstract List getReflectiveDatas(List<VersionModel> versions);


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