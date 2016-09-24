package cn.ytxu.xhttp_wrapper.apidocjs.parser.request;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header.RequestHeaderParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.input.RequestInputParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.restful_url.RESTfulUrlParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.ResponseGroupParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestParser {
    private List<RequestGroupModel> requestGroups;

    public RequestParser(List<RequestGroupModel> requestGroups) {
        this.requestGroups = requestGroups;
    }

    public void start() {
        requestGroups.forEach(requestGroup -> requestGroup.getRequests().forEach(request -> {
            new RESTfulUrlParser(request).start();
            new RequestHeaderParser(request).start();
            new RequestInputParser(request).start();
            // parse success and error param
            new ResponseGroupParser(request).start();
        }));
    }
}
