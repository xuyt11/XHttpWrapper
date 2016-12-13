package cn.ytxu.http_wrapper.apidocjs.parser.status_code;

import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.http_wrapper.common.enums.StatusCodeParseModel;
import cn.ytxu.http_wrapper.config.ConfigWrapper;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeModel;

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
        apiData.getParameter().getFields().values()
                .forEach(fields -> createStatusCodesByFields(statusCodeGroup, fields));
    }

    private void createStatusCodesByFields(StatusCodeGroupModel statusCodeGroup, List<FieldBean> fields) {
        for (FieldBean field : fields) {
            createStatusCodeByFieldBean(statusCodeGroup, field);
        }
    }

    private StatusCodeModel createStatusCodeByFieldBean(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
        return parseModel.createStatusCodeByApidocjsData(statusCodeGroup, field);
    }

}
