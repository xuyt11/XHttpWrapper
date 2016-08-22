package cn.ytxu.api_semi_auto_creater.parser.response;

import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

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


    //********************** loop setup defined to output **********************
    private void setupDontRequireAutomaticGenerationOutputObjectFileOfOutput(List<OutputParamModel> outputs) {
        List<OutputParamModel> oaOutputs = getOutputsOfJSONObjectAndJSONArrayType(outputs);
        for (OutputParamModel output : oaOutputs) {
            // TODO

        }
    }

    private List<OutputParamModel> getOutputsOfJSONObjectAndJSONArrayType(List<OutputParamModel> outputs) {
        List<OutputParamModel> oaOutputs = new ArrayList<>();
        for (OutputParamModel output : oaOutputs) {
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
