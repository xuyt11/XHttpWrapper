package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.Config;
import cn.ytxu.apacer.ConfigV6;
import cn.ytxu.apacer.entity.*;
import cn.ytxu.apacer.exception.BlankTextException;
import cn.ytxu.apacer.exception.TargetElementsNotFoundException;
import cn.ytxu.util.LogUtil;
import cn.ytxu.util.TextUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rx.Observable;
import rx.functions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiDocHtmlParser {

	
	public void main(String[] args) {
        ApiDocHtmlParser apiDocHtmlParser = new ApiDocHtmlParser();
        apiDocHtmlParser.parserApiDocHtmlCode2DocumentEntity();
		
	}
    
    public ApiDocHtmlParser() {
        super();
    }

	public DocumentEntity parserApiDocHtmlCode2DocumentEntity() {
		Document doc = getDocument();

        List<String> versions = getVersionCodes(doc);
        List<ApiEnitity> apis = getApiEnitities(versions);

        Elements sectionEls = getSectionElements(doc);

        DocumentEntity docEntity = parseSectionElsForCategoryAndStatusCodeThenReturnDocmentEntity(apis, sectionEls);
        docEntity.setVersions(versions);
        docEntity.setApis(apis);
        return docEntity;
	}


    private Document getDocument() {
//		Connection conn = Jsoup.connect(ApiEnitity.ApiDocUrl);
//		conn.userAgent(UserAgentConfig.getWithRandom());
		try {
            Document doc = Jsoup.parse(new File(Config.ApiDocHtmlPath), Config.CharsetName);
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ytxu document is null, so can not run...");
        }
	}

	private List<String> getVersionCodes(Document doc) {
		Elements versionEls = doc.select("div.container-fluid > div.row-fluid > div#content > div#project > div.pull-right > div.btn-group > ul#versions > li.version > a");
		if (null == versionEls || versionEls.size() <= 0) {
			throw new RuntimeException("ytxu have not version code element...");
        }

		List<String> versions = new ArrayList<>(versionEls.size());
		for (int i = 0, size = versionEls.size(); i < size; i++) {
			versions.add(versionEls.get(i).text().trim());
		}
		return versions;
	}

    private List<ApiEnitity> getApiEnitities(List<String> versions) {
        List<ApiEnitity> apis = new ArrayList<>(versions.size());
        for (String version : versions) {
            apis.add(new ApiEnitity(version));
        }
        return apis;
    }

    private Elements getSectionElements(Document doc) {
        Elements sectionEls = doc.select("div.container-fluid > div.row-fluid > div#content > div#sections > section");
        if (null == sectionEls || sectionEls.size() <= 0) {
            throw new RuntimeException("ytxu can not select the section elements, so stop running...");
        }

        return sectionEls;
    }

    /** 解析sectionEle:<br>
     * 其中有一个状态码section,是属于doc的,<br>
     * 其他的都是request的分类,都是属于API的;且分类中的方法需要判断版本号,进行设置 */
    private DocumentEntity parseSectionElsForCategoryAndStatusCodeThenReturnDocmentEntity(List<ApiEnitity> apis, Elements sectionEls) {
        DocumentEntity docEntity = new DocumentEntity();
        for(int i=0, count=sectionEls.size(); i<count; i++) {
            parseSectionEle(apis, sectionEls, docEntity, i);
        }
        return docEntity;
    }

    private void parseSectionEle(List<ApiEnitity> apis, Elements sectionEls, DocumentEntity docEntity, int index) {
        Element sectionEle = sectionEls.get(index);

        // 1、get name of the category
        String name;
        try {
            name = findCategoryName(sectionEle, index);
        } catch (TargetElementsNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (BlankTextException e) {
            e.printStackTrace();
            return;
        }

        // 2 状态码
        if (ConfigV6.statusCode.StatusCode.equals(name)) {
            List<StatusCodeEntity> statusCodes = StatusCodeParser.parseStatusCodes(sectionEle);
            docEntity.setStatusCodes(statusCodes);
            return;
        }

        // 3 获取到了该分类的所有方法
        CategoryEntity category = CategoryParser.parserTheSectionElementAndCreateCategory(sectionEle, index, name);
        // 3.1 将该分类中的方法,按版本号进行分类
        addACategoryToTargetApi(apis, category);
    }

    private String findCategoryName(Element sectionEle, int index) throws TargetElementsNotFoundException, BlankTextException {
		Elements h1Els = sectionEle.select("h1");
		if (null == h1Els || h1Els.size() <= 0) {
			throw new TargetElementsNotFoundException("index:" + index + ", can`t find the h1 element");
		}

		String name = h1Els.first().text().trim();
        if (TextUtil.isBlank(name)) {
            throw new BlankTextException("index:" + index + ", this name is blank for category");
        }
		return name;
	}

    /** 根据版本号,将category中的method分类到docEntity的apis中 */
    private void addACategoryToTargetApi2(List<ApiEnitity> apis, CategoryEntity category) {
        // 1 为每一个API创建一个category
        for (ApiEnitity api : apis) {
            List<MethodEntity> methods = category.getMethods();
            List<MethodEntity> targetMethods = new ArrayList<>();
            if (null == methods || methods.size() <= 0) {// 没有方法,不需要为API添加request分类了;
                continue;
            }

            // 2 将api中category的methods中,与api版本不同的method remove
            final String versionCode = api.getCurrVersionCode();
            Iterator<MethodEntity> iterator = methods.iterator();
            while (iterator.hasNext()) {
                MethodEntity method = iterator.next();
                if (versionCode.equals(method.getVersionCode())) {
                    targetMethods.add(method);
                }
            }

            if (methods.size() <= 0) {// 没有方法,不需要为API添加request分类了;
                continue;
            }

            CategoryEntity cate = new CategoryEntity();
            cate.setName(category.getName());
            cate.setMethods(targetMethods);
            api.addACategory(cate);
        }
    }


    /** 根据版本号,将category中的method分类到docEntity的apis中 */
    private void addACategoryToTargetApi(List<ApiEnitity> apis, CategoryEntity category) {
        // 1 为每一个API创建一个category
        for (ApiEnitity api : apis) {
            // 2 将api中category的methods中,与api版本不同的method remove
            final String versionCode = api.getCurrVersionCode();
            final List<MethodEntity> targetMethods = new ArrayList<>();
            Observable.from(category.getMethods())
            .filter(new Func1<MethodEntity, Boolean>() {
                    @Override
                    public Boolean call(MethodEntity methodEntity) {
                        return versionCode.equals(methodEntity.getVersionCode());
                    }
                })
            .collect(
                new Func0<List<MethodEntity>>() {
                    @Override
                    public List<MethodEntity> call() {
                        return new ArrayList<MethodEntity>();
                    }
                },
                new Action2<List<MethodEntity>, MethodEntity>() {
                    @Override
                    public void call(List<MethodEntity> methods, MethodEntity methodEntity) {
                        methods.add(methodEntity);
                    }
                })
            .subscribe(new Action1<List<MethodEntity>>() {
                @Override
                public void call(List<MethodEntity> methodEntities) {

                    CategoryEntity cate = new CategoryEntity();
                    cate.setName(category.getName());
                    cate.setMethods(methodEntities);

                    api.addACategory(cate);
                }
            });
        }
    }


}
