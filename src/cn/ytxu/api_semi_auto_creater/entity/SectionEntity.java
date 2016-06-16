package cn.ytxu.api_semi_auto_creater.entity;


import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 * API 分类的实体类
 */
public class SectionEntity extends BaseEntity<DocumentEntity> {
    private String name;// 类别的名称
    private List<RequestEntity> requests;// 该分类中所有的方法

}
