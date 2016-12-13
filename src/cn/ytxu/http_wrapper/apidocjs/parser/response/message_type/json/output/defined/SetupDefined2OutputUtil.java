package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.defined;

import cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub.GetAllOutputUtil;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;
import cn.ytxu.http_wrapper.model.response.field.ResponseFieldModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/21.
 * 对request中defineds进行辨析，设置到response字段上
 */
public class SetupDefined2OutputUtil {

    private final ResponseModel response;
    private final List<ResponseFieldModel> defineds;

    public SetupDefined2OutputUtil(ResponseModel response) {
        this.response = response;
        this.defineds = getDefineds();
    }

    private List<ResponseFieldModel> getDefineds() {
        List<ResponseFieldModel> list = new ArrayList<>();
        response.getHigherLevel().getFieldGroups()
                .forEach(responseFieldGroup -> list.addAll(responseFieldGroup.getFields()));
        return list;
    }

    public void start() {
        if (isNotNeed2LoopSetup()) {
            return;
        }
        List<OutputParamModel> outputs = new GetAllOutputUtil(response).start();
        loopSetupDefined2Output(outputs);
    }

    private boolean isNotNeed2LoopSetup() {
        return defineds.isEmpty();
    }


    //********************** loop setup defined to output **********************
    private void loopSetupDefined2Output(List<OutputParamModel> outputs) {
        for (OutputParamModel output : outputs) {
            setupDefined2Output(output);
        }
    }

    private void setupDefined2Output(OutputParamModel output) {
        final String outputName = output.getName();
        for (ResponseFieldModel defined : defineds) {
            if (findTargetDefined(outputName, defined)) {
                output.setDefined(defined);
                return;
            }
        }
    }

    private boolean findTargetDefined(String outputName, ResponseFieldModel defined) {
        return outputName.equals(defined.getName());
    }

}
