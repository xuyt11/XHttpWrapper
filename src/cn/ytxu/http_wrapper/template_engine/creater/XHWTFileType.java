package cn.ytxu.http_wrapper.template_engine.creater;

import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.List;

/**
 * x http wrapper template file type
 * 需要解析的文件:模板文件
 */
public enum XHWTFileType {
    /**
     * 所有API请求的统一调用入口，统合所有的请求类别的接口，防止API接口分散。
     */
    HttpApi() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getHttpApiReflectiveDatas(versions);
        }
    },
    /**
     * 单个请求分组中，所有的请求方法。
     * 若需要的话，可以添加对应的简化参数个数的方法(缩略请求方法)。
     */
    Request() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getRequestReflectiveDatas(versions);
        }
    },
    /**
     * 请求参数分组归类，对应单个请求
     */
    RequestParam() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getRequestParamReflectiveDatas(versions);
        }
    },
    /**
     * 请求的相应数据model
     */
    Response() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            return ReflectiveDataConvertor.getResponseReflectiveDatas(versions);
        }
    },
    /**
     * 基础的响应实体类
     * 所有的请求响应的数据，都是在该类的基础上的；
     * 且在其中还包含了错误异常提示的model
     */
    BaseResponse() {
        @Override
        public List getReflectiveDatas(List<VersionModel> versions) {
            boolean isPolymerization = ConfigWrapper.getTemplateFileInfo().isPolymerization(this);
            return ReflectiveDataConvertor.getBaseResponseReflectiveDatas(versions, isPolymerization);
        }
    },
    /**
     * 响应中所有状态码的枚举类
     */
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