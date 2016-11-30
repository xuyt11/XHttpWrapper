package cn.ytxu.api_semi_auto_creater.apidocjs_parser;

import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.response.ResponseModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.BaseParser;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.request.RequestParser;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.ResponseParser;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.response.ResponseSErrorParser;

/**
 * Created by Administrator on 2016/9/7.
 */
public class Parser {

    public Parser() {
    }

    public DocModel start() {
        DocModel docModel = new BaseParser().start();
        parseStatusCodes(docModel);
        parseRequests(docModel);
        parseResponses(docModel);
        parseResponseSErrors(docModel);
        return docModel;
    }

    private void parseStatusCodes(DocModel docModel) {
//        List<StatusCodeCategoryModel> statusCodes = StatusCodeWrapper.getInstance().getStatusCodeGroups(docModel, false);
//        for (StatusCodeCategoryModel statusCode : statusCodes) {
//            new StatusCodeParser(statusCode).start();
//        }
    }

    private void parseRequests(DocModel docModel) {
        for (RequestModel request : docModel.getRequests(false)) {
            new RequestParser(request).start();
        }
    }

    private void parseResponses(DocModel docModel) {
        for (ResponseModel response : docModel.getResponses(false)) {
            new ResponseParser(response).start();
        }
    }

    private void parseResponseSErrors(DocModel docModel) {
        new ResponseSErrorParser(docModel, docModel.getResponses(false)).start();
    }

}
