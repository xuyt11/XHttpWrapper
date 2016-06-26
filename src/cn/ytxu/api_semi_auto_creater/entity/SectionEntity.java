package cn.ytxu.api_semi_auto_creater.entity;


import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 * API 分类的实体类
 */
public class SectionEntity extends BaseEntity<DocumentEntity> {
    private String name;// 类别的名称
    private List<RequestEntity> requests;// 该分类中所有的方法

    public SectionEntity(DocumentEntity higherLevel, Element element) {
        super(higherLevel, element);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RequestEntity> getRequests() {
        return requests;
    }

    public void setRequests(List<RequestEntity> requests) {
        this.requests = requests;
    }
}
