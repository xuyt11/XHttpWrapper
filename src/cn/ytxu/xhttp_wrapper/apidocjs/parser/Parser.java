package cn.ytxu.xhttp_wrapper.apidocjs.parser;

import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.util.FileUtil;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class Parser {

    public Parser() {
    }

    public void start() throws IOException {
        List<ApiDataBean> apiDatas = getApiDatas();
        // TODO
        // 1 get compile model
        // 2 create a order version model list
        // 3 dependent by compile model to create a object tree
        // 3.1 if is mutil_version, version-->section-->request
        // 3.2 else is no_version, section-->request, and must remove old version request, just keep the latest version

    }

    private List<ApiDataBean> getApiDatas() throws IOException {
        // 1 get api_data.json path
        String apiDataPath = Property.getApidocProperty().getApiDataJsonPath();
        // 2 get json data from file
        String apiDataJsonStr = FileUtil.getContent(apiDataPath);
        // 3 get java object array by json data
        return JSON.parseArray(apiDataJsonStr, ApiDataBean.class);
    }


}
