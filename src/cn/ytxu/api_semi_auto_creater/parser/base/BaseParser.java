package cn.ytxu.api_semi_auto_creater.parser.base;

import cn.ytxu.api_semi_auto_creater.parser.base.entity.DocEntity;
import cn.ytxu.api_semi_auto_creater.parser.base.entity.RequestEntity;
import cn.ytxu.api_semi_auto_creater.parser.base.entity.SectionEntity;
import cn.ytxu.util.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.api_semi_auto_creater.config.property.status_code.StatusCodeProperty;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.model.status_code.StatusCodeCategoryModel;
import cn.ytxu.util.CamelCaseUtils;
import cn.ytxu.util.LogUtil;
import com.sun.istack.internal.NotNull;
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

    public DocModel start() {
        DocEntity doc = new DocParser().start();
        DocModel docModel = new Converter(doc).invoke();
        return docModel;
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
            getAllSections();
            parseSections();
            return docEntity;
        }

        private void createDoc() {
            String apidocHtmlPath = Property.getApidocProperty().getHtmlPath();
            Document doc = JsoupParserUtil.getDocument(apidocHtmlPath);
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

        private void getAllSections() {
            Elements sectionEls = JsoupParserUtil.getEles(docEntity.getElement(), CSS_QUERY_GET_SECTION);

            List<SectionEntity> sections = new ArrayList<>(sectionEls.size());
            for (Element sectionEle : sectionEls) {
                SectionEntity section = getSection(sectionEle);
                sections.add(section);
            }

            docEntity.setSections(sections);
        }

        private SectionEntity getSection(Element sectionEle) {
            String name = findSectionName(sectionEle);
            return new SectionEntity(docEntity, sectionEle, name);
        }

        private String findSectionName(Element sectionEle) {
            Elements h1Els = JsoupParserUtil.getEles(sectionEle, CSS_QUERY_FIND_CATEGORY_NAME);
            return JsoupParserUtil.getText(h1Els.first());
        }

        private void parseSections() {
            for (SectionEntity section : docEntity.getSections()) {
                new SectionParser(section).start();
            }
        }

    }

    public static class SectionParser {
        private static final String CSS_QUERY_ARTICLE = "article";
        private static final String ATTR_DATA_NAME = "data-name";
        private static final String ATTR_DATA_VERSION = "data-version";

        private SectionEntity sectionEntity;

        public SectionParser(SectionEntity section) {
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

            List<RequestEntity> requests = new ArrayList<>(requestEles.size());
            for (Element requestEle : requestEles) {
                RequestEntity request = getRequest(requestEle);
                requests.add(request);
            }
            sectionEntity.setRequests(requests);
        }

        private RequestEntity getRequest(Element requestEle) {
            RequestEntity request = new RequestEntity(sectionEntity, requestEle);

            Element articleEle = JsoupParserUtil.getFirstEle(requestEle, CSS_QUERY_ARTICLE);
            String dataName = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_NAME);
            String methodName = CamelCaseUtils.toCamelCase(dataName);
            request.setName(methodName);

            String methodVersion = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_VERSION);
            request.setVersion(methodVersion);

            return request;
        }

    }


    private static class Converter {
        private DocEntity doc;

        public Converter(DocEntity doc) {
            this.doc = doc;
        }

        public DocModel invoke() {
            DocModel docModel = new DocModel(doc.getElement());

            List<VersionModel> versionModels = new VersionConverter(doc, docModel).invoke();
            docModel.setVersions(versionModels);

            return docModel;
        }
    }

    private static class VersionConverter {
        private List<String> versionStrs;
        private List<SectionEntity> sections;
        private DocModel docModel;

        public VersionConverter(DocEntity doc, DocModel docModel) {
            this.versionStrs = doc.getVersions();
            this.sections = doc.getSections();
            this.docModel = docModel;
        }

        public List<VersionModel> invoke() {
            List<VersionModel> versionModels = getVersionModels();
            setSectionsToVersions(versionModels);
            return versionModels;
        }

        @NotNull
        private List<VersionModel> getVersionModels() {
            List<VersionModel> versionModels = new ArrayList<>(versionStrs.size());
            for (String versionStr : versionStrs) {
                VersionModel versionModel = new VersionModel(docModel, versionStr);
                versionModels.add(versionModel);
            }
            return versionModels;
        }

        private void setSectionsToVersions(List<VersionModel> versionModels) {
            for (VersionModel versionModel : versionModels) {
                setSectionsToVersion(versionModel);
            }
        }

        private void setSectionsToVersion(VersionModel versionModel) {
            String versionName = versionModel.getName();
            List<SectionEntity> sectionEntities = findTargetVersionSections(versionName, sections);
            if (notNeedSetSections(sectionEntities)) {
                return;
            }

            try {
                SectionEntity statusCodeSection = findStatusCodeSection(sectionEntities);
                sectionEntities.remove(statusCodeSection);
                List<StatusCodeCategoryModel> statusCodes = new StatusCodeCategoryConverter(versionModel, statusCodeSection).invoke();
                versionModel.setStatusCodes(statusCodes);
            } catch (StatusCodeSectionNotFoundException ignore) {
            }
            List<SectionEntity> requestSections = sectionEntities;
            List<SectionModel> sectionModels = new SectionConverter(versionModel, requestSections).invoke();
            versionModel.setSections(sectionModels);
        }

        @NotNull
        private List<SectionEntity> findTargetVersionSections(String versionName, List<SectionEntity> sections) {
            List<SectionEntity> newSections = new ArrayList<>();
            for (SectionEntity section : sections) {
                List<RequestEntity> requests = findTargetVersionRequests(versionName, section);
                if (requests.size() <= 0) {// not have this version name request;
                    continue;
                }

                SectionEntity newSection = SectionEntity.copy(section);
                newSection.setRequests(requests);
                newSections.add(newSection);
            }
            return newSections;
        }

        @NotNull
        private List<RequestEntity> findTargetVersionRequests(String versionName, SectionEntity section) {
            List<RequestEntity> newRequests = new ArrayList<>();
            for (RequestEntity request : section.getRequests()) {
                if (versionName.equals(request.getVersion())) {
                    newRequests.add(request);
                }
            }
            return newRequests;
        }

        private boolean notNeedSetSections(List<SectionEntity> sectionEntities) {
            return sectionEntities.size() <= 0;
        }

        private SectionEntity findStatusCodeSection(List<SectionEntity> sections) {
            final String statusCodeSectionName = StatusCodeProperty.getInstance().getSectionName4StatusCode();
            for (SectionEntity section : sections) {
                if (!statusCodeSectionName.equals(section.getName())) {
                    continue;
                }
                return section;
            }
            throw new StatusCodeSectionNotFoundException("没有找到状态码字段");
        }

        private static final class StatusCodeSectionNotFoundException extends RuntimeException {
            public StatusCodeSectionNotFoundException(String message) {
                super(message);
            }
        }
    }

    private static class SectionConverter {
        private VersionModel versionModel;
        private List<SectionEntity> sectionEntities;

        public SectionConverter(VersionModel versionModel, List<SectionEntity> sectionEntities) {
            this.versionModel = versionModel;
            this.sectionEntities = sectionEntities;
        }

        public List<SectionModel> invoke() {
            List<SectionModel> sectionModels = new ArrayList<>(sectionEntities.size());
            for (SectionEntity sectionEntity : sectionEntities) {
                SectionModel sectionModel = new SingleSectionConverter(versionModel, sectionEntity).invoke();
                sectionModels.add(sectionModel);
            }
            return sectionModels;
        }
    }

    private static class SingleSectionConverter {
        private VersionModel versionModel;
        private SectionEntity sectionEntity;

        public SingleSectionConverter(VersionModel versionModel, SectionEntity sectionEntity) {
            this.versionModel = versionModel;
            this.sectionEntity = sectionEntity;
        }

        public SectionModel invoke() {
            SectionModel sectionModel = new SectionModel(versionModel, sectionEntity.getElement(), sectionEntity.getName());
            List<RequestModel> requestModels = new RequestConverter(sectionEntity, sectionModel).invoke();
            sectionModel.setRequests(requestModels);
            return sectionModel;
        }
    }

    private static class RequestConverter {
        private SectionEntity sectionEntity;
        private SectionModel sectionModel;

        public RequestConverter(SectionEntity sectionEntity, SectionModel sectionModel) {
            this.sectionEntity = sectionEntity;
            this.sectionModel = sectionModel;
        }

        public List<RequestModel> invoke() {
            List<RequestModel> requestModels = new ArrayList<>(sectionEntity.getRequests().size());
            for (RequestEntity requestEntity : sectionEntity.getRequests()) {
                RequestModel requestModel = new RequestModel(sectionModel, requestEntity.getElement(), requestEntity.getName(), requestEntity.getVersion());
                requestModels.add(requestModel);
            }
            return requestModels;
        }
    }

    private static class StatusCodeCategoryConverter {
        private VersionModel versionModel;
        private SectionEntity sectionEntity;

        public StatusCodeCategoryConverter(VersionModel versionModel, SectionEntity sectionEntity) {
            this.versionModel = versionModel;
            this.sectionEntity = sectionEntity;
        }

        public List<StatusCodeCategoryModel> invoke() {
            List<StatusCodeCategoryModel> sccModels = new ArrayList<>(sectionEntity.getRequests().size());
            for (RequestEntity requestEntity : sectionEntity.getRequests()) {
                StatusCodeCategoryModel sccModel = new StatusCodeCategoryModel(versionModel, requestEntity.getElement(), requestEntity.getName(), requestEntity.getVersion());
                sccModels.add(sccModel);
            }
            return sccModels;
        }

    }

}
