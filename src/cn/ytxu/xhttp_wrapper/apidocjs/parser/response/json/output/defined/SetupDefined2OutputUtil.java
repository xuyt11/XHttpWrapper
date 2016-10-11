package cn.ytxu.xhttp_wrapper.apidocjs.parser.response.json.output.defined;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.json.output.sub.GetAllOutputUtil;
import cn.ytxu.xhttp_wrapper.model.field.FieldModel;
import cn.ytxu.xhttp_wrapper.model.response.OutputParamModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;

import java.util.ArrayList;
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
        List<FieldModel> defineds = getDefineds();
        if (isNotNeed2LoopSetup(defineds)) {
            return;
        }
        List<OutputParamModel> outputs = new GetAllOutputUtil(response).start();
        loopSetupDefined2Output(defineds, outputs);
    }

    private boolean isNotNeed2LoopSetup(List<FieldModel> defineds) {
        return Objects.isNull(defineds);
    }

    private List<FieldModel> getDefineds() {
        List<FieldModel> list = new ArrayList<>();
        response.getHigherLevel().getFieldGroups()
                .forEach(responseFieldGroup -> list.addAll(responseFieldGroup.getFields()));
        return list;
    }


    //********************** loop setup defined to output **********************
    private void loopSetupDefined2Output(List<FieldModel> defineds, List<OutputParamModel> outputs) {
        for (OutputParamModel output : outputs) {
            setupDefined2Output(output, defineds);
        }
    }

    private void setupDefined2Output(OutputParamModel output, List<FieldModel> defineds) {
        final String outputName = output.getName();
        for (FieldModel defined : defineds) {
            if (findTargetDefined(outputName, defined)) {
                output.setDefined(defined);
                return;
            }
        }
    }

    private boolean findTargetDefined(String outputName, FieldModel defined) {
        return outputName.equals(defined.getName());
    }

}
