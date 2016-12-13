package cn.ytxu.xhttp_wrapper.config;

import cn.ytxu.util.LogUtil;
import cn.ytxu.xhttp_wrapper.config.property.api_data.ApiDataWrapper;
import cn.ytxu.xhttp_wrapper.config.property.base_config.BaseConfigWrapper;
import cn.ytxu.xhttp_wrapper.config.property.filter.FilterWrapper;
import cn.ytxu.xhttp_wrapper.config.property.element_type.FieldTypeWrapper;
import cn.ytxu.xhttp_wrapper.config.property.response.ResponseWrapper;
import cn.ytxu.xhttp_wrapper.config.property.request.RequestWrapper;
import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeWrapper;
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
            ConfigBean object = JSON.parseObject(in, ConfigBean.class);
            load(object);
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

    private static void load(ConfigBean config) {
        ApiDataWrapper.load(config.getApiData());
        BaseConfigWrapper.load(config.getBaseConfig());
        FilterWrapper.load(config.getFilter());
        RequestWrapper.load(config.getRequest());
        ResponseWrapper.load(config.getResponse());
        StatusCodeWrapper.load(config.getStatusCode());
        FieldTypeWrapper.load(config.getFieldTypeEnum());
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
