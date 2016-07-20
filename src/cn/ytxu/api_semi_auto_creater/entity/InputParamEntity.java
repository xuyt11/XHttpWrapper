package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/6/16.
 */
public class InputParamEntity extends BaseEntity<RequestEntity> {

    public InputParamEntity(RequestEntity higherLevel, Element element) {
        super(higherLevel, element);
    }

}
