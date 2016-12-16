package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub;

import cn.ytxu.http_wrapper.config.property.param_type.ParamTypeEnum;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/24.
 * 获取到response中所有的能生成响应实体文件的output
 */
public class GetOutputsThatCanGenerateResponseEntityFileUtil {
    private ResponseModel response;

    public GetOutputsThatCanGenerateResponseEntityFileUtil(ResponseModel response) {
        this.response = response;
    }

    public List<OutputParamModel> start() {
        List<OutputParamModel> oaOutputs = new GetOutputsOfObjectAndArrayTypeUtil(response).start();
        return getOutputsThatCanGenerateResponseEntityFile(oaOutputs);
    }

    private List<OutputParamModel> getOutputsThatCanGenerateResponseEntityFile(List<OutputParamModel> oaOutputs) {
        List<OutputParamModel> outputs = new ArrayList<>();
        for (OutputParamModel oaOutput : oaOutputs) {
            if (canGenerateResponseEntityFile(oaOutput)) {
                outputs.add(oaOutput);
            }
        }
        return outputs;
    }

    private boolean canGenerateResponseEntityFile(OutputParamModel output) {
        if (output.isNonGenerationResponseEntityFileTag()) {
            return false;
        }
        if (output.getType() == ParamTypeEnum.ARRAY && output.getSubType() != ParamTypeEnum.OBJECT) {
            return false;
        }
        return true;
    }
}
