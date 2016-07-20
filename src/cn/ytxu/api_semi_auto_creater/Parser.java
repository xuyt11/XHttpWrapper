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
    DocumentModel docment;
    private List<SectionModel> sections;
    private List<RequestModel> requests;

    public Parser() {

    }

    public DocumentModel start() {
        // 1 get docment from html content
        parseDocumentAndGetSections();
        // 2 parse section
        parseSectionsAndGetRequests();
        // 3 parse request method
        parseRequests();
        // 4 parse method RESTful url
//        RESTfulUrlModel restfulUrl = new RESTfulUrlModel();
        // 5 parse Parameter desc table element
//        DefinedParameterModel definedParameter = new DefinedParameterModel();
        // 6 parse header and input field
//        InputParamModel header = new InputParamModel();
//        InputParamModel inputParam = new InputParamModel();
        // 7 parse response
//        ResponseModel response = new ResponseModel();
        // 8 parse response content --> output param --> response entity
//        OutputParamModel outputParam = new OutputParamModel();

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
        for (SectionModel section : sections) {
            SectionParser parser = new SectionParser(section);
            List<RequestModel> datas = parser.get();
            requests.addAll(datas);
        }
    }

    private void parseRequests() {
        for (RequestModel request : requests) {
            RequestParser parser = new RequestParser(request);
            parser.get();
        }
    }

    private void parseDefinedParams() {
        for (RequestModel request : requests) {
            List<DefinedParameterModel> definedParams = request.getDefinedParams();

            if (ListUtil.isEmpty(definedParams)) {
                continue;
            }

            for (DefinedParameterModel definedParam : definedParams) {
                DefinedParamParser parser = new DefinedParamParser(definedParam);
                parser.get();
            }
        }
    }

}
