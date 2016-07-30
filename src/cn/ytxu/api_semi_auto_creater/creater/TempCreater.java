package cn.ytxu.api_semi_auto_creater.creater;

import cn.ytxu.api_semi_auto_creater.model.DocModel;
import cn.ytxu.api_semi_auto_creater.model.VersionModel;
import cn.ytxu.api_semi_auto_creater.util.XTempModel;
import cn.ytxu.api_semi_auto_creater.util.XTempUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ytxu on 2016/7/24.
 */
public class TempCreater {

    private DocModel docModel;

    public TempCreater(DocModel docModel) {
        this.docModel = docModel;
    }

    public void start() {
        new HttpApiCreater(docModel).start();
//        requestCreate();
//        responseCreate();
    }


    private static class HttpApiCreater {
        private DocModel docModel;


        public HttpApiCreater(DocModel docModel) {
            this.docModel = docModel;
        }

        private void start() {
            XTempModel model = new XTempUtil(XTempUtil.Suffix.HttpApi).start();

//            List<StatementRecord> records = StatementRecord.getRecords(contents);
//            StatementRecord.parseRecords(records);
//
//            for (VersionModel version : docModel.getVersions()) {
//                System.out.println("=============start===========");
//                StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, version);
//
//                // TODO 写入到文件中
//                System.out.println(contentBuffer.toString());
//                System.out.println("=============end===========");
//            }


        }



    }


}
