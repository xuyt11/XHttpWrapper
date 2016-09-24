package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.request.header.RequestHeaderContainerModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestHeaderParser {
    private RequestModel request;

    public RequestHeaderParser(RequestModel request) {
        this.request = request;
    }

    public RequestHeaderContainerModel start() {
        Bean header = request.getElement().getHeader();
        RequestHeaderContainerModel headerContainer = new RequestHeaderContainerModel(request, header);

        List<FieldGroupModel<RequestHeaderContainerModel>> fieldGroups = new FieldGroupParser(headerContainer, header, headerContainer).start();

        setfilterTag2HeaderParam(fieldGroups);

        request.setHeaderContainer(headerContainer);
        return headerContainer;
    }

    private void setfilterTag2HeaderParam(List<FieldGroupModel<RequestHeaderContainerModel>> fieldGroups) {
        fieldGroups.forEach(fieldGroup -> fieldGroup.getFields().forEach(field -> {
            boolean isFilterParam = Property.getFilterProperty().hasThisHeaderInFilterHeaders(field.getName());
            if (isFilterParam) {
                field.setFilterTag(true);
            }
        }));
    }

}
