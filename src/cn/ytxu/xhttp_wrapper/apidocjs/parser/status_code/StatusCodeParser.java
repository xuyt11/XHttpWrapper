package cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.model.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.StatusCodeModel;

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
        statusCodeGroups.forEach((StatusCodeGroupModel statusCodeGroup) -> {
            statusCodeGroup.getElement().getParameter().getFields().forEach((String statusCodeTypeName, List<FieldBean> fields) -> {
                List<StatusCodeModel> statusCodes = new ArrayList<>(fields.size());
                fields.forEach((FieldBean field) -> {
                    StatusCodeModel statusCode = convertStatusCodeModel(statusCodeGroup, field);
                    statusCodes.add(statusCode);
                });
                statusCodeGroup.addStatusCodes(statusCodes);
            });
        });
    }

    private StatusCodeModel convertStatusCodeModel(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
        String statusCodeName = field.getField();
        String description = field.getDescription();
        int separatorIndex = getSeparatorIndex(description);
        String statusCodeDesc = getStatusCodeDesc(description, separatorIndex);
        String statusCodeNumber = getStatusCodeNumber(description, separatorIndex);

        return new StatusCodeModel(statusCodeGroup, field, statusCodeName, statusCodeNumber, statusCodeDesc);
    }

    private int getSeparatorIndex(String description) {
        int separatorIndex = description.indexOf(",");
        if (separatorIndex <= 0) {// 小于0:没有找到; 等于0:没有statusCode
            throw new RuntimeException("the separatorIndex is not bigger 0");
        }
        return separatorIndex;
    }

    private String getStatusCodeDesc(String description, int separatorIndex) {
        return description.substring(separatorIndex + 1).trim();
    }

    private String getStatusCodeNumber(String description, int separatorIndex) {
        String statusCodeStr = description.substring(0, separatorIndex).trim();
        if (isLongStr(statusCodeStr)) {
            return statusCodeStr;
        }

        // 防止在状态码前面有其他字符串
        while (statusCodeStr.length() > 1) {
            statusCodeStr = statusCodeStr.substring(1, statusCodeStr.length());
            if (isLongStr(statusCodeStr)) {
                return statusCodeStr;
            }
        }
        throw new RuntimeException("the status code string can not convert long type value");
    }

    private boolean isLongStr(String content) {
        try {
            Long.parseLong(content);
            return true;
        } catch (NumberFormatException ignore) {// 不能转换成数字,有问题
            return false;
        }
    }
}
