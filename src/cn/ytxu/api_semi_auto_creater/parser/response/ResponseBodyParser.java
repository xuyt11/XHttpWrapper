package cn.ytxu.api_semi_auto_creater.parser.response;

import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.api_semi_auto_creater.parser.response.output.OutputParamParser;
import cn.ytxu.util.LogUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by ytxu on 2016/8/16.
 */
public class ResponseBodyParser {

    private ResponseModel response;
    private String body;

    public ResponseBodyParser(ResponseModel response) {
        this.response = response;
        this.body = response.getBody();
    }

    public void start() {
        //1 解析出body中json格式数据的所有字段；
        JSONObject bodyJObj;
        try {
            bodyJObj = JSON.parseObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
            printErrorLog();
            return;
        }

        parseStatusCode(bodyJObj);
        new OutputParamParser(response, bodyJObj).start();
    }

    private void parseStatusCode(JSONObject bodyJObj) {
        String statusCodeName = Property.getBreName().getStatusCode();
        if (bodyJObj.containsKey(statusCodeName)) {
            response.setStatusCode(String.valueOf(bodyJObj.getInteger(statusCodeName)));
        } else {
            LogUtil.ee(ResponseBodyParser.class, getLogTitle(), "can not have status code:", response.toString());
        }
    }

    private void printErrorLog() {
        String versionName = response.getHigherLevel().getHigherLevel().getHigherLevel().getName();
        String sectionName = response.getHigherLevel().getHigherLevel().getName();
        String requestName = response.getHigherLevel().getName();
        LogUtil.ee(ResponseBodyParser.class, "在版本号为", versionName, "，分类为", sectionName, "，请求名为", requestName,
                " \n下的响应desc为", response.getDesc(), "中返回数据的Json格式有问题！\n",
                "响应体为", body);
    }

    private String[] getLogTitle() {
        String versionName = response.getHigherLevel().getHigherLevel().getHigherLevel().getName();
        String sectionName = response.getHigherLevel().getHigherLevel().getName();
        String requestName = response.getHigherLevel().getName();
        return new String[]{"在版本号为", versionName, "，分类为", sectionName, "，请求名为", requestName,
                " \n下的响应desc为", response.getDesc()};
    }

}
