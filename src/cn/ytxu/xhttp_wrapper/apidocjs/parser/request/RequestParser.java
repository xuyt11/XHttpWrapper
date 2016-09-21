package cn.ytxu.xhttp_wrapper.apidocjs.parser.request;

import cn.ytxu.xhttp_wrapper.apidocjs.parser.request.restful_url.RESTfulUrlParser;
import cn.ytxu.xhttp_wrapper.model.request.RequestGroupModel;
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
            // parse header param
            // parse input param
            // parse success param
            // parse error param
        }));
    }
}
