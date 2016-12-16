package cn.ytxu.http_wrapper.config.property.param_type;

import cn.ytxu.http_wrapper.config.property.element_type.ElementType;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Objects;

/**
 * Created by ytxu on 16/12/16.
 * 参数的类型：包含请求与响应参数
 */
public enum ParamTypeEnum {
    INTEGER(Integer.class),
    LONG(Long.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    /**
     * FUTURE 未来将会删除掉的类型，这样的类型，不能知道精确类型
     */
    @Deprecated
    NUMBER(Number.class),

    BOOLEAN(Boolean.class),

    STRING(String.class),

    OBJECT(JSONObject.class),
    /**
     * tip: JSONArray中不能包含JSONArray，这种的数据结构，我不解析
     */
    ARRAY(JSONArray.class),

    DATE(String.class),
    FILE(String.class),
    MAP(JSONObject.class),
    UNKNOWN(null);


    private final Class clazz;

    ParamTypeEnum(Class clazz) {
        this.clazz = clazz;
    }



}
