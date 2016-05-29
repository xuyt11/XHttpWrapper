package cn.ytxu.apacer.dataParser.apidocjsParser.v7.output;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by ytxu on 2016/5/29.
 */
public enum OutputType {
    String("String") {
        @Override
        boolean isThisType(Object obj) {
            Class objType = obj.getClass();
            return objType == String.class;
        }
    },
    Number("Number") {
        @Override
        boolean isThisType(Object obj) {
            Class objType = obj.getClass();
            return objType == Integer.class || objType == Long.class;
        }
    },
    Integer("Integer") {
        @Override
        boolean isThisType(Object obj) {
            Class objType = obj.getClass();
            return objType == Integer.class;
        }
    },
    Long("Long") {
        @Override
        boolean isThisType(Object obj) {
            Class objType = obj.getClass();
            return objType == Long.class;
        }
    },
    Boolean("Boolean") {
        @Override
        boolean isThisType(Object obj) {
            Class objType = obj.getClass();
            return objType == Boolean.class;
        }
    },
    JSONObject("JSONObject") {
        @Override
        boolean isThisType(Object obj) {
            Class objType = obj.getClass();
            return objType == JSONObject.class;
        }
    },
    JSONArray("JSONArray") {
        @Override
        boolean isThisType(Object obj) {
            Class objType = obj.getClass();
            return objType == JSONArray.class;
        }
    },
    Unknown("Unknown") {
        @Override
        boolean isThisType(Object obj) {
            throw new RuntimeException("can not call it...");
        }
    };

    private final String typeName;

    OutputType(String typeName) {
        this.typeName = typeName;
    }

    abstract boolean isThisType(Object obj);

    public static OutputType getOutputType(Object obj) {
        if (obj == null) {
            return Unknown;
        }
        if (String.isThisType(obj)) {
            return String;
        }
        if (Number.isThisType(obj)) {// Integer || Long
            return Number;
        }
        if (Boolean.isThisType(obj)) {
            return Boolean;
        }
        if (JSONObject.isThisType(obj)) {
            return JSONObject;
        }
        if (JSONArray.isThisType(obj)) {
            return JSONArray;
        }
        return Unknown;
    }
}
