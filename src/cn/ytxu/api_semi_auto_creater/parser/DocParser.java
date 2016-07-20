package cn.ytxu.api_semi_auto_creater.parser;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.apacer.exception.BlankTextException;
import cn.ytxu.apacer.exception.TargetElementsNotFoundException;
import cn.ytxu.api_semi_auto_creater.entity.DocumentEntity;
import cn.ytxu.api_semi_auto_creater.entity.SectionEntity;
import cn.ytxu.api_semi_auto_creater.entity.StatusCodeEntity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/6/26.
 */
public class DocParser {
    private static final String CONTENT = "div.container-fluid > div.row-fluid > div#content";
    private static final String CSS_QUERY_GET_VERSION_CODE = CONTENT + " > div#project > div.pull-right > div.btn-group > ul#versions > li.version > a";
    private static final String CSS_QUERY_GET_SECTION = CONTENT + " > div#sections > section";

    private final DocumentEntity documentEntity;
    private Elements sectionEls;

    public DocParser() {
        super();

        // 1 get html document
        Document doc = JsoupParserUtil.getDocument(Config.getApiDocHtmlPath());
        documentEntity = new DocumentEntity(null, doc);
    }

    public DocumentEntity get() {
        // 2 get version elements then parse it
        getVersionCodes();
        // 3 get section elements
        getSectionElements();
        // 4 get status code elements then parse status code
        getStatusCodes();

        setSections();

        return documentEntity;
    }

    private void getVersionCodes() {
        Elements versionEls = JsoupParserUtil.getEles(documentEntity.getElement(), CSS_QUERY_GET_VERSION_CODE);
        if (null == versionEls || versionEls.size() <= 0) {
            throw new RuntimeException("ytxu error status: have not version code element...");
        }

        List<String> versions = new ArrayList<>(versionEls.size());
        for (int i = 0, size = versionEls.size(); i < size; i++) {
            versions.add(JsoupParserUtil.getText(versionEls.get(i)));
        }

        documentEntity.setVersions(versions);
    }

    private void getSectionElements() {
        sectionEls = JsoupParserUtil.getEles(documentEntity.getElement(), CSS_QUERY_GET_SECTION);
        if (null == sectionEls || sectionEls.size() <= 0) {
            throw new RuntimeException("ytxu error status: can not select the section elements, so stop running...");
        }
    }

    private void getStatusCodes() {
        Element statusCodeEle = getStatusCodeElement();
        sectionEls.remove(statusCodeEle);// remove status code section

        List<StatusCodeEntity> statusCodes = new StatusCodeParser().parseStatusCodes(statusCodeEle);
        documentEntity.setStatusCodes(statusCodes);
    }

    private Element getStatusCodeElement() {
        for (Element sectionEle : sectionEls) {
            String name;
            try {
                name = SectionParser.findSectionName(sectionEle);
            } catch (TargetElementsNotFoundException ignore) {
                continue;
            } catch (BlankTextException ignore) {
                continue;
            }
            if (!Config.statusCode.StatusCode.equals(name)) {
                continue;
            }
            return sectionEle;
        }
        throw new RuntimeException("ytxu error status: can not find status code section element...");
    }

    private void setSections() {
        List<SectionEntity> sections = new ArrayList<>(sectionEls.size());

        for (Element sectionEle : sectionEls) {
            SectionEntity section = new SectionEntity(documentEntity, sectionEle);
            sections.add(section);
        }
        documentEntity.setSections(sections);
    }


}
