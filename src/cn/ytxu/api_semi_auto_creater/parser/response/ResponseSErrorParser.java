package cn.ytxu.api_semi_auto_creater.parser.response;

import cn.ytxu.api_semi_auto_creater.config.property.base_response_entity_name.BaseResponseEntityNameProperty;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.response.OutputParamModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/6.
 */
public class ResponseSErrorParser {

    private DocModel doc;
    private List<ResponseModel> responses;

    public ResponseSErrorParser(DocModel doc, List<ResponseModel> responses) {
        this.doc = doc;
        this.responses = responses;
    }

    public void start() {
        List<OutputParamModel> errors = getErrors();
        List<OutputParamModel> subs = getSubsOfErrors(errors);
        doc.setSubsOfErrors(subs);
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

}
