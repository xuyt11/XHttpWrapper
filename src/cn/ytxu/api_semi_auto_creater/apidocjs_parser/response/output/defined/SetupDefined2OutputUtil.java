package cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output.defined;

import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.output.sub.GetAllOutputUtil;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/8/21.
 * 对request中defineds进行辨析，设置到response字段上
 */
public class SetupDefined2OutputUtil {

    private ResponseModel response;

    public SetupDefined2OutputUtil(ResponseModel response) {
        this.response = response;
    }

    public void start() {
        List<DefinedParamModel> defineds = getDefineds();
        if (isNotNeed2LoopSetup(defineds)) {
            return;
        }
        List<OutputParamModel> outputs = new GetAllOutputUtil(response).start();
        loopSetupDefined2Output(defineds, outputs);
    }

    private boolean isNotNeed2LoopSetup(List<DefinedParamModel> defineds) {
        return Objects.isNull(defineds);
    }

    private List<DefinedParamModel> getDefineds() {
        return response.getHigherLevel().getDefinedParams();
    }


    //********************** loop setup defined to output **********************
    private void loopSetupDefined2Output(List<DefinedParamModel> defineds, List<OutputParamModel> outputs) {
        for (OutputParamModel output : outputs) {
            setupDefined2Output(output, defineds);
        }
    }

    private void setupDefined2Output(OutputParamModel output, List<DefinedParamModel> defineds) {
        final String outputName = output.getName();
        for (DefinedParamModel defined : defineds) {
            if (findTargetDefined(outputName, defined)) {
                output.setDefined(defined);
                return;
            }
        }
    }

    private boolean findTargetDefined(String outputName, DefinedParamModel defined) {
        return outputName.equals(defined.getName());
    }

}
