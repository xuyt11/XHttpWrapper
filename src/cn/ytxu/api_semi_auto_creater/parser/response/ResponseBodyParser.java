package cn.ytxu.api_semi_auto_creater.parser.response;

import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
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
            // TODO 循环遍历JsonArray对象，而不只是获取第一个对象，
            // TODO 并且要将所有的字段中，若value为null的字段，判断之后是否有值，有值的话就要替换掉
            bodyJObj = JSON.parseObject(body);
        } catch (JSONException e) {
            e.printStackTrace();
            printErrorLog();
            return;
        }

    }

    private void printErrorLog() {
        String versionName = response.getHigherLevel().getHigherLevel().getHigherLevel().getName();
        String sectionName = response.getHigherLevel().getHigherLevel().getName();
        String requestName = response.getHigherLevel().getName();
        LogUtil.ee("在版本号为", versionName, "，分类为", sectionName, "，请求名为", requestName,
                " \n下的响应desc为", response.getDesc(), "中返回数据的Json格式有问题！\n",
                "响应体为", body);
    }

}
