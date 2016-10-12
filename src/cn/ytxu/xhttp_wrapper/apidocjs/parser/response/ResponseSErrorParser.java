package cn.ytxu.xhttp_wrapper.apidocjs.parser.response;

import cn.ytxu.xhttp_wrapper.config.property.base_response_entity_name.BaseResponseEntityNameProperty;
import cn.ytxu.xhttp_wrapper.model.VersionModel;
import cn.ytxu.xhttp_wrapper.model.response.OutputParamModel;
import cn.ytxu.xhttp_wrapper.model.response.ResponseModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by ytxu on 2016/9/6.
 */
public class ResponseSErrorParser {

    private VersionModel version;
    private List<ResponseModel> responses = new ArrayList<>();

    public ResponseSErrorParser(VersionModel version) {
        this.version = version;
        version.getRequestGroups().stream().map(requestGroup ->
                requestGroup.getRequests()).forEach(requests ->
                requests.forEach(request -> responses.addAll(request.getErrorContainer().getResponses())));
    }

    public void start() {
        List<OutputParamModel> errors = getErrors();
        List<OutputParamModel> subsOfErrors = getSubsOfErrors(errors);
        List<OutputParamModel> subs = deduplicated(subsOfErrors);
        version.setSubsOfErrors(subs);
    }

    private List<OutputParamModel> getErrors() {
        final String errorName = BaseResponseEntityNameProperty.get().getError();
        List<OutputParamModel> errors = new ArrayList<>();
        for (int i = 0; i < responses.size(); i++) {
            ResponseModel response = responses.get(i);
            List<OutputParamModel> outputs = response.getOutputs();
            try {
                OutputParamModel error = getError(errorName, outputs);
                errors.add(error);
            } catch (NotFoundErrorParamException ignore) {
            }
        }
        return errors;
    }

    private OutputParamModel getError(String errorName, List<OutputParamModel> outputs) {
        for (int i = 0; i < outputs.size(); i++) {
            OutputParamModel output = outputs.get(i);
            if (errorName.equalsIgnoreCase(output.getName())) {
                return output;
            }
        }
        throw new NotFoundErrorParamException();
    }

    private static class NotFoundErrorParamException extends RuntimeException {
    }

    private List<OutputParamModel> getSubsOfErrors(List<OutputParamModel> errors) {
        List<OutputParamModel> subs = new ArrayList<>();
        for (int i = 0; i < errors.size(); i++) {
            OutputParamModel error = errors.get(i);
            List<OutputParamModel> subsOfError = error.getSubs();
            if (subsOfError.size() > 0) {
                subs.addAll(subsOfError);
            }
        }
        return subs;
    }

    private List<OutputParamModel> deduplicated(List<OutputParamModel> subsOfErrors) {
        return new ArrayList<>(new HashSet(subsOfErrors));
    }

}
