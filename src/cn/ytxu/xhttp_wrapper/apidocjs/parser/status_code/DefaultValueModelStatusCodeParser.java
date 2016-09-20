package cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.model.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.StatusCodeModel;

/**
 * Created by ytxu on 2016/9/21.
 */
public class DefaultValueModelStatusCodeParser {
    private StatusCodeGroupModel statusCodeGroup;
    private FieldBean field;

    public DefaultValueModelStatusCodeParser(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
        this.statusCodeGroup = statusCodeGroup;
        this.field = field;
    }

    public StatusCodeModel start() {
        String statusCodeGroupName = field.getGroup();
        String statusCodeName = field.getField();
        String statusCodeNumber = field.getDefaultValue();
        String statusCodeDesc = field.getDescription();

        return new StatusCodeModel(statusCodeGroup, field, statusCodeGroupName, statusCodeName, statusCodeNumber, statusCodeDesc);
    }
}
