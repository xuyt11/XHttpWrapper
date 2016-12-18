package cn.ytxu.http_wrapper.apidocjs.parser.response;

import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.version.VersionModel;
import cn.ytxu.http_wrapper.model.response.OutputParamModel;
import cn.ytxu.http_wrapper.model.response.ResponseModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Created by ytxu on 2016/9/6.
 */
public class ResponseSErrorParser {

    private final VersionModel version;
    private final String errorOutputName;// 响应中的错误字段的名称
    private final List<ResponseModel> responses;

    public ResponseSErrorParser(VersionModel version) {
        this.version = version;
        this.errorOutputName = ConfigWrapper.getResponse().getError();
        this.responses = new ArrayList<>(200);
        version.getRequestGroups().stream().map(requestGroup -> requestGroup.getRequests())
                .forEach(requests -> requests.forEach(request -> {
                    responses.addAll(request.getResponseContainer().getErrorResponses());
                    responses.addAll(request.getResponseContainer().getSuccessResponses());
                }));
    }

    public void start() {
        List<OutputParamModel> errors = getErrors();
        List<OutputParamModel> subsOfErrors = getSubsOfErrors(errors);
        List<OutputParamModel> subs = deduplicated(subsOfErrors);
        Collections.sort(subs);
        version.setSubsOfErrors(subs);
    }

    private List<OutputParamModel> getErrors() {
        List<OutputParamModel> errors = new ArrayList<>();
        for (ResponseModel response : responses) {
            List<OutputParamModel> outputs = response.getOutputs();
            try {
                OutputParamModel error = getError(outputs);
                errors.add(error);
            } catch (NotFoundErrorParamException ignore) {
            }
        }
        return errors;
    }

    private OutputParamModel getError(List<OutputParamModel> outputs) {
        for (OutputParamModel output : outputs) {
            if (errorOutputName.equalsIgnoreCase(output.getName())) {
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
