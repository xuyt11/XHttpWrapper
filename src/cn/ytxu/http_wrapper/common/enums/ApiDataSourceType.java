package cn.ytxu.http_wrapper.common.enums;

import cn.ytxu.http_wrapper.apidocjs.ApidocjsDataParser;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by ytxu on 2016/12/13.
 * API数据源的类型
 * 根据不同的类型，使用不同的解析器，转换成XHWT的数据，用于最后的文件生成
 */
public enum ApiDataSourceType {
    apidocjs {
        @Override
        public List<VersionModel> createXHWTModelByParseApiData() throws IOException {
            return new ApidocjsDataParser().start();
        }
    };

    public abstract List<VersionModel> createXHWTModelByParseApiData() throws IOException;

    public static ApiDataSourceType get(String name) {
        for (ApiDataSourceType apiDataSourceType : ApiDataSourceType.values()) {
            if (apiDataSourceType.name().equals(name)) {
                return apiDataSourceType;
            }
        }
        throw new IllegalArgumentException("don`t found this api_data_source:" + name);
    }
}
