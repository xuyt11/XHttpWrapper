package cn.ytxu.http_wrapper.apidocjs.parser.response.message_type.json.output.sub;

import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/8/24.
 * 获取每一个response中所有的output
 */
public class GetAllOutputUtil {

    private ResponseModel response;

    public GetAllOutputUtil(ResponseModel response) {
        this.response = response;
    }

    public List<OutputParamModel> start() {
        List<OutputParamModel> outputs = response.getOutputs();
        if (outputs.isEmpty()) {
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

}
