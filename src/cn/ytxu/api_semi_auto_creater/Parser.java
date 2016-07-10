package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.api_semi_auto_creater.entity.*;
import cn.ytxu.api_semi_auto_creater.parser.DefinedParamParser;
import cn.ytxu.api_semi_auto_creater.parser.DocParser;
import cn.ytxu.api_semi_auto_creater.parser.RequestParser;
import cn.ytxu.api_semi_auto_creater.parser.SectionParser;
import cn.ytxu.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class Parser {
    DocumentEntity docment;
    private List<SectionEntity> sections;
    private List<RequestEntity> requests;

    public Parser() {

    }

    public DocumentEntity start() {
        // 1 get docment from html content
        parseDocumentAndGetSections();
        // 2 parse section
        parseSectionsAndGetRequests();
        // 3 parse request method
        parseRequests();
        // 4 parse method RESTful url
//        RESTfulUrlEntity restfulUrl = new RESTfulUrlEntity();
        // 5 parse Parameter desc table element
//        DefinedParameterEntity definedParameter = new DefinedParameterEntity();
        // 6 parse header and input field
//        InputParamEntity header = new InputParamEntity();
//        InputParamEntity inputParam = new InputParamEntity();
        // 7 parse response
//        ResponseEntity response = new ResponseEntity();
        // 8 parse response content --> output param --> response entity
//        OutputParamEntity outputParam = new OutputParamEntity();

        // 9 parse defindParamterEntity

        // 10 use defindParamterEntity to filter header,input, output

        return docment;
    }

    private void parseDocumentAndGetSections() {
        docment = new DocParser().get();
        sections = docment.getSections();
    }

    private void parseSectionsAndGetRequests() {
        requests = new ArrayList<>();
        for (SectionEntity section : sections) {
            SectionParser parser = new SectionParser(section);
            List<RequestEntity> datas = parser.get();
            requests.addAll(datas);
        }
    }

    private void parseRequests() {
        for (RequestEntity request : requests) {
            RequestParser parser = new RequestParser(request);
            parser.get();
        }
    }

    private void parseDefinedParams() {
        for (RequestEntity request : requests) {
            List<DefinedParameterEntity> definedParams = request.getDefinedParams();

            if (ListUtil.isEmpty(definedParams)) {
                continue;
            }

            for (DefinedParameterEntity definedParam : definedParams) {
                DefinedParamParser parser = new DefinedParamParser(definedParam);
                parser.get();
            }
        }
    }

}
