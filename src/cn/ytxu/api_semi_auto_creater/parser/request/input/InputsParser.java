package cn.ytxu.api_semi_auto_creater.parser.request.input;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.request.InputParamModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.util.ListUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016/8/2.
 */
public class InputsParser {
    private static final String CSS_QUERY_FIELDSET = "form.form-horizontal > fieldset";
    /**
     * header字段：class以header-fields结束
     */
    private static final String CSS_QUERY_GET_HEADER = "div[class$=header-fields] > div.control-group";
    /**
     * 输入参数字段：class以param-fields结束
     */
    private static final String CSS_QUERY_GET_INPUT_PARAM = "div[class$=param-fields] > div.control-group";


    private RequestModel request;
    private Element articleEle;

    public InputsParser(RequestModel request, Element articleEle) {
        this.request = request;
        this.articleEle = articleEle;
    }

    public void start() {
        Element fieldsetEle = JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_FIELDSET);
        getHeaderFields(fieldsetEle);
        getInputFields(fieldsetEle);
    }

    private void getHeaderFields(Element fieldsetEle) {
        List<InputParamModel> headers = getInputParams(fieldsetEle, CSS_QUERY_GET_HEADER);
        request.setHeaders(headers);
    }

    private void getInputFields(Element fieldsetEle) {
        List<InputParamModel> inputs = getInputParams(fieldsetEle, CSS_QUERY_GET_INPUT_PARAM);
        request.setInputs(inputs);
    }

    private List<InputParamModel> getInputParams(Element fieldsetEle, String cssQuery) {
        Elements fieldEls = JsoupParserUtil.getEles(fieldsetEle, cssQuery);
        if (ListUtil.isEmpty(fieldEls)) {
            return Collections.EMPTY_LIST;
        }

        List<InputParamModel> inputs = new ArrayList<>(fieldEls.size());
        for (Element fieldEle : fieldEls) {
            InputParamModel input = new InputParamModel(request, fieldEle);
            input = new InputFieldParser(input).start();
            inputs.add(input);
        }
        return inputs;
    }
}
