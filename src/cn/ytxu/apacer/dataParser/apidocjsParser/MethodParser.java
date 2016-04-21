package cn.ytxu.apacer.dataParser.apidocjsParser;

import java.util.List;

import cn.ytxu.apacer.entity.ResponseEntity;
import org.jsoup.nodes.Element;

import cn.ytxu.util.CamelCaseUtils;

import cn.ytxu.apacer.entity.FieldEntity;
import cn.ytxu.apacer.entity.MethodEntity;
import org.jsoup.select.Elements;

/**
 * 方法的解析类
 * @author ytxu 2016-3-21
 *
 */
public class MethodParser {

	public static MethodEntity parseMethodElement(int categoryIndex, int methodIndex, Element methodEle) {
//		String methodId = methodEle.attr("id");
		
		Element articleEle = methodEle.select("article").first();
//		String methodCategory = articleEle.attr("data-group");
		String methodName = articleEle.attr("data-name");
		String methodVersion = articleEle.attr("data-version");
		
		String methodDescription = articleEle.select("div.pull-left > h1").first().text();
		
		Element preEle = articleEle.select("pre.prettyprint.language-html").first();
		String methodType = preEle.attr("data-type");
		String methodUrl = preEle.select("code").first().text();

        // headers, input params
		Elements tableEls = articleEle.select("table");
		List<FieldEntity> descParams = FieldParser.getDescParams(tableEls);

		Element fieldsetEle = articleEle.select("form.form-horizontal > fieldset").first();
		List<FieldEntity> headerFields = FieldParser.getHeaderFields(fieldsetEle);
		List<FieldEntity> inputFields = FieldParser.getInputParamFields(fieldsetEle);

		headerFields = FieldParser.replaceDescParam(headerFields, descParams);
		inputFields = FieldParser.replaceDescParam(inputFields, descParams);

        // response : output params
        Elements responseDescEls = articleEle.select("ul.nav.nav-tabs.nav-tabs-examples > li");// 该请求响应的描述
        Elements responseEls = articleEle.select("div.tab-content > div.tab-pane");// 请求响应报文的数据:响应头,响应体
        List<ResponseEntity> responses = ResponseParser.parser(categoryIndex, methodIndex, responseDescEls, responseEls);

        responses = OutputParamsParser.parser(categoryIndex, methodIndex, responses, descParams);

		MethodEntity method = new MethodEntity();
		method.setDescrption(methodDescription);
		method.setUrl(methodUrl);
		method.setMethodType(methodType);
		method.setHeaders(headerFields);
		method.setInputParameters(inputFields);
		method.setVersionCode(methodVersion);
		method.setMethodName(CamelCaseUtils.toCamelCase(methodName));
        method.setResponses(responses);
		
		method.setRESTfulApis(RESTfulAPIParser.parse(methodUrl));
		
		return method;
	}
	
	
	
}
