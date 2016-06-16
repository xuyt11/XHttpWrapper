package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.api_semi_auto_creater.entity.*;
import org.jsoup.nodes.Document;

/**
 * Created by ytxu on 2016/6/16.
 */
public class Parser {

    public Parser() {

    }

    public void start() {
        // 1 get docment from html content
        DocumentEntity docment = getDocument();

        // 2 parse section
        SectionEntity section = new SectionEntity();
        // 3 parse request method
        RequestEntity request = new RequestEntity();
        // 4 parse method RESTful url
        RESTfulUrlEntity restfulUrl = new RESTfulUrlEntity();
        // 5 parse Parameter desc table element
        DefinedParameterEntity definedParameter = new DefinedParameterEntity();
        // 6 parse header and input field
        InputParamEntity header = new InputParamEntity();
        InputParamEntity inputParam = new InputParamEntity();
        // 7 parse response
        ResponseEntity response = new ResponseEntity();
        // 8 parse response content --> output param --> response entity
        OutputParamEntity outputParam = new OutputParamEntity();


    }

    private DocumentEntity getDocument() {
        // 1 get html document
        // 2 get version elements then parse it
        // 3 get section elements
        // 4 get status code elements then parse status code
        DocumentEntity document = new DocumentEntity();
        Document doc = JsoupParserUtil.getDocument(Config.getApiDocHtmlPath());
        document.setElement(doc);
        return document;
    }

}
