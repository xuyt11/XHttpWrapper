package cn.ytxu.api_semi_auto_creater.config;

import cn.ytxu.util.LogUtil;

import java.util.Objects;
import java.util.Properties;

/**
 * 基础response必须的字段的字段名称
 */
public class BaseResponseEntityNameProperty {

    private enum Entity {
        statusCode("response.StatusCode") {
            @Override
            public void setValue2Object(BaseResponseEntityNameProperty breName, String entityName) {
                breName.setStatusCode(entityName);
            }
        },
        message("response.Message") {
            @Override
            public void setValue2Object(BaseResponseEntityNameProperty breName, String entityName) {
                breName.setMessage(entityName);
            }
        },
        error("response.Error") {
            @Override
            public void setValue2Object(BaseResponseEntityNameProperty breName, String entityName) {
                breName.setError(entityName);
            }
        },
        data("response.Data") {
            @Override
            public void setValue2Object(BaseResponseEntityNameProperty breName, String entityName) {
                breName.setData(entityName);
            }
        };

        private final String key;

        Entity(String key) {
            this.key = key;
        }

        public abstract void setValue2Object(BaseResponseEntityNameProperty breName, String entityName);
    }

    private static BaseResponseEntityNameProperty breName;

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

    private void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private void setError(String error) {
        this.error = error;
    }

    private void setData(String data) {
        this.data = data;
    }

    public static void createByParseProperties(Properties pps) {
        breName = new BaseResponseEntityNameProperty();
        for (Entity entity : Entity.values()) {
            String entityName = pps.getProperty(entity.key, null);
            entity.setValue2Object(breName, entityName);
            if (Objects.isNull(entityName)) {
                LogUtil.ee(Property.class, "cant find ", entity.key);
            }
        }
    }

    public static BaseResponseEntityNameProperty get() {
        return breName;
    }
}