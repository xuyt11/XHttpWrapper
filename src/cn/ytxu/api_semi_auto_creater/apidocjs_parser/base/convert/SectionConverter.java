package cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.convert;

import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.SectionEntity;

import java.util.ArrayList;
import java.util.List;

public class SectionConverter {
    private VersionModel versionModel;
    private List<SectionEntity> sectionEntities;

    public SectionConverter(VersionModel versionModel, List<SectionEntity> sectionEntities) {
        this.versionModel = versionModel;
        this.sectionEntities = sectionEntities;
    }

    public List<SectionModel> invoke() {
        List<SectionModel> sectionModels = new ArrayList<>(sectionEntities.size());
        for (SectionEntity sectionEntity : sectionEntities) {
            SectionModel sectionModel = new SingleSectionConverter(versionModel, sectionEntity).invoke();
            sectionModels.add(sectionModel);
        }
        return sectionModels;
    }
}