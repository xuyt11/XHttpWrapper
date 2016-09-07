package cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.convert;

import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.model.status_code.StatusCodeCategoryModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.RequestEntity;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.SectionEntity;

import java.util.ArrayList;
import java.util.List;

public class StatusCodeCategoryConverter {
    private VersionModel versionModel;
    private SectionEntity sectionEntity;

    public StatusCodeCategoryConverter(VersionModel versionModel, SectionEntity sectionEntity) {
        this.versionModel = versionModel;
        this.sectionEntity = sectionEntity;
    }

    public List<StatusCodeCategoryModel> invoke() {
        List<StatusCodeCategoryModel> sccModels = new ArrayList<>(sectionEntity.getRequests().size());
        for (RequestEntity requestEntity : sectionEntity.getRequests()) {
            StatusCodeCategoryModel sccModel = new StatusCodeCategoryModel(versionModel, requestEntity.getElement(), requestEntity.getName(), requestEntity.getVersion());
            sccModels.add(sccModel);
        }
        return sccModels;
    }

}