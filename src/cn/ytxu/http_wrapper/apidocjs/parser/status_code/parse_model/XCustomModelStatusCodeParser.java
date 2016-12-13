package cn.ytxu.http_wrapper.apidocjs.parser.status_code.parse_model;

import cn.ytxu.http_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeModel;

/**
 * Created by Administrator on 2016/9/21.
 * 状态码的desc格式：<br>
 * Field            Description<br>
 * OK               (0, '')<br>
 * UNAUTHORIZED     (1, '登录状态已过期，请重新登入')<br>
 * SERVER_ERROR     (5, '服务器错误') # 5XX 服务器错误<br>
 * <p>
 * Field        format:statusCodeName<br>
 * Description  format1：statusCodeNumber, statusCodeDesc<br>
 * statusCodeDesc String-->statusCodeDesc<br>
 * Description  format2：(statusCodeNumber, statusCodeDesc)<br>
 * statusCodeDesc String-->statusCodeDesc)<br>
 * Description  format3：(statusCodeNumber, statusCodeDesc)xxx<br>
 * statusCodeDesc String-->statusCodeDesc)xxx<br>
 */
public class XCustomModelStatusCodeParser {
    private StatusCodeGroupModel statusCodeGroup;
    private FieldBean field;

    public XCustomModelStatusCodeParser(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
        this.statusCodeGroup = statusCodeGroup;
        this.field = field;
    }

    public StatusCodeModel start() {
        String statusCodeGroupName = field.getGroup();
        String statusCodeName = field.getField();

        final String description = field.getDescription();
        int separatorIndex = getSeparatorIndex(description);

        String statusCodeDesc = getStatusCodeDesc(description, separatorIndex);
        int statusCodeNumber = getStatusCodeNumber(description, separatorIndex);

        return ApidocjsHelper.getField().createStatusCode(statusCodeGroup, statusCodeGroupName, statusCodeName, statusCodeNumber, statusCodeDesc);
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

    private int getStatusCodeNumber(String description, int separatorIndex) {
        String statusCodeNumberStr = description.substring(0, separatorIndex).trim();
        if (isIntegerStr(statusCodeNumberStr)) {
            return Integer.valueOf(statusCodeNumberStr);
        }

        // 防止在状态码前面有其他字符串
        while (statusCodeNumberStr.length() > 1) {
            statusCodeNumberStr = statusCodeNumberStr.substring(1, statusCodeNumberStr.length());
            if (isIntegerStr(statusCodeNumberStr)) {
                return Integer.valueOf(statusCodeNumberStr);
            }
        }
        throw new IllegalArgumentException("the status code string can not convert long type value\n"
                + "and the field bean is " + field.toString());
    }

    private boolean isIntegerStr(String content) {
        try {
            Long.parseLong(content);
            return true;
        } catch (NumberFormatException ignore) {// 不能转换成数字,有问题
            return false;
        }
    }
}
