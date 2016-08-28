package cn.ytxu.api_semi_auto_creater.config.property;

import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.util.LogUtil;

import java.util.*;

/**
 * Created by ytxu on 2016/8/28.
 */
public class FilterRequestHeaderProperty {
    /**
     * 过滤request中header参数的key
     */
    private static final String FILTER_HEADERS_KEY = "filter.headers";
    /**
     * 过滤api中版本的key，获取到需要输出的版本号
     */
    private static final String FILTER_OUTPUT_VERSIONS_KEY = "filter.output_versions";

    private static final FilterRequestHeaderProperty instance = new FilterRequestHeaderProperty();

    private List<String> filterHeaders = Collections.EMPTY_LIST;
    private List<String> outputVersions = Collections.EMPTY_LIST;// 需要输出的版本号列表

    private FilterRequestHeaderProperty() {
    }

    public static void load(Properties pps) {
        setFilterHeaders(pps);
        setOutputVersions(pps);
    }

    private static void setFilterHeaders(Properties pps) {
        String filterHeadersStr = pps.getProperty(FILTER_HEADERS_KEY, null);
        if (Objects.isNull(filterHeadersStr)) {
            LogUtil.i(FilterRequestHeaderProperty.class, "non need filter any headers...");
            return;
        }
        instance.filterHeaders = Arrays.asList(filterHeadersStr.split(","));
    }

    private static void setOutputVersions(Properties pps) {
        String outputVersionStr = pps.getProperty(FILTER_OUTPUT_VERSIONS_KEY, null);
        if (Objects.isNull(outputVersionStr)) {
            LogUtil.i(FilterRequestHeaderProperty.class, "non need filter any versions...");
            return;
        }
        instance.outputVersions = Arrays.asList(outputVersionStr.split(","));
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

    public List<VersionModel> getVersionsAfterFilter(DocModel docModel) {
        if (nonNeedFilterVersion()) {
            return docModel.getVersions();
        }

        List<VersionModel> versions = new ArrayList<>();
        for (VersionModel version : docModel.getVersions()) {
            if (isOutputVersion(version)) {
                versions.add(version);
            }
        }
        return versions;
    }

    /**
     * 是否需要过滤版本号
     */
    private boolean nonNeedFilterVersion() {
        return outputVersions == Collections.EMPTY_LIST;
    }

    private boolean isOutputVersion(VersionModel version) {
        for (String outputVersion : outputVersions) {
            if (outputVersion.equals(version.getName())) {
                return true;
            }
        }
        return false;
    }
}
