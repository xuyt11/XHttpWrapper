package cn.ytxu.http_wrapper.config.property.api_data;

import java.util.Collections;
import java.util.List;

/**
 * api_data.json等api数据文件的地址信息，包括多操作系统的配置
 */
public class ApiDataBean {
    private String api_data_source;// API数据源的类型
    private List<ApiDataFilePathInfoBean> file_path_infos = Collections.EMPTY_LIST;// 多操作系统的配置
    private String file_charset = "UTF-8";// API数据文件中内容的字符集；

    public String getApiDataSource() {
        return api_data_source;
    }

    public void setApiDataSource(String api_data_source) {
        this.api_data_source = api_data_source;
    }

    public List<ApiDataFilePathInfoBean> getFilePathInfos() {
        return file_path_infos;
    }

    public void setFilePathInfos(List<ApiDataFilePathInfoBean> file_path_infos) {
        this.file_path_infos = file_path_infos;
    }

    public String getFileCharset() {
        return file_charset;
    }

    public void setFileCharset(String file_charset) {
        this.file_charset = file_charset;
    }
}
