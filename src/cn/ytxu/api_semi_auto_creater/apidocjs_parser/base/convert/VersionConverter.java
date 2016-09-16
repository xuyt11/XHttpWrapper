package cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.convert;

import cn.ytxu.xhttp_wrapper.config.property.status_code.StatusCodeProperty;
import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.model.status_code.StatusCodeCategoryModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.DocEntity;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.RequestEntity;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.SectionEntity;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VersionConverter {
    private List<String> versionStrs;
    private List<SectionEntity> sections;
    private DocModel docModel;

    public VersionConverter(DocEntity doc, DocModel docModel) {
        this.versionStrs = doc.getVersions();
        this.sections = doc.getSections();
        this.docModel = docModel;
    }

    public List<VersionModel> invoke() {
        List<VersionModel> versionModels = getVersionModels();
        setSectionsToVersions(versionModels);
        return versionModels;
    }

    @NotNull
    private List<VersionModel> getVersionModels() {
        List<VersionModel> versionModels = new ArrayList<>(versionStrs.size());
        for (String versionStr : versionStrs) {
            VersionModel versionModel = new VersionModel(docModel, versionStr);
            versionModels.add(versionModel);
        }
        return versionModels;
    }

    private void setSectionsToVersions(List<VersionModel> versionModels) {
        for (VersionModel versionModel : versionModels) {
            setSectionsToVersion(versionModel);
        }
    }

    private void setSectionsToVersion(VersionModel versionModel) {
        String versionName = versionModel.getName();
        List<SectionEntity> sectionEntities = findTargetVersionSections(versionName, sections);
        if (notNeedSetSections(sectionEntities)) {
            return;
        }

        try {
            SectionEntity statusCodeSection = findStatusCodeSection(sectionEntities);
            sectionEntities.remove(statusCodeSection);
            List<StatusCodeCategoryModel> statusCodes = new StatusCodeCategoryConverter(versionModel, statusCodeSection).invoke();
            versionModel.setStatusCodes(statusCodes);
        } catch (StatusCodeSectionNotFoundException ignore) {
        }
        List<SectionEntity> requestSections = sectionEntities;
        List<SectionModel> sectionModels = new SectionConverter(versionModel, requestSections).invoke();
        versionModel.setSections(sectionModels);
    }

    @NotNull
    private List<SectionEntity> findTargetVersionSections(String versionName, List<SectionEntity> sections) {
        List<SectionEntity> newSections = new ArrayList<>();
        for (SectionEntity section : sections) {
            List<RequestEntity> requests = findTargetVersionRequests(versionName, section);
            if (requests.size() <= 0) {// not have this version name request;
                continue;
            }

            SectionEntity newSection = SectionEntity.copy(section);
            newSection.setRequests(requests);
            newSections.add(newSection);
        }
        return newSections;
    }

    @NotNull
    private List<RequestEntity> findTargetVersionRequests(String versionName, SectionEntity section) {
        List<RequestEntity> newRequests = new ArrayList<>();
        for (RequestEntity request : section.getRequests()) {
            if (versionName.equals(request.getVersion())) {
                newRequests.add(request);
            }
        }
        return newRequests;
    }

    private boolean notNeedSetSections(List<SectionEntity> sectionEntities) {
        return sectionEntities.size() <= 0;
    }

    private SectionEntity findStatusCodeSection(List<SectionEntity> sections) {
        final String statusCodeSectionName = StatusCodeProperty.getInstance().getSectionName4StatusCode();
        for (SectionEntity section : sections) {
            if (!statusCodeSectionName.equals(section.getName())) {
                continue;
            }
            return section;
        }
        throw new StatusCodeSectionNotFoundException("没有找到状态码字段");
    }

    private static final class StatusCodeSectionNotFoundException extends RuntimeException {
        public StatusCodeSectionNotFoundException(String message) {
            super(message);
        }
    }
}