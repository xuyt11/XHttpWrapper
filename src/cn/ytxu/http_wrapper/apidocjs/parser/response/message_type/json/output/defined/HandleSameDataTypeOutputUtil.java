package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.defined;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub.GetOutputsOfObjectAndArrayTypeUtil;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/8/23.
 * 子孙节点或兄弟节点中类型(DataType)相同，判断与处理；
 * 将之后相同DataType的output设置为不需要自动生成response entity file的；
 */
public class HandleSameDataTypeOutputUtil {

    private ResponseModel response;

    public HandleSameDataTypeOutputUtil(ResponseModel response) {
        this.response = response;
    }

    public void start() {
        List<OutputParamModel> oaOutputs = new GetOutputsOfObjectAndArrayTypeUtil(response).start();
        List<OutputParamModel> hasDataTypeOutputs = getAllOutputs4HasDataTypeFromOAOutputs(oaOutputs);

        for (OutputParamModel output : hasDataTypeOutputs) {
            if (hasSetAutoGenerationTag(output)) {
                continue;
            }
            loopSetNonGenerationResponseEntityFileTag2TheSameDataTypeOutput(output, hasDataTypeOutputs);
        }
    }

    private List<OutputParamModel> getAllOutputs4HasDataTypeFromOAOutputs(List<OutputParamModel> oaOutputs) {
        List<OutputParamModel> hasDataTypes = new ArrayList<>();
        for (OutputParamModel output : oaOutputs) {
            if (notHasDataType(output)) {
                continue;
            }
            hasDataTypes.add(output);
        }
        return hasDataTypes;
    }

    private boolean notHasDataType(OutputParamModel output) {
        return Objects.isNull(output.getDefined()) || Objects.isNull(output.getDefined().getDataType());
    }

    private boolean hasSetAutoGenerationTag(OutputParamModel output) {
        return output.isNonGenerationResponseEntityFileTag();
    }

    /**
     * 将oaOutputs中多有defined相同的output，设置为不需要再生成response entity file的
     */
    private void loopSetNonGenerationResponseEntityFileTag2TheSameDataTypeOutput(OutputParamModel target, List<OutputParamModel> hasDataTypeOutputs) {
        for (OutputParamModel output : hasDataTypeOutputs) {
            if (output == target) {
                continue;
            }
            if (isTheSameDataType(target, output)) {
                output.setNonGenerationResponseEntityFileTag();
            }
        }
    }

    private boolean isTheSameDataType(OutputParamModel target, OutputParamModel output) {
        return target.getDefined().getDataType().equals(output.getDefined().getDataType());
    }


}
