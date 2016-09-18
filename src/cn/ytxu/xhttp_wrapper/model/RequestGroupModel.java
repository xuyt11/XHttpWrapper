package cn.ytxu.xhttp_wrapper.model;

import cn.ytxu.util.FileUtil;

import java.util.List;

/**
 * Created by ytxu on 2016-9-18
 */
public class RequestGroupModel extends BaseModel<VersionModel, String> {
    private String name;
//    private List<RequestModel> requests;

    public RequestGroupModel(VersionModel higherLevel, String element) {
        super(higherLevel, element);
        this.name = element;
    }

    public String getName() {
        return name;
    }

//    public List<RequestModel> getRequests() {
//        return requests;
//    }
//
//    public void setRequests(List<RequestModel> requests) {
//        this.requests = requests;
//    }


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

//    public List requests() {
//        return requests;
//    }

}
