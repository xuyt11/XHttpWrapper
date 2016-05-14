package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.apacer.entity.CategoryEntity;
import cn.ytxu.apacer.entity.MethodEntity;
import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 分类的解析类
 *
 * @author ytxu 2016-3-21
 */
public class CategoryParser {

    public CategoryEntity getCategory(Element categoryEle, String name) {
        List<MethodEntity> methods = getMethods(categoryEle, name);
        LogUtil.i("name:" + name + ", methods.size():" + methods.size());

        // create a category based of name and methods
        CategoryEntity category = new CategoryEntity(name, methods);
        return category;
    }

    private List<MethodEntity> getMethods(Element categoryEle, String name) {
        String cssQuery = getCssQuery(name);

        // 2、搜索到属性值开头匹配的所有div元素
        Elements methodEles = getMethodElements(categoryEle, cssQuery);

        if (null == methodEles || methodEles.size() <= 0) {
            LogUtil.i("ytxu can`t find a method element of the category...");
            return null;
        }

        List<MethodEntity> methods = new ArrayList<>(methodEles.size());
        for (Iterator<Element> iterator = methodEles.iterator(); iterator.hasNext(); ) {
            Element methodEle = iterator.next();
            MethodEntity method = MethodParser.parseMethodElement(-1, -1, methodEle);
            methods.add(method);
        }
        return methods;
    }

    private String getCssQuery(String name) {
        // 1、生成一个需要搜索到的div元素中，id属性的开始字符串
        String replaceName = name.replace(" ", "_");// 防止出现类似：fa deal的分类，而在检索的id时，是以fa_deal开始的；
        String startId = "api-" + replaceName + "-";

        String cssQuery = "div[id^=" + startId + "]";// 匹配属性值开头：匹配以api-Account-开头的所有div元素
        return cssQuery;
    }

    private Elements getMethodElements(Element categoryEle, String cssQuery) {
        return JsoupParserUtil.getEles(categoryEle, cssQuery);
    }

}