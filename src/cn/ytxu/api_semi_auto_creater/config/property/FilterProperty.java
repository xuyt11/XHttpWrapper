package cn.ytxu.api_semi_auto_creater.config.property;

import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.util.LogUtil;

import java.util.*;

/**
 * Created by ytxu on 2016/8/28.
 */
public class FilterProperty {
    /**
     * 过滤request中header参数的key
     */
    private static final String FILTER_HEADERS_KEY = "filter.headers";
    /**
     * 过滤api中版本的key，获取到需要输出的版本号
     */
    private static final String FILTER_OUTPUT_VERSIONS_KEY = "filter.output_versions";

    private static FilterProperty instance;

    private final List<String> filterHeaders;
    private final boolean nonNeedFilterVersion;// 是否不需要过滤版本号
    private final List<String> outputVersions;// 需要输出的版本号列表

    private FilterProperty(List<String> filterHeaders, boolean nonNeedFilterVersion, List<String> outputVersions) {
        this.filterHeaders = filterHeaders;
        this.nonNeedFilterVersion = nonNeedFilterVersion;
        this.outputVersions = outputVersions;
    }

    public static FilterProperty getInstance() {
        return instance;
    }

    public static void load(Properties pps) {
        List<String> filterHeaders = getFilterHeaders(pps);
        boolean nonNeedFilterVersion;
        List<String> outputVersions;
        try {
            outputVersions = getOutputVersions(pps);
            nonNeedFilterVersion = false;
        } catch (NonNeedFilterVersionException ignore) {
            ignore.printStackTrace();
            outputVersions = Collections.EMPTY_LIST;
            nonNeedFilterVersion = true;
        }
        instance = new FilterProperty(filterHeaders, nonNeedFilterVersion, outputVersions);
    }

    private static List<String> getFilterHeaders(Properties pps) {
        String filterHeadersStr = pps.getProperty(FILTER_HEADERS_KEY, null);
        if (Objects.isNull(filterHeadersStr)) {
            LogUtil.i(FilterProperty.class, "non need filter any headers...");
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(filterHeadersStr.split(","));
    }

    private static List<String> getOutputVersions(Properties pps) {
        String outputVersionStr = pps.getProperty(FILTER_OUTPUT_VERSIONS_KEY, null);
        if (Objects.isNull(outputVersionStr)) {
            throw new NonNeedFilterVersionException("non need filter any versions...");
        }
        return Arrays.asList(outputVersionStr.split(","));
    }

    private static class NonNeedFilterVersionException extends RuntimeException {
        NonNeedFilterVersionException(String message) {
            super(message);
        }
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
        if (nonNeedFilterVersion) {
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

    private boolean isOutputVersion(VersionModel version) {
        for (String outputVersion : outputVersions) {
            if (outputVersion.equals(version.getName())) {
                return true;
            }
        }
        return false;
    }
}
