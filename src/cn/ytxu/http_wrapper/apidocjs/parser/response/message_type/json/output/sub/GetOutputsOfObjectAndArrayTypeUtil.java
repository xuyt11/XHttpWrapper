package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub;

import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/24.
 * 获取到response中所有的JSONObject与JSONArray类型的output
 */
public class GetOutputsOfObjectAndArrayTypeUtil {
    private ResponseModel response;

    public GetOutputsOfObjectAndArrayTypeUtil(ResponseModel response) {
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
                case OBJECT:
                case ARRAY:
                case MAP:// TODO need check map
                    oaOutputs.add(output);
                    break;
            }
        }
        return oaOutputs;
    }
}
