package cn.ytxu.api_semi_auto_creater.parser;

import cn.ytxu.apacer.dataParser.jsoupUtil.JsoupParserUtil;
import cn.ytxu.apacer.exception.BlankTextException;
import cn.ytxu.apacer.exception.TargetElementsNotFoundException;
import cn.ytxu.api_semi_auto_creater.entity.RequestEntity;
import cn.ytxu.api_semi_auto_creater.entity.SectionEntity;
import cn.ytxu.util.LogUtil;
import cn.ytxu.util.TextUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类的解析类
 * Created by ytxu on 2016/6/26.
 */
public class SectionParser {
    private static final String CSS_QUERY_FIND_CATEGORY_NAME = "h1";

    private SectionEntity sectionEntity;
    private Element sectionEle;

    private String name;
    private String cssQuery;
    private Elements requestEles;
    private List<RequestEntity> requests;

    public SectionParser(SectionEntity section) {
        super();
        sectionEntity = section;
        this.sectionEle = section.getElement();
        requests = new ArrayList<>();
    }

    public List<RequestEntity> get() {
        getSectionName();

        // 1、生成一个需要搜索到的div元素中，id属性的开始字符串
        getCssQuery();
        // 2、搜索到属性值开头匹配的所有div元素
        getRequestElements();
        getRequests();

        return requests;
    }

    private void getSectionName() {
        try {
            name = findSectionName(sectionEle);
        } catch (TargetElementsNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (BlankTextException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void getCssQuery() {
        String replaceName = name.replace(" ", "_");// 防止出现类似：fa deal的分类，而在检索的id时，是以fa_deal开始的；
        String startId = "api-" + replaceName + "-";
        cssQuery = "div[id^=" + startId + "]";// 匹配属性值开头：匹配以api-Account-开头的所有div元素
    }

    private void getRequestElements() {
        requestEles = JsoupParserUtil.getEles(sectionEle, cssQuery);
    }

    private void getRequests() {
        if (null == requestEles || requestEles.size() <= 0) {
            LogUtil.i("ytxu can`t find a method element of the category...");
            return;
        }

        for (Element requestEle : requestEles) {
            RequestEntity request = new RequestEntity(sectionEntity, requestEle);
            requests.add(request);
        }
    }


    public static String findSectionName(Element sectionEle) throws TargetElementsNotFoundException, BlankTextException {
        Elements h1Els = JsoupParserUtil.getEles(sectionEle, CSS_QUERY_FIND_CATEGORY_NAME);
        if (null == h1Els || h1Els.size() <= 0) {
            throw new TargetElementsNotFoundException(" can`t find the h1 element");
        }

        String name = JsoupParserUtil.getText(h1Els.first());
        if (TextUtil.isBlank(name)) {
            throw new BlankTextException(" this name is blank for category");
        }
        return name;
    }
}
