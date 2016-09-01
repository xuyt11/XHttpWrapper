package cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name;

import cn.ytxu.api_semi_auto_creater.config.PropertyEntity;

/**
 * 基础response必须的字段的字段名称
 */
public class BaseResponseEntityNameProperty {

    private static BaseResponseEntityNameProperty instance;

    private PropertyEntity.ResponseBean response;

    public static BaseResponseEntityNameProperty get() {
        return instance;
    }

    public static void load(PropertyEntity.ResponseBean response) {
        instance = new BaseResponseEntityNameProperty(response);
    }

    private BaseResponseEntityNameProperty(PropertyEntity.ResponseBean response) {
        this.response = response;
    }

    public String getStatusCode() {
        return response.getStatusCode();
    }

    public String getMessage() {
        return response.getMessage();
    }

    public String getError() {
        return response.getError();
    }

    public String getData() {
        return response.getData();
    }
}