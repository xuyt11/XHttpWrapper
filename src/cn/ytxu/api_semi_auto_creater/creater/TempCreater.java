package cn.ytxu.api_semi_auto_creater.creater;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.api_semi_auto_creater.model.DocModel;
import cn.ytxu.api_semi_auto_creater.model.VersionModel;
import cn.ytxu.api_semi_auto_creater.util.ReflectiveUtil;
import cn.ytxu.api_semi_auto_creater.util.XTempModel;
import cn.ytxu.api_semi_auto_creater.util.XTempUtil;
import cn.ytxu.api_semi_auto_creater.util.statement.StatementRecord;
import cn.ytxu.api_semi_auto_creater.util.statement.record.TextStatementRecord;

import java.io.Writer;
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
        createHttpApi();
//        requestCreate();
//        responseCreate();
    }


    private void createHttpApi() {
        XTempModel model = new XTempUtil(XTempUtil.Suffix.HttpApi).start();
        List<StatementRecord> records = StatementRecord.getRecords(model.getContents());
        StatementRecord.parseRecords(records);

        for (VersionModel version : docModel.getVersions()) {
            String dirPath = getString(model.getFileDir(), version);
            String fileName = getString(model.getFileName(), version);

            BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
                // TODO get write buffer need retain parameter
                StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, version);
                writer.write(contentBuffer.toString());
            });

//            System.out.println("=============start===========");
//            StringBuffer contentBuffer = StatementRecord.getWriteBuffer(records, version);
//
//            // TODO 写入到文件中
//            System.out.println(contentBuffer.toString());
//            System.out.println("=============end===========");
        }
    }

    private static String getString(String content, VersionModel version) {
        TextStatementRecord record = new TextStatementRecord(null, content);
        record.parse();
        return record.getWriteBuffer(version).toString().trim();
    }


}
