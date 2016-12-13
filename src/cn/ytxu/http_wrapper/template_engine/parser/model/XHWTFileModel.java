package cn.ytxu.http_wrapper.template_engine.parser.model;

import java.util.List;

/**
 * xhwt文件，中生成文件的路径与名称；
 * 2016-12-11
 */
public class XHWTFileModel {
    private String fileName;// 文件的名称
    private List<XHWTFileDirModel> fileDirs;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<XHWTFileDirModel> getFileDirs() {
        return fileDirs;
    }

    public void setFileDirs(List<XHWTFileDirModel> fileDirs) {
        this.fileDirs = fileDirs;
    }
}
