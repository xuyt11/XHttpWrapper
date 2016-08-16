package cn.ytxu.api_semi_auto_creater.config;

import cn.ytxu.util.LogUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by ytxu on 2016/8/14.
 */
public class Property {
    /**
     * 过滤request中header参数
     */
    private static final String FILTER_HEADERS = "filter.headers";

    private static List<String> filterHeaders = Collections.EMPTY_LIST;
    private static BaseResponseEntityName breName;

    public static void load() {
        InputStream in = null;
        try {
            Properties pps = new Properties();
            in = Property.class.getClassLoader().getResourceAsStream("NewChama-android.properties");
            pps.load(in);
            getFilterHeaders(pps);
            breName = BaseResponseEntityName.createByParseProperties(pps);
            pps.clear();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("init properties file failure...");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void getFilterHeaders(Properties pps) {
        String filterHeadersStr = pps.getProperty(FILTER_HEADERS, null);
        if (Objects.isNull(filterHeadersStr)) {
            LogUtil.i(Property.class, "cant find filter.headers...");
            return;
        }
        filterHeaders = Arrays.asList(filterHeadersStr.split(","));
    }

    public static boolean hasThisHeaderInFilterHeaders(String headerName) {
        for (String filterHeader : filterHeaders) {
            if (filterHeader.equals(headerName)) {
                return true;
            }
        }
        return false;
    }

    public static BaseResponseEntityName getBreName() {
        return breName;
    }


    public static void main(String... args) {
//        String filterHeadersStr = null;
//        String[] filterHeaders = filterHeadersStr.split(",");
//        Arrays.asList(filterHeaders);

        load();

    }

    /**
     * 基础response必须的字段的字段名称
     */
    public static class BaseResponseEntityName {

        private enum Entity {
            statusCode("response.StatusCode") {
                @Override
                public void setValue2Object(BaseResponseEntityName breName, String entityName) {
                    breName.setStatusCode(entityName);
                }
            },
            message("response.Message") {
                @Override
                public void setValue2Object(BaseResponseEntityName breName, String entityName) {
                    breName.setMessage(entityName);
                }
            },
            error("response.Error") {
                @Override
                public void setValue2Object(BaseResponseEntityName breName, String entityName) {
                    breName.setError(entityName);
                }
            },
            data("response.Data") {
                @Override
                public void setValue2Object(BaseResponseEntityName breName, String entityName) {
                    breName.setData(entityName);
                }
            };

            private final String key;

            Entity(String key) {
                this.key = key;
            }

            public abstract void setValue2Object(BaseResponseEntityName breName, String entityName);
        }

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

        public static BaseResponseEntityName createByParseProperties(Properties pps) {
            BaseResponseEntityName breName = new BaseResponseEntityName();
            for (Entity entity : Entity.values()) {
                String entityName = pps.getProperty(entity.key, null);
                entity.setValue2Object(breName, entityName);
                if (Objects.isNull(entityName)) {
                    LogUtil.ee(Property.class, "cant find ", entity.key);
                }
            }
            return breName;
        }
    }

}
