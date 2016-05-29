package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.MethodEntity;
import cn.ytxu.apacer.entity.RESTfulApiEntity;
import cn.ytxu.apacer.entity.ResponseEntity;
import cn.ytxu.util.CamelCaseUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * 方法的解析类
 * @author ytxu 2016-3-21
 *
 */
public class MethodParser {
	private static final String CSS_QUERY_ARTICLE = "article";
	private static final String ATTR_DATA_NAME = "data-name";
	private static final String ATTR_DATA_VERSION = "data-version";
	private static final String CSS_QUERY_GET_METHOD_DESC = "div.pull-left > h1";
    private static final String CSS_QUERY_GET_TYPE_AND_URL_FOR_METHOD = "pre.prettyprint.language-html";
    private static final String ATTR_DATA_TYPE = "data-type";
    private static final String CSS_QUERY_GET_METHOD_URL = "code";
    private static final String CSS_QUERY_TABLE = "table";
    private static final String CSS_QUERY_FIELDSET = "form.form-horizontal > fieldset";
    private static final String CSS_QUERY_RESPONSE = "div.tab-content > div.tab-pane";
    private static final String CSS_QUERY_RESPONSE_DESC = "ul.nav.nav-tabs.nav-tabs-examples > li";


	public MethodEntity getMethod(Element methodEle) {
//		String methodId = methodEle.attr("id");
		Element articleEle = getArticleElement(methodEle);
//		String methodCategory = articleEle.attr("data-group");
		String methodName = getMethodName(articleEle);
		String methodVersion = getMethodVersion(articleEle);
		String methodDescription = getMethodDesc(articleEle);
		
		Element preEle = getMethodTypeAndUrlEle(articleEle);
		String methodType = getMethodType(preEle);
		String methodUrl = getMethodUrl(preEle);
        List<RESTfulApiEntity> resTfulApiEntities = getRESTfuls(methodUrl);

        // headers, input params
        List<FieldEntity> descParams = getDescParams(articleEle);
		Element fieldsetEle = getFieldsetEle(articleEle);
        List<FieldEntity> headerFields = getHeaderFields(descParams, fieldsetEle);
        List<FieldEntity> inputFields = getInputFields(descParams, fieldsetEle);
        // response : output params
        List<ResponseEntity> responses = getResponses(articleEle, descParams);

		MethodEntity method = new MethodEntity();
		method.setMethodName(methodName);
		method.setVersionCode(methodVersion);
		method.setDescrption(methodDescription);
		method.setMethodType(methodType);
		method.setUrl(methodUrl);
		method.setRESTfulApis(resTfulApiEntities);
		method.setHeaders(headerFields);
		method.setInputParameters(inputFields);
        method.setResponses(responses);

        // TODO 要删除，并用BaseEntity中的higherLevel进行替代
		ResponseEntity.setMethod(responses, method);
		
		return method;
	}

    private Element getArticleElement(Element methodEle) {
		return JsoupParserUtil.getFirstEle(methodEle, CSS_QUERY_ARTICLE);
	}

	private String getMethodName(Element articleEle) {
		String dataName = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_NAME);
		String methodName = CamelCaseUtils.toCamelCase(dataName);
		return methodName;
	}

	private String getMethodVersion(Element articleEle) {
		return JsoupParserUtil.getAttr(articleEle, ATTR_DATA_VERSION);
	}

    private String getMethodDesc(Element articleEle) {
        Element methodDescEle = JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_GET_METHOD_DESC);
        String methodDesc = JsoupParserUtil.getText(methodDescEle);
        return methodDesc;
    }

    private Element getMethodTypeAndUrlEle(Element articleEle) {
        return JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_GET_TYPE_AND_URL_FOR_METHOD);
    }

    private String getMethodType(Element preEle) {
        return JsoupParserUtil.getAttr(preEle, ATTR_DATA_TYPE);
    }

    private String getMethodUrl(Element preEle) {
        Element methodUrlEle = JsoupParserUtil.getFirstEle(preEle, CSS_QUERY_GET_METHOD_URL);
        String methodUrl = JsoupParserUtil.getText(methodUrlEle);
        return methodUrl;
    }

    private List<FieldEntity> getDescParams(Element articleEle) {
        Elements descParamCategoryEles = JsoupParserUtil.getEles(articleEle, CSS_QUERY_TABLE);// table elements
        return new FieldParser().getDescParams(descParamCategoryEles);
    }

    private Element getFieldsetEle(Element articleEle) {
        return JsoupParserUtil.getFirstEle(articleEle, CSS_QUERY_FIELDSET);
    }

    private List<FieldEntity> getHeaderFields(List<FieldEntity> descParams, Element fieldsetEle) {
        List<FieldEntity> headerFields = new FieldParser().getHeaderFields(fieldsetEle, descParams);
        return headerFields;
    }

    private List<FieldEntity> getInputFields(List<FieldEntity> descParams, Element fieldsetEle) {
        List<FieldEntity> inputFields = new FieldParser().getInputParamFields(fieldsetEle, descParams);
        return inputFields;
    }

    private List<ResponseEntity> getResponses(Element articleEle, List<FieldEntity> descParams) {
        Elements responseDescEls = getResponseDescEles(articleEle);// 该请求响应的描述
        Elements responseEls = getResponseEles(articleEle);// 请求响应报文的数据:响应头,响应体

        List<ResponseEntity> responses = new ResponseParser().getResponses(responseDescEls, responseEls);
        responses = new OutputParamsParser().parseResponseContent(responses, descParams);

        return responses;
    }

    private Elements getResponseEles(Element articleEle) {
        return JsoupParserUtil.getEles(articleEle, CSS_QUERY_RESPONSE);
    }

    private Elements getResponseDescEles(Element articleEle) {
        return JsoupParserUtil.getEles(articleEle, CSS_QUERY_RESPONSE_DESC);
    }

    private List<RESTfulApiEntity> getRESTfuls(String methodUrl) {
        return RESTfulAPIParser.parse(methodUrl);
    }
}
