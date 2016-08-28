package cn.ytxu.api_semi_auto_creater.xtemp_parser;

import cn.ytxu.api_semi_auto_creater.config.OSPlatform;

import java.util.List;

/**
 * Created by ytxu on 16/7/30.
 */
public class XTempModel {
    private List<FileDir> fileDirs;
    private String fileName;
    private List<String> contents;

    XTempModel() {
    }

    void setFileDirs(List<FileDir> fileDirs) {
        this.fileDirs = fileDirs;
    }

    void setFileName(String fileName) {
        this.fileName = fileName;
    }

    void setContents(List<String> contents) {
        this.contents = contents;
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

    public String getFileName() {
        return fileName;
    }

    public List<String> getContents() {
        return contents;
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
