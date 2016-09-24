package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.request.header.RequestHeadersModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestHeaderParser {
    private RequestModel request;

    public RequestHeaderParser(RequestModel request) {
        this.request = request;
    }

    public RequestHeadersModel start() {
        Bean header = request.getElement().getHeader();
        RequestHeadersModel requestHeaders = new RequestHeadersModel(request, header);

        List<FieldGroupModel<RequestHeadersModel>> fieldGroups = new FieldGroupParser(requestHeaders, header, requestHeaders).start();

        setfilterTag2HeaderParam(fieldGroups);

        request.setHeaders(requestHeaders);
        return requestHeaders;
    }

    private void setfilterTag2HeaderParam(List<FieldGroupModel<RequestHeadersModel>> fieldGroups) {
        fieldGroups.forEach(fieldGroup -> fieldGroup.getFields().forEach(field -> {
            boolean isFilterParam = Property.getFilterProperty().hasThisHeaderInFilterHeaders(field.getName());
            if (isFilterParam) {
                field.setFilterTag(true);
            }
        }));
    }

}
