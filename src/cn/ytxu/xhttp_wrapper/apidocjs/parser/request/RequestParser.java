package cn.ytxu.xhttp_wrapper.apidocjs.parser.request;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header.RequestHeaderContainerParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.input.RequestInputContainerParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.restful_url.RESTfulUrlParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.ResponseContainerParser;
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
            new RequestHeaderContainerParser(request).start();
            new RequestInputContainerParser(request).start();
            new ResponseContainerParser(request).start();
        }));
    }
}
