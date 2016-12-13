package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json;

import cn.ytxu.http_wrapper.common.util.LogUtil;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.request.RequestModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2016/10/11.
 */
public class ResponseBodyParser {
    private ResponseModel response;

    public ResponseBodyParser(ResponseModel response) {
        this.response = response;
    }

    /**
     * @return notNeedParseAgain
     */
    public boolean start() {
        // 默认两个(responseDesc, responseText)都有数据
        int separatorIndex = getSeparatorIndex();
        if (separatorIndex <= 0) {
            printLog4NotHaveJsonTypeResponseBody();
            return true;
        }

        String header = getResponseHeader(separatorIndex);
        String body = getResponseBody(separatorIndex);
        response.setHeaderAndBody(header, body);

        //1 解析出body中json格式数据的所有字段；
        JSONObject bodyJObj;
        try {
            bodyJObj = JSON.parseObject(body);
        } catch (JSONException ignore) {
            printErrorLog4IsErrorJsonType();
            return true;
        }

        return parseStatusCode(bodyJObj);
    }

    private int getSeparatorIndex() {
        return response.getContent().indexOf("{");
    }

    private String getResponseHeader(int separatorIndex) {
        return response.getContent().substring(0, separatorIndex).trim();
    }

    private String getResponseBody(int separatorIndex) {
        return response.getContent().substring(separatorIndex).trim();
    }

    private boolean parseStatusCode(JSONObject bodyJObj) {
        String statusCodeName = ConfigWrapper.getResponse().getStatusCode();
        if (bodyJObj.containsKey(statusCodeName)) {
            response.setStatusCode(String.valueOf(bodyJObj.getInteger(statusCodeName)));
            return false;
        } else {
            printErrorLog4NotHaveStatusCode();
            return true;
        }
    }


    //************************ print log ************************
    private void printLog4NotHaveJsonTypeResponseBody() {
        LogUtil.ee(JsonResponseMessageParser.class, getErrorCommonMsg(), "not have json type response body...");
    }

    private void printErrorLog4IsErrorJsonType() {
        LogUtil.ee(JsonResponseMessageParser.class, getErrorCommonMsg(), "json text is error...");
    }

    private void printErrorLog4NotHaveStatusCode() {
        LogUtil.ee(JsonResponseMessageParser.class, getErrorCommonMsg(), " not have status code...");
    }

    private String[] getErrorCommonMsg() {
        RequestModel request = response.getHigherLevel().getHigherLevel();
        String version = request.getVersion();
        String name = request.getName();
        String group = request.getHigherLevel().getName();
        String responseContent = response.getContent();
        return new String[]{", version:", version, ", name:", name, ", group:", group,
                "\n response content:\n ", responseContent};
    }

}
