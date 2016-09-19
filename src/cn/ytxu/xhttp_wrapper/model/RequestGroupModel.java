package cn.ytxu.xhttp_wrapper.model;

import cn.ytxu.util.FileUtil;
import cn.ytxu.xhttp_wrapper.apidocjs.bean.ApiDataBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016-9-18
 */
public class RequestGroupModel extends BaseModel<VersionModel, String> {
    private String name;
    private List<RequestModel> requests = Collections.EMPTY_LIST;

    public RequestGroupModel(VersionModel higherLevel, ApiDataBean apiData) {
        super(higherLevel, apiData.getGroup());
        this.name = element;
    }

    public String getName() {
        return name;
    }


    public void addRequest(RequestModel request) {
        if (requests == Collections.EMPTY_LIST) {
            requests = new ArrayList<>(10);
        }
        requests.add(request);
    }

    public List<RequestModel> getRequests() {
        return requests;
    }


    //*************** reflect method area ***************

    public String section_class_name() {
        String className = FileUtil.getClassFileName(name);
        return className;
    }

    public String section() {
        return FileUtil.getPackageName(name);
    }

    public String section_newchama() {
        return FileUtil.getCategoryPackageName(name);
    }

    public String section_name() {
        String className = section_class_name();
        String fieldName = className.substring(0, 1).toLowerCase() + className.substring(1);
        if ("notify".equals(fieldName)) {
            fieldName += "0";
        }
        return fieldName;
    }

    public List requests() {
        return requests;
    }
}
