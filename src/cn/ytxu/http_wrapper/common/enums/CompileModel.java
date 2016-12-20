package cn.ytxu.http_wrapper.common.enums;

import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.apidocjs.parser.compile_model.multi_version.MultiVersionCompileModelParser;
import cn.ytxu.http_wrapper.apidocjs.parser.compile_model.non_version.NonVersionCompileModelParser;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.List;

/**
 * Created by ytxu on 2016/9/16.
 * 整个工具的编译模式
 */
public enum CompileModel {
    /**
     * 多版本模式：相同request有多版本
     */
    multi_version() {
        @Override
        public List<VersionModel> createApiDatasFromApidocJsData(List<ApiDataBean> apiDatas) {
            return new MultiVersionCompileModelParser(apiDatas).start();
        }
    },
    /**
     * 无版本模式：request只有最新的版本(在转换数据为内部model的过程中，只会保留该请求最新的版本)
     * 版本大小依赖于配置文件中base_config的order_versions属性顺序
     */
    non_version() {
        @Override
        public List<VersionModel> createApiDatasFromApidocJsData(List<ApiDataBean> apiDatas) {
            return new NonVersionCompileModelParser(apiDatas).start();
        }
    };

    public static CompileModel getByName(String compileModelName) {
        for (CompileModel compileModel : CompileModel.values()) {
            if (compileModel.name().equals(compileModelName)) {
                return compileModel;
            }
        }
        throw new IllegalArgumentException("compile model property setup error, the name is " + compileModelName);
    }

    public abstract List<VersionModel> createApiDatasFromApidocJsData(List<ApiDataBean> apiDatas);
}
