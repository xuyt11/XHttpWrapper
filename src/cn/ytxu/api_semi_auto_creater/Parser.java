package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.api_semi_auto_creater.entity.*;
import cn.ytxu.api_semi_auto_creater.model.DefinedParamModel;
import cn.ytxu.api_semi_auto_creater.parser.DefinedParamParser;
import cn.ytxu.api_semi_auto_creater.parser.RequestParser;
import cn.ytxu.util.ListUtil;

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
        // 3 parse request method
        parseRequests();
        // 4 parse method RESTful url
//        RESTfulUrlModel restfulUrl = new RESTfulUrlModel();
        // 5 parse Parameter desc table element
//        DefinedParamModel definedParameter = new DefinedParamModel();
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


    private void parseRequests() {
        for (RequestEntity request : requests) {
            RequestParser parser = new RequestParser(request);
            parser.get();
        }
    }

    private void parseDefinedParams() {
        for (RequestEntity request : requests) {
            List<DefinedParamModel> definedParams = request.getDefinedParams();

            if (ListUtil.isEmpty(definedParams)) {
                continue;
            }

            for (DefinedParamModel definedParam : definedParams) {
                DefinedParamParser parser = new DefinedParamParser(definedParam);
                parser.get();
            }
        }
    }

}
