package cn.ytxu.api_semi_auto_creater.parser.response.output.sub;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;

import java.util.List;

/**
 * Created by ytxu on 2016/8/24.
 * 获取到response中所有的能生成响应实体文件的output
 */
public class GetOutputsThatCanGenerateResponseEntityFile {
    private ResponseModel response;

    public GetOutputsThatCanGenerateResponseEntityFile(ResponseModel response) {
        this.response = response;
    }

    public List<OutputParamModel> start() {
        List<OutputParamModel> oaOutputs = new GetOAOutputsUtil(response).start();
        return oaOutputs;
    }
}
