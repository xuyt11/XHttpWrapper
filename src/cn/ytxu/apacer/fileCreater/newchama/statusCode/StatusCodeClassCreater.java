package cn.ytxu.apacer.fileCreater.newchama.statusCode;

import cn.ytxu.apacer.entity.RetainEntity;
import cn.ytxu.apacer.fileCreater.newchama.BaseCreater;
import cn.ytxu.apacer.config.Config;
import cn.ytxu.apacer.entity.DocumentEntity;
import cn.ytxu.apacer.entity.StatusCodeEntity;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * 状态码文件生成器
 * 2016-03-30
 */
public class StatusCodeClassCreater {
    private static volatile StatusCodeClassCreater instance;
    private StatusCodeClassCreater() {
        super();
    }
    public static StatusCodeClassCreater getInstance() {
        if (null == instance) {
            synchronized (StatusCodeClassCreater.class) {
                if (null == instance) {
                    instance = new StatusCodeClassCreater();
                }
            }
        }
        return instance;
    }


    public void create(DocumentEntity doc) {
        String fileName = Config.statusCode.StatusCodeFileName + ".java";
        String dirPath = Config.statusCode.getDirPath();

        BaseCreater.getWriter4TargetFile(dirPath, fileName, (Writer writer, RetainEntity retain) -> {
            // package
            createImportAndClassStart(writer, retain);

            List<StatusCodeEntity> statusCodes = doc.getStatusCodes();
            for (StatusCodeEntity statusCode : statusCodes) {
                writer.write(createStatusCodeParam(statusCode));
            }
            writer.write("\n");

            createRetainDataAndClassEnd(writer, retain);
        });
    }

    private void createImportAndClassStart(Writer writer, RetainEntity retain) throws IOException {
        writer.write("package " + Config.statusCode.PackageName + ";\n\n");
        if (null != retain) {
            writer.write(retain.getImportData().toString());
            writer.write("\n");
        }

        // class start
        writer.write("\npublic class " + Config.statusCode.StatusCodeFileName + " {\n\n");
    }

    private String createStatusCodeParam(StatusCodeEntity statusCode) {
        String name = statusCode.getName();
        String value = statusCode.getValue();
        String desc = statusCode.getDesc();

        StringBuffer sb = new StringBuffer();
        sb.append("\t/** ").append(desc).append(" */\n");
        sb.append("\tpublic static final int ").append(name).append(" = ").append(value).append(";\n\n");

        return sb.toString();
    }

    private void createRetainDataAndClassEnd(Writer writer, RetainEntity retain) throws IOException {
        if (null != retain) {
            writer.write(retain.getFieldData().toString());
            writer.write("\n");
            writer.write(retain.getMethodData().toString());
            writer.write("\n");
            writer.write(retain.getOtherData().toString());
        }

        // class end
        writer.write("\n}");
        writer.flush();
    }


}
