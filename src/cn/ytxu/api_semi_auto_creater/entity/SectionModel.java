package cn.ytxu.api_semi_auto_creater.entity;


import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 * API 分类的实体类
 */
public class SectionModel extends BaseModel<DocumentModel> {
    private String name;// 类别的名称
    private List<RequestModel> requests;// 该分类中所有的方法

    public SectionModel(DocumentModel higherLevel, Element element) {
        super(higherLevel, element);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RequestModel> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestModel> requests) {
        this.requests = requests;
    }
}
