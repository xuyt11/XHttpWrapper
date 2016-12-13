package cn.ytxu.http_wrapper.apidocjs.parser.request;

import cn.ytxu.http_wrapper.apidocjs.parser.request.header.RequestHeaderGroupParser;
import cn.ytxu.http_wrapper.apidocjs.parser.request.input.RequestInputGroupParser;
import cn.ytxu.http_wrapper.apidocjs.parser.request.restful_url.RESTfulUrlParser;
import cn.ytxu.http_wrapper.apidocjs.parser.response.ResponseContainerParser;
import cn.ytxu.http_wrapper.model.request.RequestGroupModel;

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
            new RequestHeaderGroupParser(request).start();
            new RequestInputGroupParser(request).start();
            new ResponseContainerParser(request).start();
        }));
    }
}
