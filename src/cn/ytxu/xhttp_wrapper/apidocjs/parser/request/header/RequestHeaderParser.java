package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.header;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
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

        List<FieldGroupModel> fieldGroups = getFieldGroups(header, requestHeader);
        requestHeader.setFieldGroups(fieldGroups);

        return requestHeader;
    }

    private List<FieldGroupModel> getFieldGroups(Bean header, RequestHeaderModel requestHeader) {
        Set<Map.Entry<String, List<FieldBean>>> entrySet = header.getFields().entrySet();
        List<FieldGroupModel> fieldGroups = new ArrayList<>(entrySet.size());
        entrySet.forEach(fieldBeanMapEntry -> {
            FieldGroupModel fieldGroup = new FieldGroupParser(requestHeader, fieldBeanMapEntry).start();
            fieldGroups.add(fieldGroup);
        });
        return fieldGroups;
    }
}
