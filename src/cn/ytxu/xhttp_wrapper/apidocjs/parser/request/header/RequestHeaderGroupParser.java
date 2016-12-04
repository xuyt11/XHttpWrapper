package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApidocjsHelper;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.api_data.ApiDataBean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.field_container.FieldContainerBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.config.ConfigWrapper;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.Objects;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestHeaderGroupParser {
    private final RequestModel request;
    private final ApiDataBean apiData;
    private final FieldContainerBean header;

    public RequestHeaderGroupParser(RequestModel request) {
        this.request = request;
        this.apiData = ApidocjsHelper.getApiData().getApiData(request);
        this.header = apiData.getHeader();
    }

    public void start() {
        if (Objects.isNull(header)) {// non header
            return;
        }

        if (header.getFields().isEmpty()) {// non header filed
            return;
        }

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
