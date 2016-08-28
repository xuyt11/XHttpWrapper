package cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name;

/**
 * 基础response必须的字段的字段
 */
public enum BaseResponseEntity {
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

    BaseResponseEntity(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public abstract void setValue2Object(BaseResponseEntityNameProperty breName, String entityName);
}