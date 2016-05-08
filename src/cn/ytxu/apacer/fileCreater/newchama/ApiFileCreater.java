package cn.ytxu.apacer.fileCreater.newchama;

import java.util.List;

import cn.ytxu.apacer.entity.ApiEnitity;
import cn.ytxu.apacer.entity.CategoryEntity;
import cn.ytxu.apacer.entity.DocumentEntity;
import cn.ytxu.apacer.fileCreater.newchama.requestInterface.v6.PublicApiClassCreater;
import cn.ytxu.apacer.fileCreater.newchama.requestInterface.v6.RequestInterfaceCreater;
import cn.ytxu.apacer.fileCreater.newchama.resultEntity.v6.BaseResponseEntityCreater;
import cn.ytxu.apacer.fileCreater.newchama.statusCode.StatusCodeClassCreater;
import cn.ytxu.apacer.fileCreater.newchama.resultEntity.v6.ResponseCategoryCreater;

import cn.ytxu.util.LogUtil;

/**
 * api接口生成器：RequestConfig requestInterface creater
 * @author ytxu 2016-3-21
 *
 */
public class ApiFileCreater {

	public static void createApiFile(DocumentEntity doc) {
		
		if (null == doc) {
			LogUtil.i("the api doc object can`t created, so end...");
			return;
		}


        // 1 status code
        StatusCodeClassCreater.getInstance().create(doc);

        // 2 for each categorys
        List<ApiEnitity> apis = doc.getApis();
        for (ApiEnitity api : apis) {
            forEachApis(doc, api);
        }

        // 3 create Base Response Entity class
        BaseResponseEntityCreater.getInstance().create(doc, apis);
	}

    private static void forEachApis(DocumentEntity doc, ApiEnitity api) {
        List<CategoryEntity> categorys = api.getCategorys();
        if (null == categorys || categorys.size() <= 0) {
            LogUtil.i("the categorys of the api is null or empty, so end...");
            return;
        }


        // 1 create base files
        createBaseClass4Api();
        PublicApiClassCreater.getInstance().createApiInterfaceClass(api);

        // 2 create api result entity class files
        for (CategoryEntity category : categorys) {
            ResponseCategoryCreater creater = ResponseCategoryCreater.getInstance();
            creater.create(category, api, doc);
        }

        // 3 create api request interface files, and maybe used the result entity
        for (CategoryEntity category : categorys) {
            RequestInterfaceCreater creater = RequestInterfaceCreater.getInstance();
            creater.create(category, api);
        }
    }

	/**
	 * 生成一个API的基类，其他所有的都继承该基类
	 */
	private static void createBaseClass4Api() {
		
	}


	
}

