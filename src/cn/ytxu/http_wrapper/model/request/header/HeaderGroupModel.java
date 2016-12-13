package cn.ytxu.http_wrapper.model.request.header;

import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.request.RequestModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2016/9/24.
 */
public class HeaderGroupModel extends BaseModel<RequestModel> implements Comparable<HeaderGroupModel> {
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

    public void addHeader(HeaderModel header) {
        if (headers == Collections.EMPTY_LIST) {
            headers = new ArrayList<>(10);
        }
        headers.add(header);
    }

    @Override
    public int compareTo(HeaderGroupModel o) {
        return this.name.compareToIgnoreCase(o.name);
    }
}
