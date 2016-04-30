package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.entity.CategoryEntity;
import cn.ytxu.apacer.entity.MethodEntity;
import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类的解析类
 * @author ytxu 2016-3-21
 *
 */
public class CategoryParser {


	public static CategoryEntity parserTheSectionElementAndCreateCategory(Element sectionEle, int index, String name) {
		LogUtil.i("index:" + index);

		
		// 3、get all method of the category
		List<MethodEntity> methods = getAllMethodsInThisCategory(sectionEle, index, name);
		
		// 4、create a category based of name and methods
		CategoryEntity category = new CategoryEntity();
		category.setName(name);
		category.setMethods(methods);
		LogUtil.i("name:" + name + ", methods.size():" + methods.size());
		
		return category;
	}

	
	private static List<MethodEntity> getAllMethodsInThisCategory(Element sectionEle, int index, String name) {
		// 1、生成一个需要搜索到的div元素中，id属性的开始字符串
		name = name.replace(" ", "_");// 防止出现类似：fa deal的分类，而在检索的id时，是以fa_deal开始的；
		String startId = "api-" + name + "-";
		
		// 2、搜索到属性值开头匹配的所有div元素
		Elements divEls = sectionEle.select("div[id^=" + startId + "]");// 匹配属性值开头：匹配以api-Account-开头的所有div元素
		
		if (null == divEls || divEls.size() <= 0) {
			LogUtil.i("index:" + index + ", ytxu can`t find a method element of the category...");
			return null;
		}
		
		List<MethodEntity> methods = new ArrayList<MethodEntity>();
		for(int i=0, count=divEls.size(); i<count; i++) {
			Element methodEle = divEls.get(i);
			MethodEntity method = MethodParser.parseMethodElement(index, i, methodEle);
			methods.add(method);// methods.add(null);
		}
		return methods;
	}

}