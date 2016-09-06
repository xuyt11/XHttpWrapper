package cn.ytxu.api_semi_auto_creater.parser.base.convert;

import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.parser.base.entity.SectionEntity;

import java.util.List;

public class SingleSectionConverter {
    private VersionModel versionModel;
    private SectionEntity sectionEntity;

    public SingleSectionConverter(VersionModel versionModel, SectionEntity sectionEntity) {
        this.versionModel = versionModel;
        this.sectionEntity = sectionEntity;
    }

    public SectionModel invoke() {
        SectionModel sectionModel = new SectionModel(versionModel, sectionEntity.getElement(), sectionEntity.getName());
        List<RequestModel> requestModels = new RequestConverter(sectionEntity, sectionModel).invoke();
        sectionModel.setRequests(requestModels);
        return sectionModel;
    }
}