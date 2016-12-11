package cn.ytxu.xhttp_wrapper.xtemp.parser.model;

import cn.ytxu.util.OSPlatform;
import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.StatementRecord;

import java.util.List;
import java.util.Objects;

/**
 * Created by ytxu on 16/7/30.
 */
public class XTempModel {
    public static final String HeaderStartTag = "<header>";
    public static final String HeaderEndTag = "</header>";

    private XTempFileModel file;
    private List<String> contents;
    private List<StatementRecord> records;

    public XTempModel() {
    }

    public void setFile(XTempFileModel file) {
        this.file = file;
    }

    public String getFileDir() {
        if (Objects.isNull(file)) {
            throw new NullPointerException("u need setup auto generat file config in <header>, or u setup error");
        }

        String currOsName = OSPlatform.getCurrentOSPlatform().getOsName();
        for (XTempFileDirModel fileDir : file.getFileDirs()) {
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

    public void setRecords(List<StatementRecord> records) {
        this.records = records;
    }

    public List<StatementRecord> getRecords() {
        return records;
    }

}
