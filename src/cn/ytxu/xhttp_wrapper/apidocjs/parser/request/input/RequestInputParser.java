package cn.ytxu.xhttp_wrapper.apidocjs.parser.request.input;

import cn.ytxu.xhttp_wrapper.apidocjs.bean.Bean;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.FieldBean;
import cn.ytxu.xhttp_wrapper.apidocjs.parser.field.FieldGroupParser;
import cn.ytxu.xhttp_wrapper.model.field.FieldGroupModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;
import cn.ytxu.xhttp_wrapper.model.request.header.RequestHeaderModel;
import cn.ytxu.xhttp_wrapper.model.request.input.RequestInputModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RequestInputParser {
    private RequestModel request;

    public RequestInputParser(RequestModel request) {
        this.request = request;
    }

    public RequestInputModel start() {
        Bean input = request.getElement().getParameter();
        RequestInputModel requestInput = new RequestInputModel(request, input);

        List<FieldGroupModel> fieldGroups = getFieldGroups(input, requestInput);
        requestInput.setFieldGroups(fieldGroups);

        return requestInput;
    }

    private List<FieldGroupModel> getFieldGroups(Bean header, RequestInputModel requestHeader) {
        Set<Map.Entry<String, List<FieldBean>>> entrySet = header.getFields().entrySet();
        List<FieldGroupModel> fieldGroups = new ArrayList<>(entrySet.size());
        entrySet.forEach(fieldBeanMapEntry -> {
            FieldGroupModel fieldGroup = new FieldGroupParser(requestHeader, fieldBeanMapEntry).start();
            fieldGroups.add(fieldGroup);
        });
        return fieldGroups;
    }
}
