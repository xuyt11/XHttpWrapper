package cn.ytxu.api_semi_auto_creater.entity;

import org.jsoup.nodes.Element;

/**
 * Created by ytxu on 2016/6/16.
 */
public class RESTfulUrlEntity extends BaseEntity<RequestEntity> {
    private String url;// 方法的相对路径，起始位置必须不是/,因为人
    private boolean isRESTfulUrl = false;

    public RESTfulUrlEntity(RequestEntity higherLevel, String url) {
        super(higherLevel, null);
        this.url = url;
    }

}
