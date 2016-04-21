package cn.ytxu.apacer.dataParser.apidocjsParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.ytxu.apacer.entity.DocumentEntity;
import cn.ytxu.apacer.entity.MethodEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.ytxu.util.LogUtil;

import cn.ytxu.apacer.Config;
import cn.ytxu.apacer.entity.ApiEnitity;
import cn.ytxu.apacer.entity.CategoryEntity;

public class ApiDocHtmlParser {

	
	public static void main(String[] args) {
		parser();
		
	}

	public static DocumentEntity parser() {
		Document doc = getDocument();
		if (null == doc) {
			LogUtil.i("ytxu document is null, so can not run...");
			return null;
		}
		
		Elements sectionEls = doc.select("div.container-fluid > div.row-fluid > div#content > div#sections > section");
		if (null == sectionEls || sectionEls.size() <= 0) {
			LogUtil.i("ytxu can not select the section elements, so stop running...");
			return null;
		}


        DocumentEntity docEntity = new DocumentEntity();
        List<String> versions = getVersionCodes(doc);
        docEntity.setVersions(versions);

        List<ApiEnitity> apis = new ArrayList<>(versions.size());
        for (String version : versions) {
            apis.add(new ApiEnitity(version));
        }
        docEntity.setApis(apis);

        for(int i=0, count=sectionEls.size(); i<count; i++) {
            parserEle(sectionEls, docEntity, i);
		}
		
		return docEntity;
	}

    /** 解析sectionEle:<br>
     * 其中有一个状态码section,是属于doc的,<br>
     * 其他的都是request的分类,都是属于API的;且分类中的方法需要判断版本号,进行设置 */
    private static void parserEle(Elements sectionEls, DocumentEntity docEntity, int i) {
        Element sectionEle = sectionEls.get(i);
        // 1、get name of the category
        String name = findCategoryName(sectionEle, i);
        if (null == name || name.trim().length() <= 0) {
            LogUtil.i("index:" + i + ", ytxu find the name of category. but the name string is null or empty...");
            return;
        }
        name = name.trim();

        // 2 状态码
        if ("StatusCode".equals(name)) {
            docEntity.setStatusCodes(StatusCodeParser.parseStatusCodes(sectionEle));
            return;
        }

        // 3 获取到了该分类的所有方法
        CategoryEntity category = CategoryParser.parserTheSectionElementAndCreateCategory(sectionEle, i, name);
        // 3.1 将该分类中的方法,按版本号进行分类
        addACategoryToTargetApi(docEntity, category);
        LogUtil.i("index:" + i + ", category:" + (null == category ? " is null..." : category.toString()));
    }

    private static Document getDocument() {
//		Connection conn = Jsoup.connect(ApiEnitity.ApiDocUrl);
//		conn.userAgent(UserAgentConfig.getWithRandom());
		Document doc = null;
		try {
			doc = Jsoup.parse(new File(Config.ApiDocHtmlPath), Config.CharsetName);
//			doc = conn.get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	private static List<String> getVersionCodes(Document doc) {
		Elements versionEls = doc.select("div.container-fluid > div.row-fluid > div#content > div#project > div.pull-right > div.btn-group > ul#versions > li.version > a");
		if (null == versionEls || versionEls.size() <= 0) {
			return null;
		}

		List<String> versions = new ArrayList<>(versionEls.size());
		for (int i = 0, size = versionEls.size(); i < size; i++) {
			versions.add(versionEls.get(i).text().trim());
		}
		return versions;
	}

	private static String findCategoryName(Element sectionEle, int index) {
		Elements h1Els = sectionEle.select("h1");
		if (null == h1Els || h1Els.size() <= 0) {
			LogUtil.i("index:" + index + ", ytxu can not find the name of category. the cause is can`t find the h1 element");
			return null;
		}

		// filter :过滤掉StatusCode分类，这里面只有状态码信息
		String name = h1Els.first().text();
//		if (null != name && "StatusCode".equals(name)) {
//			return null;
//		}

		// find the h1 element and return the text;
		return name;
	}

    /** 根据版本号,将category中的method分类到docEntity的apis中 */
    private static void addACategoryToTargetApi(DocumentEntity docEntity, CategoryEntity category) {
        List<ApiEnitity> apis = docEntity.getApis();

        // 1 为每一个API创建一个category
        for (ApiEnitity api : apis) {
            List<MethodEntity> methods = category.getMethods();
            List<MethodEntity> methodList = new ArrayList<>();
            if (null == methods || methods.size() <= 0) {// 没有方法,不需要为API添加request分类了;
                continue;
            }

            // 2 将api中category的methods中,与api版本不同的method remove
            final String versionCode = api.getCurrVersionCode();
            Iterator<MethodEntity> iterator = methods.iterator();
            while (iterator.hasNext()) {
                MethodEntity method = iterator.next();
                if (versionCode.equals(method.getVersionCode())) {
                    methodList.add(method);
                }
            }

            if (methods.size() <= 0) {// 没有方法,不需要为API添加request分类了;
                continue;
            }

            CategoryEntity cate = new CategoryEntity();
            cate.setName(category.getName());
            cate.setMethods(methodList);
            api.addACategory(cate);
        }


    }


}
