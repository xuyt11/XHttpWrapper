package cn.ytxu.xhttp_wrapper.model.request.header;

import cn.ytxu.xhttp_wrapper.model.BaseModel;
import cn.ytxu.xhttp_wrapper.model.request.RequestModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class HeaderGroupModel extends BaseModel<RequestModel, Void> {
    private final String name;
    private List<HeaderModel> headers = Collections.EMPTY_LIST;

    public HeaderGroupModel(RequestModel higherLevel, String name) {
        super(higherLevel);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<HeaderModel> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HeaderModel> headers) {
        this.headers = headers;
    }
}
