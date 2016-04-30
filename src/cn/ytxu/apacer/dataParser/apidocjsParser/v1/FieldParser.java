package cn.ytxu.apacer.dataParser.apidocjsParser.v1;

import java.util.ArrayList;
import java.util.List;

import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.ytxu.apacer.entity.FieldEntity;

/**
 * 字段的解析类
 * @author ytxu 2016-3-21
 *
 */
public class FieldParser {
	
	public static List<FieldEntity> getHeaderFields(Element fieldsetEle) {
		// header字段：class以header-fields结束
		Elements headerEls = fieldsetEle.select("div[class$=header-fields] > div.control-group");
		if (null == headerEls || headerEls.size() <= 0) {
			return null;
		}
		
		List<FieldEntity> headerFields = new ArrayList<FieldEntity>();
		for (int i=0, count=headerEls.size(); i<count; i++) {
			Element fieldEle = headerEls.get(i);
			FieldEntity field = getField(fieldEle);
			headerFields.add(field);
		}
		return headerFields;
	}

	public static List<FieldEntity> getInputParamFields(Element fieldsetEle) {
		Elements paramEls = fieldsetEle.select("div[class$=param-fields] > div.control-group");// 参数字段
		if (null == paramEls || paramEls.size() <= 0) {
			return null;
		}

		List<FieldEntity> paramFields = new ArrayList<FieldEntity>();
		for (int i=0, count=paramEls.size(); i<count; i++) {
			Element fieldEle = paramEls.get(i);
			FieldEntity field = getField(fieldEle);
			paramFields.add(field);
		}
		return paramFields;
	}
	
	private static FieldEntity getField(Element fieldEle) {
		String fieldName = fieldEle.select("label.control-label").first().text().trim();
		String fieldType = fieldEle.select("div.controls > div.input-append > span.add-on").first().text().trim();
		
		if (fieldType.startsWith("<p>")){// 去除前后的<p></p>
//			fieldType = fieldType.replace("&lt;p&gt;", "").replace("&lt;/p&gt;", "").trim();
			fieldType = fieldType.replace("<p>", "").replace("</p>", "").trim();
		}
		
		FieldEntity field = new FieldEntity(fieldName, fieldType, null);
		return field;
	}

	/**
	 * 获取到所有接口字段,包括:header,input params, output params
     */
	public static List<FieldEntity> getDescParams(Elements tableEls) {
		if (null == tableEls || tableEls.size() <= 0) {
			return null;
		}

		List<FieldEntity> descParams = new ArrayList<>();
		for (Element tableEle : tableEls) {
			Element preEle = tableEle.previousElementSibling();
			String paramCategory = null;// 参数的类别,有可能可以成为实体类的名称,也有可能是results\params等参数
			if (null != preEle && "h2".equalsIgnoreCase(preEle.tagName())) {
				paramCategory = preEle.text().trim();
			}

			Elements trEls = tableEle.select("tbody > tr");
			if (null == trEls || trEls.size() <= 0) {
				continue;
			}

			for (Element trEle : trEls) {
				Elements eles = trEle.children();
				if (null == eles || eles.size() != 3) {// 本来是要进行throw的,但是出现了StatusCode,没有type的问题.所以直接不处理吧
                    LogUtil.i(FieldParser.class, "table > tbody > tr > td, and the size of td`s children is not 3, and the tableEle is " + tableEle.text());
                    continue;
//					throw new RuntimeException("table > tbody > tr > td, and the size of td`s children is not 3, and the tableEle is " + tableEle.text());
				}

				String paramName = null;
                boolean isOptional;
                Elements spanEles = eles.get(0).select("span.label.label-optional");
                if (null == spanEles || spanEles.size() != 1) {
                    paramName = eles.get(0).text().trim();
                    isOptional = false;
                } else {
                    isOptional = true;
                    paramName = eles.get(0).textNodes().get(0).text().trim();
                }

				String paramType = eles.get(1).text().trim();
				String paramDesc = eles.get(2).text().trim();
				FieldEntity descField = new FieldEntity(paramName, paramType, paramDesc, isOptional, paramCategory);
				descParams.add(descField);
			}
		}
		return descParams;
	}

    /** 替换为更详细的FieldEntity */
    public static List<FieldEntity> replaceDescParam(List<FieldEntity> fields, List<FieldEntity> descParams) {

        if (null == fields || fields.size() <= 0 || null == descParams || descParams.size() <= 0) {
            return fields;
        }

        List<FieldEntity> outs = new ArrayList<>(fields.size());

        for (FieldEntity field : fields) {
            int indexOfDescParams = descParams.indexOf(field);
            if (indexOfDescParams < 0) {// 没有找到详细的FieldEntity对象
                outs.add(field);
            } else {
                outs.add(descParams.get(indexOfDescParams));
            }
        }
        return outs;
    }

}