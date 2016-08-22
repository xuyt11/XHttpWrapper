package cn.ytxu.api_semi_auto_creater.parser.response;

import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 2016/8/21.
 */
public class SetupDefined2OutputUtil {

    private ResponseModel response;

    public SetupDefined2OutputUtil(ResponseModel response) {
        this.response = response;
    }

    public void start() {
        List<DefinedParamModel> defineds = getDefineds();
        List<OutputParamModel> outputs = getAllOutput();
        loopSetupDefined2Output(defineds, outputs);
        setupDontRequireAutomaticGenerationOutputObjectFileOfOutput(outputs);
    }

    private List<DefinedParamModel> getDefineds() {
        return response.getHigherLevel().getDefinedParams();
    }

    private List<OutputParamModel> getAllOutput() {
        List<OutputParamModel> outputs = response.getOutputs();
        if (outputs.size() == 0) {
            return outputs;
        }

        List<OutputParamModel> allOutputs = new ArrayList<>();
        allOutputs.addAll(outputs);

        List<OutputParamModel> allSubs = outputs;
        do {
            allSubs = getSubsFromOutputs(allSubs);
            if (canAddAllSubs2AllOutputs(allSubs)) {
                allOutputs.addAll(allSubs);
            }
        } while (canGetSubsAgain(allSubs));

        return allOutputs;
    }

    @NotNull
    private List<OutputParamModel> getSubsFromOutputs(List<OutputParamModel> outputs) {
        List<OutputParamModel> allSubs = new ArrayList<>();
        for (OutputParamModel output : outputs) {
            List<OutputParamModel> subs = output.getSubs();
            if (canAddSubs2AllSubs(subs)) {
                allSubs.addAll(subs);
            }
        }
        return allSubs;
    }

    private boolean canAddSubs2AllSubs(List<OutputParamModel> subs) {
        return subs.size() > 0;
    }

    private boolean canAddAllSubs2AllOutputs(List<OutputParamModel> allSubs) {
        return allSubs.size() > 0;
    }

    private boolean canGetSubsAgain(List<OutputParamModel> allSubs) {
        return allSubs.size() > 0;
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


    //********************** loop not need generation response file to object and array type`s output **********************
    private void setupDontRequireAutomaticGenerationOutputObjectFileOfOutput(List<OutputParamModel> outputs) {
        List<OutputParamModel> oaOutputs = getOutputsOfJSONObjectAndJSONArrayType(outputs);
        List<OutputParamModel> hasDataTypeOutputs = getAllOutputs4HasDataTypeFromOAOutputs(oaOutputs);
        for (OutputParamModel output : hasDataTypeOutputs) {
            if (hasSetAutoGenerationTag(output)) {
                continue;
            }
            loopSetDontRequireGenerationResponseEntityFileTag2TheSameDataTypeOutput(output, hasDataTypeOutputs);
        }
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
        return output.isDontRequireGenerationResponseEntityFileTag();
    }

    /**
     * 将oaOutputs中多有defined相同的output，设置为不需要再生成response entity file的
     */
    private void loopSetDontRequireGenerationResponseEntityFileTag2TheSameDataTypeOutput(OutputParamModel target, List<OutputParamModel> hasDataTypeOutputs) {
        for (OutputParamModel output : hasDataTypeOutputs) {
            if (output == target) {
                continue;
            }
            if (isTheSameDataType(target, output)) {
                output.setDontRequireGenerationResponseEntityFileTag();
            }
        }
    }

    private boolean isTheSameDataType(OutputParamModel target, OutputParamModel output) {
        return target.getDefined().getDataType().equals(output.getDefined().getDataType());
    }


}
