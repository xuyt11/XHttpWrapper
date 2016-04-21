package cn.ytxu.apacer.templateParser;

import cn.ytxu.util.LogUtil;
import cn.ytxu.apacer.entity.ApiEnitity;
import cn.ytxu.apacer.entity.CategoryEntity;

import java.util.List;

/**
 * 模板代码生成器
 * 2016-03-29
 */
public class TemplateInterfaceCreater {

    public static void createApiFile(ApiEnitity api) {

        if (null == api) {
            LogUtil.i("the api object can`t created, so end...");
            return;
        }

        List<CategoryEntity> categorys = api.getCategorys();
        if (null == categorys || categorys.size() <= 0) {
            LogUtil.i("the categorys of the api is null or empty, so end...");
            return;
        }

        for (CategoryEntity category : categorys) {
            TemplateClassCreater.create(category);
        }

//        TemplatePublicApiClassCreater.createApiInterfaceClass(api);
    }

}
