package cn.ytxu.api_semi_auto_creater.parser.base.convert;

import cn.ytxu.api_semi_auto_creater.model.RequestModel;
import cn.ytxu.api_semi_auto_creater.model.base.SectionModel;
import cn.ytxu.api_semi_auto_creater.parser.base.entity.RequestEntity;
import cn.ytxu.api_semi_auto_creater.parser.base.entity.SectionEntity;

import java.util.ArrayList;
import java.util.List;

public class RequestConverter {
    private SectionEntity sectionEntity;
    private SectionModel sectionModel;

    public RequestConverter(SectionEntity sectionEntity, SectionModel sectionModel) {
        this.sectionEntity = sectionEntity;
        this.sectionModel = sectionModel;
    }

    public List<RequestModel> invoke() {
        List<RequestModel> requestModels = new ArrayList<>(sectionEntity.getRequests().size());
        for (RequestEntity requestEntity : sectionEntity.getRequests()) {
            RequestModel requestModel = new RequestModel(sectionModel, requestEntity.getElement(), requestEntity.getName(), requestEntity.getVersion());
            requestModels.add(requestModel);
        }
        return requestModels;
    }
}