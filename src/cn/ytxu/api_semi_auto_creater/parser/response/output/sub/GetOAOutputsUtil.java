package cn.ytxu.api_semi_auto_creater.parser.response.output.sub;

import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/24.
 * 获取到response中所有的JSONObject与JSONArray类型的output
 */
public class GetOAOutputsUtil {
    private ResponseModel response;

    public GetOAOutputsUtil(ResponseModel response) {
        this.response = response;
    }

    public List<OutputParamModel> start() {
        List<OutputParamModel> outputs = new GetAllOutputUtil(response).start();
        List<OutputParamModel> oaOutputs = getOutputsOfJSONObjectAndJSONArrayType(outputs);
        return oaOutputs;
    }

    private List<OutputParamModel> getOutputsOfJSONObjectAndJSONArrayType(List<OutputParamModel> outputs) {
        List<OutputParamModel> oaOutputs = new ArrayList<>();
        for (OutputParamModel output : outputs) {
            switch (output.getType()) {
                case JSON_OBJECT:
                case JSON_ARRAY:
                    oaOutputs.add(output);
                    break;
            }
        }
        return oaOutputs;
    }
}
