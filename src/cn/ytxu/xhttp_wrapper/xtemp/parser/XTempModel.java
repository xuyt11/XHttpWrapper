package cn.ytxu.xhttp_wrapper.xtemp.parser;

import cn.ytxu.util.OSPlatform;
import cn.ytxu.xhttp_wrapper.xtemp.parser.statement.StatementRecord;

import java.util.List;

/**
 * Created by ytxu on 16/7/30.
 */
public class XTempModel {
    private List<FileDir> fileDirs;
    private String fileName;
    private List<String> contents;
    private List<StatementRecord> records;

    XTempModel() {
    }

    void setFileDirs(List<FileDir> fileDirs) {
        this.fileDirs = fileDirs;
    }

    void addFileDir(FileDir fileDir) {
        this.fileDirs.add(fileDir);
    }

    public String getFileDir() {
        String currOsName = OSPlatform.getCurrentOSPlatform().getOsName();
        for (FileDir fileDir : fileDirs) {
            if (currOsName.equals(fileDir.osName)) {
                return fileDir.value;
            }
        }

        throw new NullPointerException("not find target file dir");
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    void setContents(List<String> contents) {
        this.contents = contents;
    }

    List<String> getContents() {
        return contents;
    }

    void setRecords(List<StatementRecord> records) {
        this.records = records;
    }

    public List<StatementRecord> getRecords() {
        return records;
    }

    static class FileDir {
        private String osName;// 对应系统的名称,必须要与OSPlatform中的一致
        private String value;// 文件夹的路径字符串

        FileDir(String osName, String value) {
            this.osName = osName;
            this.value = value;
        }
    }

}
