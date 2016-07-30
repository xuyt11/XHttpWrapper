package cn.ytxu.api_semi_auto_creater.creater;

import cn.ytxu.api_semi_auto_creater.model.DocModel;
import cn.ytxu.api_semi_auto_creater.model.VersionModel;
import cn.ytxu.api_semi_auto_creater.util.XTempUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;

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
        httpApiCreate();
//        requestCreate();
//        responseCreate();
    }

    private void httpApiCreate() {
        List<String> contents = new XTempUtil(XTempUtil.Suffix.HttpApi).start();

        List<StatementRecord> records = StatementRecord.getRecords(contents);
        StatementRecord.parseRecords(records);

        for (VersionModel version : docModel.getVersions()) {
            System.out.println("=============start===========");
            StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, version);

            // TODO 写入到文件中
            System.out.println(contentBuffer.toString());
            System.out.println("=============end===========");
        }


    }


}
