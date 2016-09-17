package cn.ytxu.xhttp_wrapper.apidocjs.parser;

import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.util.FileUtil;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;
import cn.ytxu.xhttp_wrapper.config.property.config.CompileModel;
import cn.ytxu.xhttp_wrapper.model.VersionModel;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/9/14.
 */
public class Parser {

    public Parser() {
    }

    public List<VersionModel> start() throws IOException {
        List<ApiDataBean> apiDatas = getApiDatas();
        CompileModel compileModel = Property.getConfigProperty().getCompileModel();
        return compileModel.generateApiTreeDependentByCompileModel(apiDatas);
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
