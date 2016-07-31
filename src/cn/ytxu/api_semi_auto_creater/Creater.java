package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.api_semi_auto_creater.creater.request.PublicApiClassCreater;
import cn.ytxu.api_semi_auto_creater.entity.DocumentEntity;
import cn.ytxu.api_semi_auto_creater.entity.SectionEntity;

import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class Creater {
    private DocumentEntity document;

    public Creater(DocumentEntity document) {
        this.document = document;
    }

    public void start() {
        List<SectionEntity> sections = document.getSections();
        if (null == sections || sections.size() <= 0) {
            throw new RuntimeException("the sections of the api is null or empty, so end...");
        }

        // 1 create api result entity class files
//        for (CategoryEntity category : categorys) {
//            ResponseCategoryCreater creater = ResponseCategoryCreater.getInstance();
//            creater.create(category, api, doc);
//        }

        // 2 create api request interface files, and maybe used the result entity
        new PublicApiClassCreater(document).start();
//        for (CategoryEntity category : categorys) {
//            RequestInterfaceCreater creater = RequestInterfaceCreater.getInstance();
//            creater.create(category, api);
//        }

    }

}
