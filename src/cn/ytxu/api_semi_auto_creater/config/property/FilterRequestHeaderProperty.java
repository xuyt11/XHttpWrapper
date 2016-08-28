package cn.ytxu.api_semi_auto_creater.config.property;

import cn.ytxu.util.LogUtil;

import java.util.*;

/**
 * Created by ytxu on 2016/8/28.
 * 过滤request中header参数
 */
public class FilterRequestHeaderProperty {
    /**
     * 过滤request中header参数的key
     */
    private static final String FILTER_HEADERS_KEY = "filter.headers";

    private static final FilterRequestHeaderProperty instance = new FilterRequestHeaderProperty();

    private List<String> filterHeaders = Collections.EMPTY_LIST;

    private FilterRequestHeaderProperty() {
    }

    public static void load(Properties pps) {
        String filterHeadersStr = pps.getProperty(FILTER_HEADERS_KEY, null);
        if (Objects.isNull(filterHeadersStr)) {
            LogUtil.i(FilterRequestHeaderProperty.class, "cant find filter.headers...");
            return;
        }
        instance.filterHeaders = Arrays.asList(filterHeadersStr.split(","));
    }

    public static FilterRequestHeaderProperty getInstance() {
        return instance;
    }

    public boolean hasThisHeaderInFilterHeaders(String headerName) {
        for (String filterHeader : filterHeaders) {
            if (filterHeader.equals(headerName)) {
                return true;
            }
        }
        return false;
    }

}
