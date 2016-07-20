package cn.ytxu.api_semi_auto_creater.parser.base;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.LogUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/7/20.
 * 基础解析器：解析出直到request name,version为止
 */
public class BaseParser {

    public BaseParser() {

    }

    public void start() {
        DocEntity doc = new DocParser().start();
        for (DocEntity.SectionEntity section : doc.getSections()) {
            new SectionParser(section).start();
        }

        // TODO convert to model
        System.out.println(doc.toString());

    }

    public static class DocParser {
        private static final String CONTENT = "div.container-fluid > div.row-fluid > div#content";
        private static final String CSS_QUERY_GET_VERSION_CODE = CONTENT + " > div#project > div.pull-right > div.btn-group > ul#versions > li.version > a";
        private static final String CSS_QUERY_GET_SECTION = CONTENT + " > div#sections > section";
        private static final String CSS_QUERY_FIND_CATEGORY_NAME = "h1";

        private DocEntity docEntity;

        public DocParser() {
            super();
        }

        public DocEntity start() {
            createDoc();
            getVersionCodes();
            List<DocEntity.SectionEntity> sections = getSections();
            getStatusCodeAndSections(sections);
            return docEntity;
        }

        private void createDoc() {
            Document doc = JsoupParserUtil.getDocument(Config.getApiDocHtmlPath());
            docEntity = new DocEntity(null, doc);
        }

        private void getVersionCodes() {
            Elements versionEls = JsoupParserUtil.getEles(docEntity.getElement(), CSS_QUERY_GET_VERSION_CODE);
            List<String> versions = new ArrayList<>(versionEls.size());
            for (Element versionEle : versionEls) {
                versions.add(JsoupParserUtil.getText(versionEle));
            }
            docEntity.setVersions(versions);
        }

        private List<DocEntity.SectionEntity> getSections() {
            Elements sectionEls = JsoupParserUtil.getEles(docEntity.getElement(), CSS_QUERY_GET_SECTION);

            List<DocEntity.SectionEntity> sections = new ArrayList<>(sectionEls.size());
            for (Element sectionEle : sectionEls) {
                DocEntity.SectionEntity section = getSection(sectionEle);
                sections.add(section);
            }

            return sections;
        }

        private DocEntity.SectionEntity getSection(Element sectionEle) {
            String name = findSectionName(sectionEle);
            return new DocEntity.SectionEntity(docEntity, sectionEle, name);
        }

        private String findSectionName(Element sectionEle) {
            Elements h1Els = JsoupParserUtil.getEles(sectionEle, CSS_QUERY_FIND_CATEGORY_NAME);
            return JsoupParserUtil.getText(h1Els.first());
        }

        private void getStatusCodeAndSections(List<DocEntity.SectionEntity> sections) {
            try {
                DocEntity.SectionEntity statusCode = getStatusCodeEntity(sections);
                docEntity.setStatusCode(statusCode);
                sections.remove(statusCode);
            } catch (RuntimeException ignore) {
                ignore.printStackTrace();
            }

            docEntity.setSections(sections);
        }

        private DocEntity.SectionEntity getStatusCodeEntity(List<DocEntity.SectionEntity> sections) {
            for (DocEntity.SectionEntity section : sections) {
                if (!Config.statusCode.StatusCode.equals(section.getName())) {
                    continue;
                }
                return section;
            }
            throw new RuntimeException("没有找到状态码字段");
        }

    }

    public static class SectionParser {
        private static final String CSS_QUERY_ARTICLE = "article";
        private static final String ATTR_DATA_NAME = "data-name";
        private static final String ATTR_DATA_VERSION = "data-version";

        private DocEntity.SectionEntity sectionEntity;

        public SectionParser(DocEntity.SectionEntity section) {
            super();
            sectionEntity = section;
        }

        public void start() {
            // 1、生成一个需要搜索到的div元素中，id属性的开始字符串
            String cssQuery = getCssQuery();
            // 2、搜索到属性值开头匹配的所有div元素
            Elements requestEles = getRequestElements(cssQuery);

            getRequests(requestEles);
        }

        private String getCssQuery() {
            String replaceName = sectionEntity.getName().replace(" ", "_");// 防止出现类似：fa deal的分类，而在检索的id时，是以fa_deal开始的；
            String startId = "api-" + replaceName + "-";
            String cssQuery = "div[id^=" + startId + "]";// 匹配属性值开头：匹配以api-Account-开头的所有div元素
            return cssQuery;
        }

        private Elements getRequestElements(String cssQuery) {
            return JsoupParserUtil.getEles(sectionEntity.getElement(), cssQuery);
        }

        private void getRequests(Elements requestEles) {
            if (null == requestEles || requestEles.size() <= 0) {
                LogUtil.i("ytxu can`t find a method element of the category...");
                return;
            }

            List<DocEntity.RequestEntity> requests = new ArrayList<>(requestEles.size());
            for (Element requestEle : requestEles) {
                DocEntity.RequestEntity request = getRequest(requestEle);
                requests.add(request);
            }
            sectionEntity.setRequests(requests);
        }

        private DocEntity.RequestEntity getRequest(Element requestEle) {
            DocEntity.RequestEntity request = new DocEntity.RequestEntity(sectionEntity, requestEle);

            Element articleEle = JsoupParserUtil.getFirstEle(requestEle, CSS_QUERY_ARTICLE);
            String dataName = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_NAME);
            String methodName = CamelCaseUtils.toCamelCase(dataName);
            request.setName(methodName);

            String methodVersion = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_VERSION);
            request.setVersion(methodVersion);

            return request;
        }

    }


    public static void main(String... args) {
        new BaseParser().start();
    }
}
