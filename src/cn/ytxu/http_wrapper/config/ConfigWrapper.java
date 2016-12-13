package cn.ytxu.http_wrapper.config;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.property.api_data.ApiDataWrapper;
import cn.ytxu.http_wrapper.config.property.base_config.BaseConfigWrapper;
import cn.ytxu.http_wrapper.config.property.filter.FilterWrapper;
import cn.ytxu.http_wrapper.config.property.element_type.FieldTypeWrapper;
import cn.ytxu.http_wrapper.config.property.response.ResponseWrapper;
import cn.ytxu.http_wrapper.config.property.request.RequestWrapper;
import cn.ytxu.http_wrapper.config.property.status_code.StatusCodeWrapper;
import cn.ytxu.http_wrapper.config.property.template_file_info.TemplateFileInfoWrapper;
import cn.ytxu.http_wrapper.template_engine.XHWTFileType;
import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ytxu on 2016/8/14.
 */
public class ConfigWrapper {

    public static void load(String xhwtConfigPath) {
        final String filePath = XHWTFileType.Json.getFilePath(xhwtConfigPath);
        InputStream in = null;
        try {
            LogUtil.i(ConfigWrapper.class, "init config file:(" + filePath + ") start...");
            in = new FileInputStream(filePath);
            ConfigBean configData = JSON.parseObject(in, ConfigBean.class);
            load(xhwtConfigPath, configData);
            LogUtil.i(ConfigWrapper.class, "init config file:(" + filePath + ") success...");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i(ConfigWrapper.class, "init config file:(" + filePath + ") failure...");
        } finally {
            if (in == null) {
                return;
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void load(String xhwtConfigPath, ConfigBean configData) {
        ApiDataWrapper.load(configData.getApiData());
        TemplateFileInfoWrapper.load(xhwtConfigPath, configData.getTemplateFileInfos());
        BaseConfigWrapper.load(configData.getBaseConfig());
        FilterWrapper.load(configData.getFilter());
        RequestWrapper.load(configData.getRequest());
        ResponseWrapper.load(configData.getResponse());
        StatusCodeWrapper.load(configData.getStatusCode());
        FieldTypeWrapper.load(configData.getFieldTypeEnum());
    }

    public static ApiDataWrapper getApiDataFile() {
        return ApiDataWrapper.getInstance();
    }

    public static BaseConfigWrapper getBaseConfig() {
        return BaseConfigWrapper.getInstance();
    }

    public static FilterWrapper getFilter() {
        return FilterWrapper.getInstance();
    }

    public static RequestWrapper getRequest() {
        return RequestWrapper.getInstance();
    }

    public static ResponseWrapper getResponse() {
        return ResponseWrapper.getInstance();
    }

    public static StatusCodeWrapper getStatusCode() {
        return StatusCodeWrapper.getInstance();
    }

    public static FieldTypeWrapper getFieldType() {
        return FieldTypeWrapper.getInstance();
    }

}
