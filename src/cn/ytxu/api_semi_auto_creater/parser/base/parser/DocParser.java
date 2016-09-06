package cn.ytxu.api_semi_auto_creater.parser.base.parser;

import cn.ytxu.api_semi_auto_creater.config.Property;
import cn.ytxu.api_semi_auto_creater.parser.base.entity.DocEntity;
import cn.ytxu.api_semi_auto_creater.parser.base.entity.SectionEntity;
import cn.ytxu.util.JsoupParserUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class DocParser {
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