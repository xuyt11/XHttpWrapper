package cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class StatusCodeParser {
    private List<StatusCodeGroupModel> statusCodeGroups;

    public StatusCodeParser(List<StatusCodeGroupModel> statusCodeGroups) {
        this.statusCodeGroups = statusCodeGroups;
    }

    public void start() {
        statusCodeGroups.forEach(statusCodeGroup -> paseStatusCodeGroup(statusCodeGroup));
    }

    private void paseStatusCodeGroup(StatusCodeGroupModel statusCodeGroup) {
        statusCodeGroup.getElement().getParameter().getFields().values().forEach(fields -> {
            List<StatusCodeModel> statusCodes = getStatusCodes(statusCodeGroup, fields);
            statusCodeGroup.addStatusCodes(statusCodes);
        });
    }

    private List<StatusCodeModel> getStatusCodes(StatusCodeGroupModel statusCodeGroup, List<FieldBean> fields) {
        List<StatusCodeModel> statusCodes = new ArrayList<>(fields.size());
        fields.forEach(field -> {
            StatusCodeModel statusCode = getStatusCode(statusCodeGroup, field);
            statusCodes.add(statusCode);
        });
        return statusCodes;
    }

    private StatusCodeModel getStatusCode(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
        StatusCodeParseModelType parseModel = ConfigWrapper.getStatusCode().getParseModel();
        return parseModel.parseApiData(statusCodeGroup, field);
    }

}
