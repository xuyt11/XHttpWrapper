package cn.ytxu.http_wrapper.common.enums;

import cn.ytxu.http_wrapper.apidocjs.bean.field_container.field.FieldBean;
import cn.ytxu.http_wrapper.apidocjs.parser.status_code.parse_model.DefaultValueModelStatusCodeParser;
import cn.ytxu.http_wrapper.apidocjs.parser.status_code.parse_model.XCustomModelStatusCodeParser;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.http_wrapper.model.status_code.StatusCodeModel;

/**
 * Created by Administrator on 2016/9/20.
 * 状态码解析模式的类型枚举;
 * tip：可以自己，自行添加解析类型与解析器
 */
public enum StatusCodeParseModel {
    /**
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
    x_custom_model("我定义的一套规范解析") {
        @Override
        public StatusCodeModel createStatusCodeByApidocjsData(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
            return new XCustomModelStatusCodeParser(statusCodeGroup, field).start();
        }
    },
    default_value_model("使用apidocjs中，参数的默认值，作为状态码的值") {
        @Override
        public StatusCodeModel createStatusCodeByApidocjsData(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
            return new DefaultValueModelStatusCodeParser(statusCodeGroup, field).start();
        }
    };

    private final String tag;

    StatusCodeParseModel(String tag) {
        this.tag = tag;
    }

    public abstract StatusCodeModel createStatusCodeByApidocjsData(StatusCodeGroupModel statusCodeGroup, FieldBean field);

    public static StatusCodeParseModel getByEnumName(String parseModelName) {
        for (StatusCodeParseModel statusCodeParseModelType : StatusCodeParseModel.values()) {
            if (statusCodeParseModelType.name().equals(parseModelName)) {
                return statusCodeParseModelType;
            }
        }
        throw new NotFoundTargetStatusCodeParseModelExcpetion(parseModelName);
    }

    private static final class NotFoundTargetStatusCodeParseModelExcpetion extends IllegalArgumentException {
        public NotFoundTargetStatusCodeParseModelExcpetion(String parseModelName) {
            super("parse model name is " + parseModelName);
        }
    }
}
