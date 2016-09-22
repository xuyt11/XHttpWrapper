package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.config.Property;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.field.FieldModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.request.header.RequestHeaderModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

        List<FieldGroupModel<RequestHeaderModel>> fieldGroups = getFieldGroups(header, requestHeader);
        requestHeader.setFieldGroups(fieldGroups);

        setfilterTag2HeaderParam(fieldGroups);

        return requestHeader;
    }

    private List<FieldGroupModel<RequestHeaderModel>> getFieldGroups(Bean header, RequestHeaderModel requestHeader) {
        Set<Map.Entry<String, List<FieldBean>>> entrySet = header.getFields().entrySet();
        List<FieldGroupModel<RequestHeaderModel>> fieldGroups = new ArrayList<>(entrySet.size());
        entrySet.forEach(fieldBeanMapEntry -> {
            FieldGroupModel fieldGroup = new FieldGroupParser(requestHeader, fieldBeanMapEntry).start();
            fieldGroups.add(fieldGroup);
        });
        return fieldGroups;
    }

    private void setfilterTag2HeaderParam(List<FieldGroupModel<RequestHeaderModel>> fieldGroups) {
        fieldGroups.forEach(fieldGroup -> fieldGroup.getFields().forEach(field -> {
            boolean isFilterParam = Property.getFilterProperty().hasThisHeaderInFilterHeaders(field.getField());
            if (isFilterParam) {
                field.setFilterTag(true);
            }
        }));
    }
}
