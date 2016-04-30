package cn.ytxu.apacer;

import cn.ytxu.apacer.dataParser.apidocjsParser.v7.ApiDocHtmlParser;
import cn.ytxu.apacer.entity.ApiEnitity;
import cn.ytxu.apacer.entity.DocumentEntity;
import cn.ytxu.apacer.fileCreater.newchama.ApiFileCreater;
import cn.ytxu.util.LogUtil;

import java.util.List;

public class EngineV7 {
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		DocumentEntity docEntity = ApiDocHtmlParser.parser();

		ApiFileCreater.createApiFile(docEntity);

		// it is a failed code, because i think too simple...
        //foreach, if else, if a field is null, and i don`t think things in this time
//		TemplateInterfaceCreater.createApiFile(api);

		long end = System.currentTimeMillis();

		LogUtil.w("duration time is " + (end - start));

	}

    /**
     * 根据API中所有的版本号,将不同版本的请求给分到不同的APIEntity中
     * @return
     */
    private static List<ApiEnitity> parserByVersion(ApiEnitity api) {




        return null;
    }
	

}
