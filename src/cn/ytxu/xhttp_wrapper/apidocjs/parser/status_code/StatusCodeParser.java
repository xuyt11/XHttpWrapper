package cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.xhttp_wrapper.common.enums.StatusCodeParseModel;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class StatusCodeParser {
    private final StatusCodeParseModel parseModel;
    private final List<StatusCodeGroupModel> statusCodeGroups;

    public StatusCodeParser(List<StatusCodeGroupModel> statusCodeGroups) {
        this.parseModel = ConfigWrapper.getStatusCode().getParseModel();
        this.statusCodeGroups = statusCodeGroups;
    }

    public void start() {
        for (StatusCodeGroupModel statusCodeGroup : statusCodeGroups) {
            paseStatusCodeGroup(statusCodeGroup);
        }
    }

    private void paseStatusCodeGroup(StatusCodeGroupModel statusCodeGroup) {
        ApiDataBean apiData = ApidocjsHelper.getApiData().getApiData(statusCodeGroup);
        apiData.getParameter().getFields().values().forEach(fields -> {
            List<StatusCodeModel> statusCodes = createStatusCodesByFields(statusCodeGroup, fields);
            statusCodeGroup.addStatusCodes(statusCodes);
        });
    }

    private List<StatusCodeModel> createStatusCodesByFields(StatusCodeGroupModel statusCodeGroup, List<FieldBean> fields) {
        List<StatusCodeModel> statusCodes = new ArrayList<>(fields.size());
        for (FieldBean field : fields) {
            StatusCodeModel statusCode = createStatusCodeByFieldBean(statusCodeGroup, field);
            statusCodes.add(statusCode);
        }
        return statusCodes;
    }

    private StatusCodeModel createStatusCodeByFieldBean(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
        return parseModel.createStatusCodeByApidocjsData(statusCodeGroup, field);
    }

}
