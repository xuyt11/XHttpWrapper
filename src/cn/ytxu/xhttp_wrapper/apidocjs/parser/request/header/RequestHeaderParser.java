package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.request.header.RequestHeaderModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestHeaderParser {
    private RequestModel request;

    public RequestHeaderParser(RequestModel request) {
        this.request = request;
    }

    public RequestHeaderModel start() {
        Bean header = request.getElement().getHeader();
        RequestHeaderModel requestHeader = new RequestHeaderModel(request, header);

        List<FieldGroupModel<RequestHeaderModel>> fieldGroups = new FieldGroupParser(requestHeader, header, requestHeader).start();

        setfilterTag2HeaderParam(fieldGroups);

        return requestHeader;
    }

    private void setfilterTag2HeaderParam(List<FieldGroupModel<RequestHeaderModel>> fieldGroups) {
        fieldGroups.forEach(fieldGroup -> fieldGroup.getFields().forEach(field -> {
            boolean isFilterParam = Property.getFilterProperty().hasThisHeaderInFilterHeaders(field.getName());
            if (isFilterParam) {
                field.setFilterTag(true);
            }
        }));
    }

}
