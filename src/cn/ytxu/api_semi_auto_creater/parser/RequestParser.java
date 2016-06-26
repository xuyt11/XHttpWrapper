package cn.ytxu.api_semi_auto_creater.parser;

import cn.ytxu.api_semi_auto_creater.entity.BaseEntity;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类的解析类
 * Created by ytxu on 2016/6/26.
 */
public class RequestParser<T> {

    private BaseEntity baseEntity;
    private Element baseEle;
    private Elements returnsEles;
    private List<T> returns;

    public RequestParser(BaseEntity baseEntity) {
        super();
        this.baseEntity = baseEntity;
        this.baseEle = baseEntity.getElement();
        returns = new ArrayList<>();
    }

    public List<T> get() {

        return returns;
    }

}
