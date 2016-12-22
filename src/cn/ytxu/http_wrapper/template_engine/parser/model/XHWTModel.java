package cn.ytxu.http_wrapper.template_engine.parser.model;

import cn.ytxu.http_wrapper.common.util.OSPlatform;
import cn.ytxu.http_wrapper.template.expression.ExpressionRecord;
import cn.ytxu.http_wrapper.template_engine.parser.statement.StatementRecord;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 16/7/30.
 */
public class XHWTModel {
    public static final String HeaderStartTag = "<t:header>";
    public static final String HeaderEndTag = "</t:header>";

    private XHWTFileModel file;
    private List<String> contents;
    private List<ExpressionRecord> records;

    public XHWTModel() {
    }

    public void setFile(XHWTFileModel file) {
        this.file = file;
    }

    public String getFileDir() {
        if (Objects.isNull(file)) {
            throw new NullPointerException("u need setup auto generat file config in <t:header>, or u setup error");
        }

        String currOsName = OSPlatform.getCurrentOSPlatform().getOsName();
        for (XHWTFileDirModel fileDir : file.getFileDirs()) {
            if (currOsName.equals(fileDir.getOsName())) {
                return fileDir.getPath();
            }
        }

        throw new NullPointerException("not find target file dir");
    }

    public String getFileName() {
        return file.getFileName();
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setRecords(List<ExpressionRecord> records) {
        this.records = records;
    }

    public List<ExpressionRecord> getRecords() {
        return records;
    }

}
