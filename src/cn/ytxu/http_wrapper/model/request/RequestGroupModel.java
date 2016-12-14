package cn.ytxu.http_wrapper.model.request;

import cn.ytxu.http_wrapper.common.util.FileUtil;
import cn.ytxu.http_wrapper.model.BaseModel;
import cn.ytxu.http_wrapper.model.version.VersionModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ytxu on 2016-9-18
 */
public class RequestGroupModel extends BaseModel<VersionModel> implements Comparable<RequestGroupModel> {
    private String name;
    private List<RequestModel> requests = Collections.EMPTY_LIST;

    public RequestGroupModel(VersionModel higherLevel, String groupName) {
        super(higherLevel);
        this.name = groupName;
        higherLevel.addRequestGroup(this);
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

    @Override
    public int compareTo(RequestGroupModel o) {
        return this.name.compareToIgnoreCase(o.name);
    }


    //*************** reflect method area ***************

    public String request_group_class_name() {
        String className = FileUtil.getClassFileName(getName());
        return className;
    }

    public String request_group() {
        return FileUtil.getPackageName(getName());
    }

    public String request_group_newchama() {
        return FileUtil.getCategoryPackageName(getName());
    }

    public String request_group_name() {
        String className = request_group_class_name();
        String fieldName = className.substring(0, 1).toLowerCase() + className.substring(1);
        if ("notify".equals(fieldName)) {
            fieldName += "0";
        }
        return fieldName;
    }

    public List requests() {
        return requests;
    }

    public boolean needImportOptionalRequestParamPackage() {
        for (RequestModel request : requests) {
            if (request.needGenerateOptionalRequestMethod()) {
                return true;
            }
        }
        return false;
    }

}
