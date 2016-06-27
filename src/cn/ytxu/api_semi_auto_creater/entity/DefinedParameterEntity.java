package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/6/16.
 * 已定义的参数：有描述信息，类型信息等
 */
public class DefinedParameterEntity extends BaseEntity<RequestEntity> {

    public DefinedParameterEntity(RequestEntity higherLevel, Element element) {
        super(higherLevel, element);
    }

}
