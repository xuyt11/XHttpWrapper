package cn.ytxu.xhttp_wrapper.apidocjs.parser.request;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header.RequestHeaderParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.input.RequestInputParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.restful_url.RESTfulUrlParser;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.response.ResponseParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.header.RequestHeaderModel;
import cn.ytxu.xhttp_wrapper.model.request.input.RequestInputModel;
import cn.ytxu.xhttp_wrapper.model.request.restful_url.RESTfulUrlModel;

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
            RESTfulUrlModel restfulUrl = new RESTfulUrlParser(request).start();
            request.setRestfulUrl(restfulUrl);

            RequestHeaderModel header = new RequestHeaderParser(request).start();
            request.setHeader(header);

            RequestInputModel input = new RequestInputParser(request).start();
            request.setInput(input);

            // parse success and error param
            new ResponseParser(request).start();
        }));
    }
}
