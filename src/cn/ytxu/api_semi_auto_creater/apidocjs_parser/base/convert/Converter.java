package cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.convert;

import cn.ytxu.api_semi_auto_creater.model.base.DocModel;
import cn.ytxu.api_semi_auto_creater.model.base.VersionModel;
import cn.ytxu.api_semi_auto_creater.apidocjs_parser.base.entity.DocEntity;

import java.util.List;

public class Converter {
    private DocEntity doc;

    public Converter(DocEntity doc) {
        this.doc = doc;
    }

    public DocModel invoke() {
        DocModel docModel = new DocModel(doc.getElement());

        List<VersionModel> versionModels = new VersionConverter(doc, docModel).invoke();
        docModel.setVersions(versionModels);

        return docModel;
    }
}