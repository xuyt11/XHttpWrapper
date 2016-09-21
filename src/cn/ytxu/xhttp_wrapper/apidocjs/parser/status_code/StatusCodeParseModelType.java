package cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code.parse_model.DefaultValueModelStatusCodeParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.status_code.parse_model.XCustomModelStatusCodeParser;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeGroupModel;
import cn.ytxu.xhttp_wrapper.model.status_code.StatusCodeModel;

/**
 * Created by Administrator on 2016/9/20.
 * 状态码解析模式的类型枚举;
 * tip：可以自己，自行添加解析类型与解析器
 */
public enum StatusCodeParseModelType {
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
        public StatusCodeModel parseApiData(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
            return new XCustomModelStatusCodeParser(statusCodeGroup, field).start();
        }
    },
    default_value_model("使用apidocjs中，参数的默认值，作为状态码的值") {
        @Override
        public StatusCodeModel parseApiData(StatusCodeGroupModel statusCodeGroup, FieldBean field) {
            return new DefaultValueModelStatusCodeParser(statusCodeGroup, field).start();
        }
    };

    private final String tag;

    StatusCodeParseModelType(String tag) {
        this.tag = tag;
    }

    public abstract StatusCodeModel parseApiData(StatusCodeGroupModel statusCodeGroup, FieldBean field);

    public static StatusCodeParseModelType getByEnumName(String parseModelName) {
        for (StatusCodeParseModelType statusCodeParseModelType : StatusCodeParseModelType.values()) {
            if (statusCodeParseModelType.name().equals(parseModelName)) {
                return statusCodeParseModelType;
            }
        }
        throw new NotFoundTargetStatusCodeParseModelExcpetion();
    }

    private static final class NotFoundTargetStatusCodeParseModelExcpetion extends IllegalArgumentException {
    }
}
