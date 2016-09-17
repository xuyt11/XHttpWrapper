package cn.ytxu.xhttp_wrapper.config.property.config;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.MutilVersionCompileModelParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.NonVersionCompileModelParser;
import cn.ytxu.xhttp_wrapper.model.VersionModel;

import java.util.List;

/**
 * Created by ytxu on 2016/9/16.
 * 整个工具的编译模式
 */
public enum CompileModel {
    /**
     * 多版本模式：相同request有多版本
     */
    mutil_version("mutil_version") {
        @Override
        public List<VersionModel> generateApiTreeDependentByCompileModel(List<ApiDataBean> apiDatas) {
            return new MutilVersionCompileModelParser(apiDatas).start();
        }
    },
    /**
     * 无版本模式：request已有最新的版本
     */
    non_version("non_version") {
        @Override
        public List<VersionModel> generateApiTreeDependentByCompileModel(List<ApiDataBean> apiDatas) {
            return new NonVersionCompileModelParser(apiDatas).start();
        }
    };

    private final String name;

    CompileModel(String name) {
        this.name = name;
    }

    public static CompileModel getByName(String compileModelStr) {
        for (CompileModel compileModel : CompileModel.values()) {
            if (compileModel.name.equals(compileModelStr)) {
                return compileModel;
            }
        }
        return mutil_version;
    }

    public abstract List<VersionModel> generateApiTreeDependentByCompileModel(List<ApiDataBean> apiDatas);
}
