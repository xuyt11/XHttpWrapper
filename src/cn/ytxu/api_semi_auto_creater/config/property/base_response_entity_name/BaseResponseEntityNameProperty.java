package cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name;

import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.util.LogUtil;

import java.util.Objects;
import java.util.Properties;

/**
 * 基础response必须的字段的字段名称
 */
public class BaseResponseEntityNameProperty {

    private static final BaseResponseEntityNameProperty instance = new BaseResponseEntityNameProperty();

    private String statusCode;
    private String message;
    private String error;
    private String data;

    public String getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public String getData() {
        return data;
    }

    void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    void setMessage(String message) {
        this.message = message;
    }

    void setError(String error) {
        this.error = error;
    }

    void setData(String data) {
        this.data = data;
    }

    public static void load(Properties pps) {
        for (BaseResponseEntity entity : BaseResponseEntity.values()) {
            String entityName = pps.getProperty(entity.getKey(), null);
            entity.setValue2Object(instance, entityName);
            if (Objects.isNull(entityName)) {
                LogUtil.ee(Property.class, "cant find ", entity.getKey());
            }
        }
    }

    public static BaseResponseEntityNameProperty get() {
        return instance;
    }
}