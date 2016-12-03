package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.request.header.RequestHeaderContainerModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestHeaderContainerParser {
    private RequestModel request;

    public RequestHeaderContainerParser(RequestModel request) {
        this.request = request;
    }

    public RequestHeaderContainerModel start() {
        FieldContainerBean header = request.getElement().getHeader();
        RequestHeaderContainerModel headerContainer = new RequestHeaderContainerModel(request, header);

        List<FieldGroupModel<RequestHeaderContainerModel>> fieldGroups = new FieldGroupParser(headerContainer, header, headerContainer).start();

        setfilterTag2HeaderParam(fieldGroups);

        request.setHeaderContainer(headerContainer);
        return headerContainer;
    }

    private void setfilterTag2HeaderParam(List<FieldGroupModel<RequestHeaderContainerModel>> fieldGroups) {
        fieldGroups.forEach(fieldGroup -> fieldGroup.getFields().forEach(field -> {
            boolean isFilterParam = ConfigWrapper.getFilter().hasThisHeaderInFilterHeaders(field.getName());
            if (isFilterParam) {
                field.setFilterTag(true);
            }
        }));
    }

}
