package cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.parser;

import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.RequestEntity;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.SectionEntity;
import cn.ytxu.http_wrapper.common.util.CamelCaseUtils;
import cn.ytxu.http_wrapper.common.util.JsoupParserUtil;
import cn.ytxu.http_wrapper.common.util.LogUtil;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SectionParser {
    private static final String CSS_QUERY_ARTICLE = "article";
    private static final String ATTR_DATA_NAME = "data-name";
    private static final String ATTR_DATA_VERSION = "data-version";

    private SectionEntity sectionEntity;

    public SectionParser(SectionEntity section) {
        super();
        sectionEntity = section;
    }

    public void start() {
        // 1、生成一个需要搜索到的div元素中，id属性的开始字符串
        String cssQuery = getCssQuery();
        // 2、搜索到属性值开头匹配的所有div元素
        Elements requestEles = getRequestElements(cssQuery);

        getRequests(requestEles);
    }

    private String getCssQuery() {
        String replaceName = sectionEntity.getName().replace(" ", "_");// 防止出现类似：fa deal的分类，而在检索的id时，是以fa_deal开始的；
        String startId = "api-" + replaceName + "-";
        String cssQuery = "div[id^=" + startId + "]";// 匹配属性值开头：匹配以api-Account-开头的所有div元素
        return cssQuery;
    }

    private Elements getRequestElements(String cssQuery) {
        return JsoupParserUtil.getEles(sectionEntity.getElement(), cssQuery);
    }

    private void getRequests(Elements requestEles) {
        if (null == requestEles || requestEles.size() <= 0) {
            LogUtil.i("ytxu can`t find a method element of the category...");
            return;
        }

        List<RequestEntity> requests = new ArrayList<>(requestEles.size());
        for (Element requestEle : requestEles) {
            RequestEntity request = getRequest(requestEle);
            requests.add(request);
        }
        sectionEntity.setRequests(requests);
    }

    private RequestEntity getRequest(Element requestEle) {
        RequestEntity request = new RequestEntity(sectionEntity, requestEle);

        Element articleEle = JsoupParserUtil.getFirstEle(requestEle, CSS_QUERY_ARTICLE);
        String dataName = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_NAME);
        String methodName = CamelCaseUtils.toCamelCase(dataName);
        request.setName(methodName);

        String methodVersion = JsoupParserUtil.getAttr(articleEle, ATTR_DATA_VERSION);
        request.setVersion(methodVersion);

        return request;
    }

}