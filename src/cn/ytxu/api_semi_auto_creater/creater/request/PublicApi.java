package cn.ytxu.api_semi_auto_creater.creater.request;

import cn.ytxu.api_semi_auto_creater.entity.RequestModel;
import cn.ytxu.api_semi_auto_creater.entity.SectionModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytxu on 2016/7/10.
 */
class PublicApi {
    private SectionModel section;
    private List<String> versions;

    private String sectionName;
    private Map<String, Boolean> versionMap;// k:versionCode, v:在该版本中是否存在该分类

    public PublicApi(SectionModel section, List<String> versions) {
        this.section = section;
        this.versions = versions;

        setSectionName();
        setVersionMap();
        setupRelationForVersionAndSection();
    }

    private void setSectionName() {
        sectionName = section.getName();
    }

    private void setVersionMap() {
        versionMap = new HashMap<>(versions.size());
        for (String version : versions) {
            versionMap.put(version, false);
        }
    }

    private void setupRelationForVersionAndSection() {
        List<RequestModel> requests = section.getRequests();
        for (RequestModel req : requests) {
            setupIfNeed(req);
        }
    }

    private void setupIfNeed(RequestModel req) {
        String version = req.getVersionCode();
        if (!versions.contains(version)) {
            throw new RuntimeException("this request version code is not in document version element, and it is " + version);
        }

        if (existInVersion(version)) {
            return;
        }
        versionMap.put(version, true);
    }

    public String getSectionName() {
        return sectionName;
    }

    public String getSectionMethodName() {
        if ("notify".equals(sectionName)) {
            return sectionName + "0";
        }
        return sectionName;
    }

    public boolean existInVersion(String version) {
        return versionMap.get(version);
    }

}