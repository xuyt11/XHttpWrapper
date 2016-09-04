package cn.ytxu.api_semi_auto_creater.parser.status_code;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.model.status_code.StatusCodeCategoryModel;
import cn.ytxu.api_semi_auto_creater.model.status_code.StatusCodeModel;
import cn.ytxu.api_semi_auto_creater.parser.defined.Defined4StatusCodeTypeParser;
import cn.ytxu.api_semi_auto_creater.parser.request.RequestParser;
import cn.ytxu.api_semi_auto_creater.parser.defined.DefinedsParser;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * 状态码解析
 * 2016-03-30
 */
public class StatusCodeParser {

    private StatusCodeCategoryModel sccModel;
    private Element baseEle;

    public StatusCodeParser(StatusCodeCategoryModel sccModel) {
        this.sccModel = sccModel;
        this.baseEle = sccModel.getElement();
    }

    public void start() {
        Element articleEle = JsoupParserUtil.getFirstEle(baseEle, RequestParser.CSS_QUERY_ARTICLE);
        List<DefinedParamModel> definds = new DefinedsParser(null, articleEle) {
            @Override
            protected void parseDefined(DefinedParamModel definedParam) {
                new Defined4StatusCodeTypeParser(definedParam).start();
            }
        }.start();

        List<StatusCodeModel> statusCodes = new ArrayList<>(definds.size());
        for (DefinedParamModel defind : definds) {
            statusCodes.add(getStatusCode(defind));
        }
        sccModel.setStatusCodes(statusCodes);
    }

    /**
     * status code descprition:<br>
     * (0, '')<br>
     * (1, '登录状态已过期，请重新登入')<br>
     * (5, '服务器错误') # 5XX 服务器错误
     */
    private StatusCodeModel getStatusCode(DefinedParamModel defind) {
        String statusCodeName = defind.getName();
        String description = defind.getDescription();
        int separatorIndex = getSeparatorIndex(description);
        String statusCodeDesc = getStatusCodeDesc(description, separatorIndex);
        String statusCodeNumber = getStatusCodeNumber(description, separatorIndex);

        return new StatusCodeModel(sccModel, statusCodeName, statusCodeNumber, statusCodeDesc);
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
