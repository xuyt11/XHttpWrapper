package cn.ytxu.api_semi_auto_creater;

import cn.ytxu.api_semi_auto_creater.creater.Property;
import cn.ytxu.api_semi_auto_creater.creater.request.PublicApiClassCreater;
import cn.ytxu.api_semi_auto_creater.entity.DocumentModel;
import cn.ytxu.api_semi_auto_creater.entity.SectionModel;

import java.util.List;

/**
 * Created by ytxu on 2016/6/16.
 */
public class Creater {
    private DocumentModel document;

    public Creater(DocumentModel document) {
        this.document = document;
    }

    public void start() {
        List<SectionModel> sections = document.getSections();
        if (null == sections || sections.size() <= 0) {
            throw new RuntimeException("the sections of the api is null or empty, so end...");
        }

        // test code
        try {
            Property.getValue(null);
        } catch (NullPointerException ignore) {
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
