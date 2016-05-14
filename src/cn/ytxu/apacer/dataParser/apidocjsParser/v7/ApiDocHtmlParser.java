package cn.ytxu.apacer.dataParser.apidocjsParser.v7;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.apacer.entity.*;
import cn.ytxu.apacer.exception.BlankTextException;
import cn.ytxu.apacer.exception.TargetElementsNotFoundException;
import cn.ytxu.util.TextUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rx.Observable;
import rx.functions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ApiDocHtmlParser {
    private static final String CONTENT = "div.container-fluid > div.row-fluid > div#content";
    private static final String CSS_QUERY_GET_VERSION_CODE = CONTENT + " > div#project > div.pull-right > div.btn-group > ul#versions > li.version > a";
    private static final String CSS_QUERY_GET_SECTION = CONTENT + " > div#sections > section";
    private static final String CSS_QUERY_FIND_CATEGORY_NAME = "h1";


	public void main(String[] args) {
        ApiDocHtmlParser apiDocHtmlParser = new ApiDocHtmlParser();
        apiDocHtmlParser.parserApiDocHtmlCode2DocumentEntity();
	}
    
    public ApiDocHtmlParser() {
        super();
    }

	public DocumentEntity parserApiDocHtmlCode2DocumentEntity() {
		Document doc = JsoupParserUtil.getDocument(Config.getApiDocHtmlPath());
        // 1 get version
        List<String> versions = getVersionCodes(doc);

        Elements sectionEls = getSectionElements(doc);
        // 2 get status code
        Element statusCodeEle = getStatusCodeElement(sectionEls);
        sectionEls.remove(statusCodeEle);
        List<StatusCodeEntity> statusCodes = StatusCodeParser.parseStatusCodes(statusCodeEle);
        // 3 get apis
        List<ApiEnitity> apis = getApiEnitities(versions);
        List<CategoryEntity> categorys = getCategoryEntities(sectionEls);
        apis = addSameVersionMethodForCategory2TargetApi(apis, categorys);
        // 3 create document entity
        DocumentEntity docEntity = new DocumentEntity();
        docEntity.setVersions(versions);
        docEntity.setStatusCodes(statusCodes);
        docEntity.setApis(apis);
        return docEntity;
	}

	private List<String> getVersionCodes(Document doc) {
		Elements versionEls = JsoupParserUtil.getEles(doc, CSS_QUERY_GET_VERSION_CODE);
		if (null == versionEls || versionEls.size() <= 0) {
			throw new RuntimeException("ytxu error status: have not version code element...");
        }

		List<String> versions = new ArrayList<>(versionEls.size());
		for (int i = 0, size = versionEls.size(); i < size; i++) {
			versions.add(JsoupParserUtil.getText(versionEls.get(i)));
		}
		return versions;
	}

    private Elements getSectionElements(Document doc) {
        Elements sectionEls = JsoupParserUtil.getEles(doc, CSS_QUERY_GET_SECTION);
        if (null == sectionEls || sectionEls.size() <= 0) {
            throw new RuntimeException("ytxu error status: can not select the section elements, so stop running...");
        }
        return sectionEls;
    }

    private Element getStatusCodeElement(Elements sectionEls) {
        for (Iterator<Element> iterator = sectionEls.iterator(); iterator.hasNext(); ) {
            Element sectionEle = iterator.next();
            String name;
            try {
                // 1、get name of the category
                name = findCategoryName(sectionEle);
            } catch (TargetElementsNotFoundException ignore) {
                continue;
            } catch (BlankTextException ignore) {
                continue;
            }
            // 2 状态码
            if (!Config.statusCode.StatusCode.equals(name)) {
                continue;
            }
            return sectionEle;
        }
        throw new RuntimeException("ytxu error status: can not find status code section element...");
    }
    
    private List<ApiEnitity> getApiEnitities(List<String> versions) {
        List<ApiEnitity> apis = new ArrayList<>(versions.size());
        for (String version : versions) {
            apis.add(new ApiEnitity(version));
        }
        return apis;
    }
    
    private List<CategoryEntity> getCategoryEntities(Elements sectionEls) {
        List<CategoryEntity> categorys = new ArrayList<>();
        for (Iterator<Element> iterator = sectionEls.iterator(); iterator.hasNext(); ) {
            Element sectionEle = iterator.next();

            // 1、get name of the category
            String name;
            try {
                name = findCategoryName(sectionEle);
            } catch (TargetElementsNotFoundException e) {
                e.printStackTrace();
                continue;
            } catch (BlankTextException e) {
                e.printStackTrace();
                continue;
            }

            // 3 获取到了该分类的所有方法
            CategoryEntity category = CategoryParser.parserTheSectionElementAndCreateCategory(sectionEle, -1, name);
            categorys.add(category);
        }
        return categorys;
    }

    /** 将分类中的方法根据版本号，进行分类，并添加到相同版本好的api中 */
    private List<ApiEnitity> addSameVersionMethodForCategory2TargetApi(List<ApiEnitity> apis, List<CategoryEntity> categorys) {
        for (Iterator<CategoryEntity> iterator = categorys.iterator(); iterator.hasNext(); ) {
            CategoryEntity category = iterator.next();
            // 3.1 将该分类中的方法,按版本号进行分类
            addACategoryToTargetApi(apis, category);
        }
        return apis;
    }

    private String findCategoryName(Element sectionEle) throws TargetElementsNotFoundException, BlankTextException {
        Elements h1Els = JsoupParserUtil.getEles(sectionEle, CSS_QUERY_FIND_CATEGORY_NAME);
        if (null == h1Els || h1Els.size() <= 0) {
            throw new TargetElementsNotFoundException(" can`t find the h1 element");
        }

        String name = JsoupParserUtil.getText(h1Els.first());
        if (TextUtil.isBlank(name)) {
            throw new BlankTextException(" this name is blank for category");
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
