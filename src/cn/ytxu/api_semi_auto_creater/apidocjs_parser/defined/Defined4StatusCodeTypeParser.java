package cn.ytxu.api_semi_auto_creater.apidocjs_parser.defined;

import cn.ytxu.util.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.model.request.DefinedParamModel;
import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 解析status code中的defined
 */
public class Defined4StatusCodeTypeParser {
    private static final int DEFINED_PARAM_ATTR_NUMBER = 2;// 参数的属性个数
    private static final int DEFINED_PARAM_ATTR_FIELD = 0;// 参数的字段,在tr标签中的index
    private static final int DEFINED_PARAM_ATTR_DESC = 1;// 参数的描述,在tr标签中的index

    private DefinedParamModel definedModel;
    private Element baseEle;
    private Elements descParamAttrEles;

    public Defined4StatusCodeTypeParser(DefinedParamModel baseEntity) {
        super();
        this.definedModel = baseEntity;
        this.baseEle = baseEntity.getElement();
    }

    /**
     * 下面是一个字段的描述信息例子：<br>
     * name             Description<br>
     * UNAUTHORIZED     (1, '登录状态已过期，请重新登入')
     */
    public void start() {
        findChildren();

        if (isNotDescParamElement()) {// status code 就是两列(name+desc)
            LogUtil.i(Defined4StatusCodeTypeParser.class, "table > tbody > tr > td, and the size of td`s children is not 2");
            return;
        }

        getName();
        getDesc();
    }

    private void findChildren() {
        descParamAttrEles = baseEle.children();
    }

    private boolean isNotDescParamElement() {
        return null == descParamAttrEles || descParamAttrEles.size() != DEFINED_PARAM_ATTR_NUMBER;
    }

    private void getName() {
        Element fieldEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_FIELD);
        String paramName = JsoupParserUtil.getText(fieldEle);
        definedModel.setName(paramName);
    }

    private void getDesc() {
        Element descEle = descParamAttrEles.get(DEFINED_PARAM_ATTR_DESC);
        String paramDesc = JsoupParserUtil.getText(descEle);
        definedModel.setDescription(paramDesc);
    }

}